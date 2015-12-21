package hu.schonherz.restaurant.web.generator;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.schonherz.restaurant.service.vo.RestaurantVo;

public class GuidGenerator {

	public static String generate(RestaurantVo restaurant) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return restaurant.getName().replace(' ', '_') + format.format(new Date());
	}

}
