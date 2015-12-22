package hu.schonherz.restaurant.dao;

import hu.schonherz.restaurant.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tothd on 2015. 12. 19..
 */
@Repository
public interface OrderDao extends JpaRepository<Order,Long>{

    List<Order> findAll();

    Order findById(Long id);

    Page<Order> findByAddressContaining(String filter, Pageable pageable);
}
