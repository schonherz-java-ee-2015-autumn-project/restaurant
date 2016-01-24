package hu.schonherz.restaurant.integration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.schonherz.administrator.InvalidDateException_Exception;
import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.administrator.RemoteCargoState;
import hu.schonherz.administrator.RestaurantService;
import hu.schonherz.administrator.RestaurantServiceImpl;
import hu.schonherz.administrator.SynchronizationService;
import hu.schonherz.administrator.SynchronizationServiceImpl;
import hu.schonherz.restaurant.integration.converter.DeliveryConverter;
import hu.schonherz.restaurant.integration.exception.RefresherException;
import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.OrderServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.ItemVo;
import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.service.vo.ProductVo;

@Stateless(mappedName = "deliveryRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class DeliveryRefresher implements RefresherLocal, RefresherRemote {

	private static Logger logger = LoggerFactory.getLogger(DeliveryRefresher.class);

	SynchronizationService synchronizationService;
	RestaurantService restService;

	@EJB
	DeliveryServiceLocal deliveryService;

	@EJB
	OrderServiceLocal orderService;

	@Override
	public void refresh() throws RefresherException {
		refreshSince(new Date(0));
	}

	@Override
	public void refreshSince(Date date) throws RefresherException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		try {
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			syncNewOrModifiedDeliveries();

			syncTakenDeliveries(calendar);

		} catch (DatatypeConfigurationException | InvalidDateException_Exception e) {
			throw new RefresherException(e);
		} catch (Exception e) {
			throw new RefresherException("Other exception", e);
		}
	}

	private void syncTakenDeliveries(XMLGregorianCalendar calendar) throws InvalidDateException_Exception {
		// Gets the list of taken/reserved deliveries only
		List<RemoteCargoDTO> cargos = getTakenDeliveries(calendar);

		List<DeliveryVo> deliveries = DeliveryConverter.toLocal(cargos);
		for (DeliveryVo taken : deliveries) {
			DeliveryVo local = deliveryService.getDeliveryByGuid(taken.getGlobalId());

			if (local == null || local.getIsDeleted()) {
				logger.warn("Invalid delivery got from administration");
				continue;
			}

			local.setDeliveryState(taken.getDeliveryState());
			local.setCourier(taken.getCourier());

			local.setOrders(new ArrayList<>(local.getOrders().size()));
			for (OrderVo takenOrder : taken.getOrders()) {
				OrderVo localOrder = orderService.getOrderByGlobalId(takenOrder.getGlobalId());
				if (localOrder != null) {
					localOrder.setOrderState(takenOrder.getOrderState());
					localOrder.setPayType(takenOrder.getPayType());
					local.getOrders().add(localOrder);
				}
			}

			deliveryService.refreshDelivery(local);
		}
	}

	private void syncNewOrModifiedDeliveries() {
		List<DeliveryVo> notSyncedDeliveries = deliveryService.getNonSyncedDeliveries();
		List<RemoteCargoDTO> remotes = DeliveryConverter.toRemote(notSyncedDeliveries);

		for (int i = 0; i < remotes.size(); i++) {
			try {
				RemoteCargoDTO rdto = remotes.get(i);
				RemoteCargoDTO savedCargo;

				if (rdto.getId() == null) {
					savedCargo = restService.saveCargo(rdto);
				} else {
					savedCargo = restService.modifyCargo(rdto);
				}

				updateDelivery(notSyncedDeliveries.get(i), savedCargo);
				deliveryService.refreshDelivery(notSyncedDeliveries.get(i));
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn(String.format("Exception when syncing delivery(%d)", notSyncedDeliveries.get(i).getId()));
			}
		}

	}

	private void updateDelivery(DeliveryVo localDelivery, RemoteCargoDTO cargo) {
		Validate.notNull(cargo.getId());

		DeliveryVo savedDelivery = DeliveryConverter.toLocal(cargo);
		localDelivery.setGlobalId(savedDelivery.getGlobalId());

		for (int i = 0; i < localDelivery.getOrders().size(); i++) {
			OrderVo localOrder = localDelivery.getOrders().get(i);
			OrderVo savedOrder = savedDelivery.getOrders().get(i);

			updateOrder(localOrder, savedOrder);
		}
	}

	private void updateOrder(OrderVo localOrder, OrderVo savedOrder) {
		Validate.notNull(savedOrder.getGlobalId());

		localOrder.setGlobalId(savedOrder.getGlobalId());

		for (int j = 0; j < localOrder.getItems().size(); j++) {
			ItemVo localItem = localOrder.getItems().get(j);
			ItemVo savedItem = savedOrder.getItems().get(j);

			updateItemAndProduct(localItem, savedItem);
		}
	}

	private void updateItemAndProduct(ItemVo localItem, ItemVo savedItem) {
		Validate.notNull(savedItem.getGlobalId());
		Validate.notNull(savedItem.getProduct().getGlobalId());

		localItem.setGlobalId(savedItem.getGlobalId());

		ProductVo localProduct = localItem.getProduct();
		ProductVo savedProduct = savedItem.getProduct();

		localProduct.setGlobalId(savedProduct.getGlobalId());
	}

	private List<RemoteCargoDTO> getTakenDeliveries(XMLGregorianCalendar calendar)
			throws InvalidDateException_Exception {
		List<RemoteCargoDTO> cargos = synchronizationService.getCargosByDate(calendar).stream()
				.filter(crg -> crg.getCourierId() != null && (crg.getState().equals(RemoteCargoState.DELIVERING)
						|| crg.getState().equals(RemoteCargoState.TAKEN)
						|| crg.getState().equals(RemoteCargoState.DELIVERED)))
				.collect(Collectors.toList());
		return cargos;
	}

	@PostConstruct
	public void init() {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("wsdllocation.properties"));
			URL url = new URL(prop.getProperty("user.wsdl"));
			URL restUrl = new URL(prop.getProperty("restaurant.wsdl"));

			QName name = new QName("http://wsserviceapi.administration.schonherz.hu/", "SynchronizationServiceImpl");
			QName restName = new QName("http://wsserviceapi.administration.schonherz.hu/", "RestaurantServiceImpl");

			SynchronizationServiceImpl synchronizationServiceImpl = new SynchronizationServiceImpl(url, name);
			RestaurantServiceImpl restServiceImpl = new RestaurantServiceImpl(restUrl, restName);

			synchronizationService = synchronizationServiceImpl.getSynchronizationServiceImplPort();
			restService = restServiceImpl.getRestaurantServiceImplPort();

		} catch (IOException e) {
			System.out.println("exception happend");
		}
	}

}
