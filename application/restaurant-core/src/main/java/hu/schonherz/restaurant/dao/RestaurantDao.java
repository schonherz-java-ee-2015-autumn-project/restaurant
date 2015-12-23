package hu.schonherz.restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.Restaurant;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Long> {

}
