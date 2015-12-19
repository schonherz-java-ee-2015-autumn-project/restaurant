package hu.schonherz.restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.Product;

/**
 * Created by tothd on 2015. 12. 19..
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    Product findById(Long id);

    @SuppressWarnings("unchecked")
    Product save(Product product);
}
