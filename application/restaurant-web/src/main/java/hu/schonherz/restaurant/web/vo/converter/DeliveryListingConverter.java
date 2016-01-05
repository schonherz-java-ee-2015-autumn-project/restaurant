
package hu.schonherz.restaurant.web.vo.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.web.vo.DeliveryListingVo;

public class DeliveryListingConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static DeliveryListingVo toListingVo(DeliveryVo source) {
		if (source == null) {
			return null;
		}
		DeliveryListingVo res = new DeliveryListingVo(source.getId(), source.getGlobalId(), source.getCourier(),
				source.getOrders().size(), source.getOrders().stream().mapToInt(ord -> ord.getTotalPrice()).sum(),
				source.getDeliveryState());
		return res;
	}

	public static List<DeliveryListingVo> toListingVo(List<DeliveryVo> sources) {
		List<DeliveryListingVo> res = new ArrayList<>();
		for (DeliveryVo deliveryVo : sources) {
			res.add(toListingVo(deliveryVo));
		}
		return res;
	}

}
