package hu.schonherz.restaurant.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = { CascadeType.ALL })
	private Order order;

	@ManyToOne(cascade = { CascadeType.ALL })
	private Product product;

	private Integer quantity;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

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

}
