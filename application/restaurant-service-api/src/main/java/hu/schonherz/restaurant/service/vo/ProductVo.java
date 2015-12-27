package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;

/**
 * Created by tothd on 2015. 12. 16..
 */
public class ProductVo implements Serializable {

	private Long id;

	private String name;

	private Integer price;

	private RestaurantVo restaurant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public RestaurantVo getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantVo restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProductVo)) {
			return false;
		}
		ProductVo other = (ProductVo) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
