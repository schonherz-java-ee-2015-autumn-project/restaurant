package hu.schonherz.restaurant.service.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.restaurant.dao.DeliveryDao;
import hu.schonherz.restaurant.dao.ItemDao;
import hu.schonherz.restaurant.dao.OrderDao;
import hu.schonherz.restaurant.dao.ProductDao;
import hu.schonherz.restaurant.dao.RestaurantDao;
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

	@Autowired
	private RestaurantDao restaurantDao;

	@Override
	public List<DeliveryVo> getDeliveriesByRestaurantId(Long restId, int i, int pageSize, String sortField, int dir,
			String filter, String filterColumnName) {

		Sort.Direction direction = dir == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = new PageRequest(i, pageSize,
				new Sort(new org.springframework.data.domain.Sort.Order(direction, sortField)));
		Page<Delivery> entities;

		String restaurantNameCode = restaurantDao.findOne(restId).getName().replace(' ', '_');

		entities = deliveryDao.findByGuidStartingWith(restaurantNameCode, pageRequest);

		return DeliveryConverter.toVo(entities.getContent());
	}

	@Override
	public int getDeliveryCount() {
		return (int) deliveryDao.count();
	}

	@Override
	public int getDeliveryCountByRestaurantId(Long restId) {
		String restaurantNameCode = restaurantDao.findOne(restId).getName().replace(' ', '_');
		return (int) deliveryDao.countByGuidStartingWith(restaurantNameCode);
	}

	@Override
	public DeliveryVo getDeliveryByGuid(String guid) {
		return DeliveryConverter.toVo(deliveryDao.findByGuid(guid));
	}

	@Override
	public void saveDelivery(DeliveryVo delivery) {
		Delivery entity = DeliveryConverter.toEntity(delivery);

		if (entity.getIsDeleted() == null) {
			entity.setIsDeleted(false);
		}

		for (Order order : entity.getOrders()) {
			for (Item item : order.getItems()) {
				item.setProduct(productDao.save(item.getProduct()));
			}
			order.setItems(itemDao.save(order.getItems()));
		}
		entity.setOrders(orderDao.save(entity.getOrders()));

		deliveryDao.save(entity);
	}

	@Override
	public DeliveryVo getDeliveryById(Long id) {
		return DeliveryConverter.toVo(deliveryDao.findOne(id));
	}

	@Override
	public void deleteDeliveryById(Long id) {
		deliveryDao.setIsDeletedById(id);
	}

}
