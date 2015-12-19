package hu.schonherz.restaurant.dao;

import hu.schonherz.restaurant.entities.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Repository
public interface DeliveryDao extends JpaRepository<Delivery,Long> {

    Page<Delivery> findAll(Pageable pageable);

    List<Delivery> findAll(Sort sort);

}
