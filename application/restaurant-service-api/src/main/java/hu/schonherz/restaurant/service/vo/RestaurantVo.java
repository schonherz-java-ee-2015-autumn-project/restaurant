package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;
import java.util.List;

public class RestaurantVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String address;

	private List<ProductVo> products;

	private Boolean banned;
	
	private float costOfService;

	public float getCostOfService() {
		return costOfService;
	}

	public void setCostOfService(float costOfService) {
		this.costOfService = costOfService;
	}

	public List<ProductVo> getProducts() {
		return products;
	}

	public void setProducts(List<ProductVo> products) {
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof RestaurantVo)) {
			return false;
		}
		RestaurantVo other = (RestaurantVo) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
