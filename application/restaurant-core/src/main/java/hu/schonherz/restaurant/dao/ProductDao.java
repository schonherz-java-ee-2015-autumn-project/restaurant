package hu.schonherz.restaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.Product;

/**
 * Created by tothd on 2015. 12. 19..
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

	List<Product> findByRestaurantId(Long id);

	Product findByNameAndRestaurantId(String name, Long id);

	List<Product> findByNameStartingWithAndRestaurantId(String query, Long id);

}
