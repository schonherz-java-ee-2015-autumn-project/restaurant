package hu.schonherz.restaurant.web.delivery.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.ItemVo;
import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.service.vo.ProductVo;

public class DeliveryUtil {

	public static Collection<ProductVo> products(DeliveryVo delivery) {
		Collection<ProductVo> res = new HashSet<ProductVo>();
		for (OrderVo order : delivery.getOrders()) {
			try {
				res.addAll(order.getItems().stream().map(i -> i.getProduct()).collect(Collectors.toList()));
			} catch (Exception e) {
			}
		}
		return res;
	}

	public static ProductVo getProductByNameAndRestaurantId(DeliveryVo delivery, String name, Long id) {

		for (OrderVo order : delivery.getOrders()) {
			try {
				Optional<ItemVo> result = order.getItems().stream().filter(p -> p.getProduct().getName().equals(name)
						&& p.getProduct().getRestaurant().getId().equals(id)).findFirst();
				if (result.isPresent()) {
					return result.get().getProduct();
				}
			} catch (Exception e) {
			}
		}

		return null;
	}

}
