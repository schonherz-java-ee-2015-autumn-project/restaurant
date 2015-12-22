package hu.schonherz.restaurant.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "deliveries")
public class Delivery extends BaseEntity {

	@Column(name = "delivery_state", nullable = false)
	@Enumerated(EnumType.STRING)
	private State deliveryState;

	@Column(name = "courier")
	private String courier;

	@Column(name = "delivery_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;

	@Column(name = "guid", unique = true, nullable = false)
	private String guid;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Order> orders;

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public State getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(State deliveryState) {
		this.deliveryState = deliveryState;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
