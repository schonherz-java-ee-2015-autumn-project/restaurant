package hu.schonherz.restaurant.service;

import hu.schonherz.restaurant.entities.Product;
import hu.schonherz.restaurant.service.vo.ProductVo;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 17..
 */
public class ProductConverter {

    static Mapper mapper = new DozerBeanMapper();

    public static ProductVo toVo(Product product) {
        if (product == null) {
            return null;
        }
        return mapper.map(product, ProductVo.class);
    }

    public static Product toEntity(ProductVo productVo) {
        if (productVo == null) {
            return null;
        }
        return mapper.map(productVo, Product.class);
    }

    public static List<ProductVo> toVo(List<Product> products) {
        List<ProductVo> productVos = new ArrayList<>();
        for (Product product : products) {
            productVos.add(toVo(product));
        }
        return productVos;
    }

    public static List<Product> toEntity(List<ProductVo> productVos) {
        List<Product> products = new ArrayList<>();
        for (ProductVo productVo : productVos) {
            products.add(toEntity(productVo));
        }
        return products;
    }
}
