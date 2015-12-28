package hu.schonherz.restaurant.service;

import hu.schonherz.restaurant.service.vo.DeliveryVo;

import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
public interface DeliveryServiceRemote {
    List<DeliveryVo> getDeliveries(int i, int pageSize, String sortField, int dir, String filter,
                                   String filterColumnName);

    int getDeliveryCount();

    DeliveryVo getDeliveryById(Long id);

    void deleteDeliveryById(Long id);
}
