
package hu.schonherz.restaurant.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hu.schonherz.restaurant.type.PayType;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "deadline")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadline;

	@Enumerated(EnumType.STRING)
	@Column(name = "pay_type")
	private PayType payType;

	@OneToMany(mappedBy = "ofOrder")
	private List<Item> items;

	@Column(name = "total_price", length = 50)
	private Integer totalPrice;

	@Column(name = "order_state", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderState orderState;

	@Column(name = "global_id", unique = true)
	private Long globalId;

	public OrderState getOrderState() {
		return orderState;
	}

	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Long globalId) {
		this.globalId = globalId;
	}

}
