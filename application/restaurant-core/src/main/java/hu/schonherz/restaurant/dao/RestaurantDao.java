package hu.schonherz.restaurant.dao;

import hu.schonherz.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tothd on 2015. 12. 23..
 */
@Repository
public interface RestaurantDao extends JpaRepository<Restaurant,Long> {

    Restaurant findById(Long id);

    Restaurant findByName(String name);

    Restaurant findByAddress(String address);
}
