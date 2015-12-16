package hu.schonherz.restaurant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_price",  nullable = false, length = 50)
    private Integer price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
