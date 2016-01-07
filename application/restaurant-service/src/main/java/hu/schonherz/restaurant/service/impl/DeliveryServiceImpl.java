package hu.schonherz.restaurant.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.restaurant.dao.DeliveryDao;
import hu.schonherz.restaurant.dao.ItemDao;
import hu.schonherz.restaurant.dao.OrderDao;
import hu.schonherz.restaurant.dao.ProductDao;
import hu.schonherz.restaurant.entities.Delivery;
import hu.schonherz.restaurant.entities.Item;
import hu.schonherz.restaurant.entities.Order;
import hu.schonherz.restaurant.service.DeliveryConverter;
import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.DeliveryServiceRemote;
import hu.schonherz.restaurant.service.vo.DeliveryVo;

@Stateless(mappedName = "DeliveryService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(DeliveryServiceLocal.class)
@Remote(DeliveryServiceRemote.class)
public class DeliveryServiceImpl implements DeliveryServiceLocal, DeliveryServiceRemote {

	@Autowired
	private DeliveryDao deliveryDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ItemDao itemDao;

	@Override
	public List<DeliveryVo> getDeliveriesByRestaurantId(Long restId, int i, int pageSize, String sortField, int dir,
			String filter, String filterColumnName) {

		Validate.notNull(restId);
		Validate.notNull(pageSize);
		Validate.notNull(sortField);
		Validate.notNull(filter);
		Validate.notNull(filterColumnName);

		Sort.Direction direction = dir == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = new PageRequest(i, pageSize,
				new Sort(new org.springframework.data.domain.Sort.Order(direction, sortField)));
		Page<Delivery> entities;

		entities = deliveryDao.findByRestaurantId(restId, pageRequest);

		return DeliveryConverter.toVo(entities.getContent());
	}

	@Override
	public int getDeliveryCount() {
		return (int) deliveryDao.count();
	}

	@Override
	public int getDeliveryCountByRestaurantId(Long restId) {
		Validate.notNull(restId);
		return (int) deliveryDao.countByRestaurantId(restId);
	}

	@Override
	public DeliveryVo getDeliveryByGuid(Long guid) {
		Validate.notNull(guid);
		return DeliveryConverter.toVo(deliveryDao.findByGlobalId(guid));
	}

	@Override
	public void saveDelivery(DeliveryVo delivery) {
		Validate.notNull(delivery);
		Delivery entity = DeliveryConverter.toEntity(delivery);
		Date newModDate = new Date();

		entity.setSynced(null);
		entity.setModDate(newModDate);

		if (entity.getIsDeleted() == null) {
			entity.setIsDeleted(false);
		}

		for (Order order : entity.getOrders()) {
			Validate.notNull(order);
			order.setModDate(newModDate);

			for (Item item : order.getItems()) {
				Validate.notNull(item);
				Validate.notNull(item.getProduct());

				item.setModDate(newModDate);
				item.getProduct().setModDate(newModDate);

				item.setProduct(productDao.save(item.getProduct()));
			}
			order.setItems(itemDao.save(order.getItems()));
		}
		entity.setOrders(orderDao.save(entity.getOrders()));

		deliveryDao.save(entity);
	}

	@Override
	public DeliveryVo getDeliveryById(Long id) {
		Validate.notNull(id);
		return DeliveryConverter.toVo(deliveryDao.findOne(id));
	}

	@Override
	public void deleteDeliveryById(Long id) {
		Validate.notNull(id);
		deliveryDao.setIsDeletedById(id);
	}

	@Override
	public List<DeliveryVo> getNonSyncedDeliveries() {
		return DeliveryConverter.toVo(deliveryDao.findNotSynced());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void refreshDelivery(DeliveryVo deliveryVo) {
		Delivery entity = DeliveryConverter.toEntity(deliveryVo);

		for (Order order : entity.getOrders()) {
			for (Item item : order.getItems()) {
				item.setProduct(productDao.save(item.getProduct()));
			}
			order.setItems(itemDao.save(order.getItems()));
		}
		entity.setOrders(orderDao.save(entity.getOrders()));

		entity.setSynced(new Date());
		deliveryDao.save(entity);
	}

}