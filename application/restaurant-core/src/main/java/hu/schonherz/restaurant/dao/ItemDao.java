package hu.schonherz.restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.schonherz.restaurant.entities.Item;

public interface ItemDao extends JpaRepository<Item, Long> {

}
