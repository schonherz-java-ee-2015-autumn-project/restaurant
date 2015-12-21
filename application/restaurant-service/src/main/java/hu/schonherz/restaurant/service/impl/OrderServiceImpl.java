package hu.schonherz.restaurant.service.impl;

import hu.schonherz.restaurant.dao.OrderDao;
import hu.schonherz.restaurant.service.OrderConverter;
import hu.schonherz.restaurant.service.OrderServiceLocal;
import hu.schonherz.restaurant.service.OrderServiceRemote;
import hu.schonherz.restaurant.service.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 21..
 */
@Stateless(mappedName = "OrderService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(OrderServiceLocal.class)
@Remote(OrderServiceRemote.class)
public class OrderServiceImpl implements OrderServiceLocal, OrderServiceRemote{

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<OrderVo> getOrders() {
        return OrderConverter.toVo(orderDao.findAll());
    }

    @Override
    public OrderVo getOrderById(Long id) {
        return OrderConverter.toVo(orderDao.findById(id));
    }

    @Override
    public OrderVo saveOrder(OrderVo orderVo) {
        return OrderConverter.toVo(orderDao.save(OrderConverter.toEntity(orderVo)));
    }
}
