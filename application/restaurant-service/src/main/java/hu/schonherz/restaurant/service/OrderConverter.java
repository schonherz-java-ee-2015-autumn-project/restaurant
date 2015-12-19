package hu.schonherz.restaurant.service;

import hu.schonherz.restaurant.entities.Order;
import hu.schonherz.restaurant.service.vo.OrderVo;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 17..
 */
public class OrderConverter {

    static Mapper mapper = new DozerBeanMapper();


    public static OrderVo toVo(Order order) {
        if (order == null) {
            return null;
        }
        return mapper.map(order, OrderVo.class);
    }

    public static Order toEntity(OrderVo orderVo) {
        if (orderVo == null) {
            return null;
        }
        return mapper.map(orderVo, Order.class);
    }

    public static List<OrderVo> toVo(List<Order> orders) {
        List<OrderVo> orderVos = new ArrayList<>();
        for (Order order : orders) {
            orderVos.add(toVo(order));
        }
        return orderVos;
    }

    public static List<Order> toEntity(List<OrderVo> orderVos) {
        List<Order> orders = new ArrayList<>();
        for (OrderVo orderVo : orderVos) {
            orders.add(toEntity(orderVo));
        }
        return orders;
    }
}
