package hu.schonherz.restaurant.service.impl;

import hu.schonherz.restaurant.dao.DeliveryDao;
import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.DeliveryServiceRemote;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 17..
 */
@Stateless(mappedName = "DeliveryService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(DeliveryServiceLocal.class)
@Remote(DeliveryServiceRemote.class)
public class DeliveryServiceImpl implements DeliveryServiceLocal, DeliveryServiceRemote{

    @Autowired
    DeliveryDao deliveryDao;

    @Override
    public List<DeliveryVo> getDeliveries(int i, int pageSize, String sortField, int dir, String filter, String filterColumnName) {
        return null;
    }

    @Override
    public int getDeliveryCount() {
        return 0;
    }

    @Override
    public DeliveryVo getDeliveryById(String id) {
        return null;
    }
}
