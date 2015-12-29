package hu.schonherz.restaurant.service.impl;

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

import hu.schonherz.restaurant.dao.OrderDao;
import hu.schonherz.restaurant.entities.Order;
import hu.schonherz.restaurant.service.OrderConverter;
import hu.schonherz.restaurant.service.OrderServiceLocal;
import hu.schonherz.restaurant.service.OrderServiceRemote;
import hu.schonherz.restaurant.service.vo.OrderVo;

/**
 * Created by tothd on 2015. 12. 21..
 */
@Stateless(mappedName = "OrderService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(OrderServiceLocal.class)
@Remote(OrderServiceRemote.class)
public class OrderServiceImpl implements OrderServiceLocal, OrderServiceRemote {

	@Autowired
	private OrderDao orderDao;

	@Override
	public List<OrderVo> getDeliveries(int i, int pageSize, String sortField, int dir, String filter,
			String filterColumnName) {
		
		Validate.notNull(pageSize);
		Validate.notNull(sortField);
		Validate.notNull(filter);
		Validate.notNull(filterColumnName);
		
		Sort.Direction direction = dir == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = new PageRequest(i, pageSize,
				new Sort(new org.springframework.data.domain.Sort.Order(direction, sortField)));
		Page<Order> entities;

		if (filter.length() != 0 && filterColumnName.equals("address")) {
			entities = orderDao.findByAddressContaining(filter, pageRequest);
		} else {
			entities = orderDao.findAll(pageRequest);
		}

		return OrderConverter.toVo(entities.getContent());
	}

	@Override
	public List<OrderVo> getOrders() {
		return OrderConverter.toVo(orderDao.findAll());
	}

	@Override
	public OrderVo getOrderById(Long id) {
		Validate.notNull(id);
		return OrderConverter.toVo(orderDao.findOne(id));
	}

	@Override
	public OrderVo saveOrder(OrderVo orderVo) {
		Validate.notNull(orderVo);
		return OrderConverter.toVo(orderDao.save(OrderConverter.toEntity(orderVo)));
	}
}
