package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.ProductVo;

/**
 * Created by tothd on 2015. 12. 21..
 */
public interface ProductServiceRemote {
	List<ProductVo> getProducts();

	ProductVo getProductById(Long id);

	ProductVo saveProduct(ProductVo productVo);

	List<ProductVo> getProductsByRestaurantId(Long id);

	ProductVo getProductByNameAndRestaurantId(String name, Long id);

	List<ProductVo> getProductsByNameStartingWithAndRestaurantId(String query, Long id);

}
