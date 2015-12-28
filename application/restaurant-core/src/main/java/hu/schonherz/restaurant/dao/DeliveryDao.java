package hu.schonherz.restaurant.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.Delivery;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Repository
public interface DeliveryDao extends JpaRepository<Delivery, Long> {

	Page<Delivery> findByCourierContaining(String filter, Pageable pageable);

	Delivery findByGuid(String guid);

	Page<Delivery> findByGuidStartingWith(String restaurantNameCode, Pageable pageable);

	long countByGuidStartingWith(String restaurantNameCode);

	@Modifying
	@Query("update Delivery d set d.isDeleted=true where d.id=?1")
	void setIsDeletedById(Long id);

}
