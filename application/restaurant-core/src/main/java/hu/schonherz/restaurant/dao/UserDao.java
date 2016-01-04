package hu.schonherz.restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	public User findByUsername(String username);

}