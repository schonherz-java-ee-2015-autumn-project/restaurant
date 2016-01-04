package hu.schonherz.restaurant.dao;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.restaurant.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test-core.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class UserDaoTest {

	@Autowired
	UserDao userDao;

	@Test
	public void testFindByUsername() {
		User user = createUser("Test Admin", "testpass", "062015151515", "admintest");

		User saved = userDao.save(user);

		User got = userDao.findByUsername("admintest");

		assertTrue(equals(saved, got));
	}

	private Boolean equals(User u1, User u2) {
		return (u1.getId().equals(u2.getId())) && (u1.getName().equals(u2.getName()))
				&& (u1.getPhoneNumber().equals(u2.getPhoneNumber())) && (u1.getPassword().equals(u2.getPassword()));
	}

	private User createUser(String name, String password, String phoneNumber, String username) {
		User user = new User();

		user.setName(name);
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		user.setUsername(username);
		user.setBanned(false);

		return user;
	}
}