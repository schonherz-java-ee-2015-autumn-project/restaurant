package hu.schonherz.restaurant.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "deliveries")
@Where(clause = "is_deleted=0")
public class Delivery extends BaseEntity {

	@Column(name = "courier")
	private String courier;

	@Column(name = "delivery_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;

	@Column(name = "guid", unique = true, nullable = false)
	private String guid;

	@ManyToMany
	private List<Order> orders;

	@Column(name = "delivery_state", nullable = false)
	@Enumerated(EnumType.STRING)
	private DeliveryState deliveryState;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@ManyToOne
	private Restaurant restaurant;

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public DeliveryState getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(DeliveryState deliveryState) {
		this.deliveryState = deliveryState;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
