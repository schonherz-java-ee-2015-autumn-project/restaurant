package hu.schonherz.restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.Restaurant;

/**
 * Created by tothd on 2015. 12. 23..
 */
@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {

	Restaurant findByName(String name);

	Restaurant findByAddress(String address);

}
