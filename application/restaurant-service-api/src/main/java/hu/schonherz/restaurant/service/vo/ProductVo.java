package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;

/**
 * Created by tothd on 2015. 12. 16..
 */
public class ProductVo extends BaseVo implements Serializable {

	private String name;

	private Integer price;

	private RestaurantVo restaurant;

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
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ProductVo)) {
			return false;
		}
		ProductVo other = (ProductVo) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (restaurant == null) {
			if (other.restaurant != null) {
				return false;
			}
		} else if (!restaurant.equals(other.restaurant)) {
			return false;
		}
		return true;
	}

}
