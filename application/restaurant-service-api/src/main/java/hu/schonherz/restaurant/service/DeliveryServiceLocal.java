package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.DeliveryVo;

/**
 * Created by tothd on 2015. 12. 16..
 */
public interface DeliveryServiceLocal {
	List<DeliveryVo> getDeliveriesByRestaurantId(Long resId, int i, int pageSize, String sortField, int dir,
			String filter, String filterColumnName);

	int getDeliveryCount();

	int getDeliveryCountByRestaurantId(Long restId);

	DeliveryVo getDeliveryByGuid(String guid);

	void saveDelivery(DeliveryVo delivery);

	DeliveryVo getDeliveryById(Long id);

	void deleteDeliveryById(Long id);

}
