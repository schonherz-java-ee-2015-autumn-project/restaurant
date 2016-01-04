package hu.schonherz.restaurant.entities;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.DETACH})
	private Product product;

	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.DETACH})
	private Order ofOrder;

	private Integer quantity;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Order getOfOrder() {
		return ofOrder;
	}

	public void setOfOrder(Order ofOrder) {
		this.ofOrder = ofOrder;
	}

}
