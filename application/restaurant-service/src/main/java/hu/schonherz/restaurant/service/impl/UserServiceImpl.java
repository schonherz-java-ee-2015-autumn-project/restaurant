package hu.schonherz.restaurant.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.restaurant.dao.RestaurantDao;
import hu.schonherz.restaurant.dao.RoleDao;
import hu.schonherz.restaurant.dao.UserDao;
import hu.schonherz.restaurant.entities.Restaurant;
import hu.schonherz.restaurant.entities.Role;
import hu.schonherz.restaurant.entities.User;
import hu.schonherz.restaurant.service.EntityVoConverter;
import hu.schonherz.restaurant.service.RestaurantConverter;
import hu.schonherz.restaurant.service.UserServiceLocal;
import hu.schonherz.restaurant.service.UserServiceRemote;
import hu.schonherz.restaurant.service.vo.UserVo;

@Stateless(mappedName = "UserService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(UserServiceLocal.class)
@Remote(UserServiceRemote.class)
public class UserServiceImpl implements UserServiceLocal, UserServiceRemote {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RestaurantDao restaurantDao;

	@Autowired
	private EntityVoConverter<UserVo, User> userConverter;

	@Override
	public UserVo findUserByName(String name) throws Exception {
		Validate.notNull(name);
		return userConverter.toVo(userDao.findByUsername(name));
	}

	@Override
	public List<UserVo> getUsers() {
		return userConverter.toVo(userDao.findAll());
	}

	@Override
	public UserVo findById(Long id) {
		Validate.notNull(id);
		return userConverter.toVo(userDao.findOne(id));
	}

	@Override
	public void save(UserVo user) {
		Validate.notNull(user);
		User u = userDao.findByUsername(user.getUsername());

		Role role = roleDao.findByName("ROLE_USER");
		if (role == null) {
			role = new Role("ROLE_USER");
			role = roleDao.save(role);
		}

		Restaurant restaurant = restaurantDao.findByName(user.getRestaurant().getName());
		if (restaurant == null) {
			restaurant = restaurantDao.save(RestaurantConverter.toEntity(user.getRestaurant()));
		}

		if (u == null) {
			u = userConverter.toEntity(user);
		} else {
			user.setId(u.getId());
			u = userConverter.toEntity(user);
		}

		u.setRoles(Arrays.asList(role));
		u.setRestaurant(restaurant);
		userDao.save(u);

	}

}