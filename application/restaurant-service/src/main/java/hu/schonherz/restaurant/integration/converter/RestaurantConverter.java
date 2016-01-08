package hu.schonherz.restaurant.integration.converter;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.WebRestaurantDTO;
import hu.schonherz.restaurant.service.vo.RestaurantVo;

public class RestaurantConverter {

	public static RestaurantVo toVo(WebRestaurantDTO restaurant) {
		RestaurantVo restaurantVo = new RestaurantVo();

		restaurantVo.setAddress(restaurant.getAddress());
		restaurantVo.setBanned(restaurant.isIsDeleted());
		restaurantVo.setGlobalId(restaurant.getId());
		restaurantVo.setName(restaurant.getName());
		restaurantVo.setCostOfService(restaurant.getPrice());

		return restaurantVo;
	}

	public static List<RestaurantVo> toWebDto(List<WebRestaurantDTO> restaurant) {
		ArrayList<RestaurantVo> restaurants = new ArrayList<RestaurantVo>();

		for (WebRestaurantDTO webdto : restaurant) {
			restaurants.add(toVo(webdto));
		}

		return restaurants;
	}

}
