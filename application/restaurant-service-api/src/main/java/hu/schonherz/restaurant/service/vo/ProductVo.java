package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;

/**
 * Created by tothd on 2015. 12. 16..
 */
public class ProductVo extends BaseVo implements Serializable{

    private String name;

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
