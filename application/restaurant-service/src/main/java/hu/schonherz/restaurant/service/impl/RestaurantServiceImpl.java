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

import hu.schonherz.restaurant.dao.RestaurantDao;
import hu.schonherz.restaurant.service.RestaurantConverter;
import hu.schonherz.restaurant.service.RestaurantServiceLocal;
import hu.schonherz.restaurant.service.RestaurantServiceRemote;
import hu.schonherz.restaurant.service.vo.RestaurantVo;

/**
 * Created by tothd on 2015. 12. 23..
 */
@Stateless(mappedName = "RestaurantService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(RestaurantServiceLocal.class)
@Remote(RestaurantServiceRemote.class)
public class RestaurantServiceImpl implements RestaurantServiceLocal, RestaurantServiceRemote {

	@Autowired
	RestaurantDao restaurantDao;

	@Override
	public List<RestaurantVo> getRestaurants() {
		return RestaurantConverter.toVo(restaurantDao.findAll());
	}

	@Override
	public RestaurantVo getRestaurantById(Long id) {
		return RestaurantConverter.toVo(restaurantDao.findOne(id));
	}

	@Override
	public RestaurantVo getRestaurantByName(String name) {
		return RestaurantConverter.toVo(restaurantDao.findByName(name));
	}

	@Override
	public RestaurantVo getRestaurantByAddress(String address) {
		return RestaurantConverter.toVo(restaurantDao.findByAddress(address));
	}
}
