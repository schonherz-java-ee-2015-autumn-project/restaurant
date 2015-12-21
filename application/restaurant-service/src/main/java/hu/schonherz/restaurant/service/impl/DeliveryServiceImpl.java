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
import hu.schonherz.restaurant.dao.OrderDao;
import hu.schonherz.restaurant.dao.ProductDao;
import hu.schonherz.restaurant.entities.Delivery;
import hu.schonherz.restaurant.entities.Order;
import hu.schonherz.restaurant.service.DeliveryConverter;
import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.DeliveryServiceRemote;
import hu.schonherz.restaurant.service.vo.DeliveryVo;

/**
 * Created by tothd on 2015. 12. 17..
 */
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

	@Override
	public List<DeliveryVo> getDeliveries(int i, int pageSize, String sortField, int dir, String filter,
			String filterColumnName) {

		Sort.Direction direction = dir == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = new PageRequest(i, pageSize,
				new Sort(new org.springframework.data.domain.Sort.Order(direction, sortField)));
		Page<Delivery> entities;

		if (filter.length() != 0 && filterColumnName.equals("courier")) {
			entities = deliveryDao.findByCourierContaining(filter, pageRequest);
		} else {
			entities = deliveryDao.findAll(pageRequest);
		}

		return DeliveryConverter.toVo(entities.getContent());
	}

	@Override
	public int getDeliveryCount() {
		return (int) deliveryDao.count();
	}

	@Override
	public DeliveryVo getDeliveryById(Long id) {
		return DeliveryConverter.toVo(deliveryDao.findOne(id));
	}

	@Override
	public DeliveryVo getDeliveryByGuid(String guid) {
		return DeliveryConverter.toVo(deliveryDao.findByGuid(guid));
	}

	@Override
	public void saveDelivery(DeliveryVo delivery) {
		Delivery entity = DeliveryConverter.toEntity(delivery);

		for (Order order : entity.getOrders()) {
			int sum = order.getProducts().stream().mapToInt(prd -> prd.getPrice()).sum();
			order.setTotalPrice(sum);

			order.setProducts(productDao.save(order.getProducts()));
		}

		entity.setOrders(orderDao.save(entity.getOrders()));

		deliveryDao.save(entity);
	}
}
