package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;
import java.util.List;

public class RestaurantVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String address;

	private List<ProductVo> products;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
