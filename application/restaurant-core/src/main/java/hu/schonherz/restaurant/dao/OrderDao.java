package hu.schonherz.restaurant.dao;

import hu.schonherz.restaurant.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tothd on 2015. 12. 19..
 */
@Repository
public interface OrderDao extends JpaRepository<Order,Long>{

    List<Order> findAll();

    @SuppressWarnings("unchecked")
    Order save(Order order);
}
