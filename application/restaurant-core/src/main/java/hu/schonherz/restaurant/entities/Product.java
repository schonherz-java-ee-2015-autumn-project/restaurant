package hu.schonherz.restaurant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

	@Column(name = "product_name", nullable = false)
	private String name;

	@Column(name = "product_price", nullable = false, length = 50)
	private Integer price;

	@ManyToOne(fetch = FetchType.LAZY)
	private Restaurant restaurant;

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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
