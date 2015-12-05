package hu.schonherz.restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

	public Role findByName(String name);

}
