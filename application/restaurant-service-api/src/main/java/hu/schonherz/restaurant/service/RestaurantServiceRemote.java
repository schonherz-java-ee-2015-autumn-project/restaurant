package hu.schonherz.restaurant.service;

import hu.schonherz.restaurant.service.vo.RestaurantVo;

import java.util.List;

/**
 * Created by tothd on 2015. 12. 23..
 */
public interface RestaurantServiceRemote {

    List<RestaurantVo> getRestaurants();

    RestaurantVo getRestaurantById(Long id);

    RestaurantVo getRestaurantByName(String name);

    RestaurantVo getRestaurantByAddress(String address);
}
