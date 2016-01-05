package hu.schonherz.restaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hu.schonherz.restaurant.entities.Item;

public interface ItemDao extends JpaRepository<Item, Long> {

	@Query("SELECT i FROM Delivery d JOIN d.orders o JOIN o.items i WHERE d.id = :deliveryId")
	List<Item> findByDeliveryId(@Param("deliveryId") Long id);

	Item findByGlobalId(Long globalId);

}