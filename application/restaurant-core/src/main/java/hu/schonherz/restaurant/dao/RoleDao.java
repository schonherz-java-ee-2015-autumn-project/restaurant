package hu.schonherz.restaurant.dao;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.dto.AddressReport;
import hu.schonherz.restaurant.dto.OrderCountReport;
import hu.schonherz.restaurant.entities.Role;



@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

	public Role findByName(String name);
	


}
