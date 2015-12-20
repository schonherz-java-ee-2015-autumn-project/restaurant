package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.entities.Restaurant;
import hu.schonherz.restaurant.service.vo.RestaurantVo;

public class RestaurantConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static RestaurantVo toVo(Restaurant entity) {
		if (entity == null) {
			return null;
		}
		return mapper.map(entity, RestaurantVo.class);
	}

	public static Restaurant toEntity(RestaurantVo vo) {
		if (vo == null) {
			return null;
		}
		return mapper.map(vo, Restaurant.class);
	}

	public static List<RestaurantVo> toVo(List<Restaurant> restaurants) {
		List<RestaurantVo> rv = new ArrayList<>();
		for (Restaurant rest : restaurants) {
			rv.add(toVo(rest));
		}
		return rv;
	}

	public static List<Restaurant> toEntity(List<RestaurantVo> restaurants) {
		List<Restaurant> rv = new ArrayList<>();
		for (RestaurantVo rest : restaurants) {
			rv.add(toEntity(rest));
		}
		return rv;
	}
}
