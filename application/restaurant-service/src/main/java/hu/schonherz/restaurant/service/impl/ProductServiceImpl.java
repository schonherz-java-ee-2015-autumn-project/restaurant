package hu.schonherz.restaurant.service.impl;

import hu.schonherz.restaurant.dao.ProductDao;
import hu.schonherz.restaurant.service.ProductConverter;
import hu.schonherz.restaurant.service.ProductServiceLocal;
import hu.schonherz.restaurant.service.ProductServiceRemote;
import hu.schonherz.restaurant.service.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 21..
 */
@Stateless(mappedName = "ProductService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(ProductServiceLocal.class)
@Remote(ProductServiceRemote.class)
public class ProductServiceImpl implements ProductServiceLocal,ProductServiceRemote{

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductVo> getProducts() {
        return ProductConverter.toVo(productDao.findAll());
    }

    @Override
    public ProductVo getProductById(Long id) {
        return ProductConverter.toVo(productDao.findById(id));
    }

    @Override
    public ProductVo saveProduct(ProductVo productVo) {
        return ProductConverter.toVo(productDao.save(ProductConverter.toEntity(productVo)));
    }

    @Override
    public List<ProductVo> getProductsByRestaurantId(Long id) {
        return ProductConverter.toVo(productDao.findAllByRestaurantId(id));
    }
}
