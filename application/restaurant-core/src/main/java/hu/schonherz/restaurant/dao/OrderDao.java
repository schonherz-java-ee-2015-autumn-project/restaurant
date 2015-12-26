package hu.schonherz.restaurant.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.Order;

/**
 * Created by tothd on 2015. 12. 19..
 */
@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

	Page<Order> findByAddressContaining(String filter, Pageable pageable);

}
