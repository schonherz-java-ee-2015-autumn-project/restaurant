package hu.schonherz.restaurant.web.vo;

import java.io.Serializable;

public class DeliveryItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Integer quantity;

	public DeliveryItem() {
	}

	public DeliveryItem(String name, Integer quantity) {
		super();
		this.name = name;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
