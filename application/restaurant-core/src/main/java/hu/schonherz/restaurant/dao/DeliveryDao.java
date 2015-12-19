package hu.schonherz.restaurant.dao;

import hu.schonherz.restaurant.entities.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Repository
public interface DeliveryDao extends JpaRepository<Delivery,Long>, JpaSpecificationExecutor<Delivery> {

    Page<Delivery> findAllContaining(String filter,PageRequest pageRequest);

    long countById();

    Delivery findById(Long id);

    @SuppressWarnings("unchecked")
    Delivery save(Delivery delivery);
}
