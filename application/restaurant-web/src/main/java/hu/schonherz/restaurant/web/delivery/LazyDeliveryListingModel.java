package hu.schonherz.restaurant.web.delivery;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.web.UserSessionBean;
import hu.schonherz.restaurant.web.vo.DeliveryListingVo;
import hu.schonherz.restaurant.web.vo.converter.DeliveryListingConverter;

@ViewScoped
@ManagedBean(name = "deliveryTableModel")
public class LazyDeliveryListingModel extends LazyDataModel<DeliveryListingVo> {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(LazyDeliveryListingModel.class);

	@EJB
	private DeliveryServiceLocal deliveryService;

	@ManagedProperty("#{userSessionBean}")
	private UserSessionBean userSessionBean;

	private List<DeliveryListingVo> data;

	@PostConstruct
	public void init() {
		if (userSessionBean.getUser() == null) {
			userSessionBean.init();
		}
	}

	@Override
	public List<DeliveryListingVo> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		String filter = "";
		String filterColumnName = "";

		if (sortField == null) {
			sortField = "guid";
		}

		int dir = sortOrder.equals(SortOrder.ASCENDING) ? 1 : 2;

		Long restId = userSessionBean.getUser().getRestaurant().getId();
		data = DeliveryListingConverter.toListingVo(deliveryService.getDeliveriesByRestaurantId(restId,
				first / pageSize, pageSize, sortField, dir, filter, filterColumnName));

		int size = deliveryService.getDeliveryCountByRestaurantId(restId);

		setRowCount(size);

		return data;
	}

	@Override
	public DeliveryListingVo getRowData(String rowKey) {
		if (rowKey == null) {
			logger.warn("Null rowkey!");
			return null;
		}

		for (DeliveryListingVo listingVo : data) {
			if (listingVo.getGuid().equals(rowKey)) {
				return listingVo;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(DeliveryListingVo object) {
		return object.getGuid();
	}

	public DeliveryServiceLocal getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryServiceLocal deliveryService) {
		this.deliveryService = deliveryService;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public List<DeliveryListingVo> getData() {
		return data;
	}

	public void setData(List<DeliveryListingVo> data) {
		this.data = data;
	}

}
