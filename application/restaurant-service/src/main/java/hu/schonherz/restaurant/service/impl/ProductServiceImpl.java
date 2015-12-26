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

import hu.schonherz.restaurant.dao.ProductDao;
import hu.schonherz.restaurant.service.ProductConverter;
import hu.schonherz.restaurant.service.ProductServiceLocal;
import hu.schonherz.restaurant.service.ProductServiceRemote;
import hu.schonherz.restaurant.service.vo.ProductVo;

/**
 * Created by tothd on 2015. 12. 21..
 */
@Stateless(mappedName = "ProductService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(ProductServiceLocal.class)
@Remote(ProductServiceRemote.class)
public class ProductServiceImpl implements ProductServiceLocal, ProductServiceRemote {

	@Autowired
	private ProductDao productDao;

	@Override
	public List<ProductVo> getProducts() {
		return ProductConverter.toVo(productDao.findAll());
	}

	@Override
	public ProductVo getProductById(Long id) {
		return ProductConverter.toVo(productDao.findOne(id));
	}

	@Override
	public ProductVo saveProduct(ProductVo productVo) {
		return ProductConverter.toVo(productDao.save(ProductConverter.toEntity(productVo)));
	}

	@Override
	public List<ProductVo> getProductsByRestaurantId(Long id) {
		return ProductConverter.toVo(productDao.findByRestaurantId(id));
	}

	@Override
	public ProductVo getProductByNameAndRestaurantId(String name, Long id) {
		return ProductConverter.toVo(productDao.findByNameAndRestaurantId(name, id));
	}

}
