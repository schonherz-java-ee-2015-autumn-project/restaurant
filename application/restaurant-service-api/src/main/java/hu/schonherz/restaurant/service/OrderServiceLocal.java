package hu.schonherz.restaurant.service;

import hu.schonherz.restaurant.service.vo.OrderVo;

import java.util.List;

/**
 * Created by tothd on 2015. 12. 21..
 */
public interface OrderServiceLocal {

    List<OrderVo> getDeliveries(int i, int pageSize, String sortField, int dir, String filter,
                                   String filterColumnName);

    List<OrderVo> getOrders();

    OrderVo getOrderById(Long id);

    OrderVo saveOrder(OrderVo orderVo);
}
