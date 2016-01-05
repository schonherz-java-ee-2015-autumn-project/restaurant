package hu.schonherz.restaurant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "global_id", unique = true)
	private Long globalId;

	@ManyToOne
	private Product product;

	@ManyToOne
	private Order ofOrder;

	private Integer quantity;

	public Long getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Long globalId) {
		this.globalId = globalId;
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

	public Order getOfOrder() {
		return ofOrder;
	}

	public void setOfOrder(Order ofOrder) {
		this.ofOrder = ofOrder;
	}

}