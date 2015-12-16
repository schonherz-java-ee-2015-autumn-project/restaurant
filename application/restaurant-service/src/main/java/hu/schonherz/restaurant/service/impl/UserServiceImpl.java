package hu.schonherz.restaurant.service.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.restaurant.dao.UserDao;
import hu.schonherz.restaurant.entities.User;
import hu.schonherz.restaurant.service.EntityVoConverter;
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
	private EntityVoConverter<UserVo, User> userConverter;

	@Override
	public UserVo findUserByName(String name) throws Exception {
		return userConverter.toVo(userDao.findByUsername(name));
	}

	@Override
	public List<UserVo> getUsers() {
		return userConverter.toVo(userDao.findAll());
	}

	@Override
	public UserVo findById(Long id) {
		return userConverter.toVo(userDao.findOne(id));
	}

}
