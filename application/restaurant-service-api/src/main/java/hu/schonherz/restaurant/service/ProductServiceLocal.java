package hu.schonherz.restaurant.service;

import hu.schonherz.restaurant.service.vo.ProductVo;

import java.util.List;

/**
 * Created by tothd on 2015. 12. 21..
 */
public interface ProductServiceLocal {
    List<ProductVo> getProducts();

    ProductVo getProductById(Long id);

    ProductVo saveProduct(ProductVo productVo);

    List<ProductVo> getProductsByRestaurantId(Long id);
}
