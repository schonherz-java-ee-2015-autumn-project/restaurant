package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.OrderVo;

/**
 * Created by tothd on 2015. 12. 21..
 */
public interface OrderServiceRemote {

	List<OrderVo> getDeliveries(int i, int pageSize, String sortField, int dir, String filter, String filterColumnName);

	List<OrderVo> getOrders();

	OrderVo getOrderById(Long id);

	OrderVo saveOrder(OrderVo orderVo);
}
