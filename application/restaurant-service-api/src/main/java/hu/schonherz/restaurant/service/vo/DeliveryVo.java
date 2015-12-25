package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
public class DeliveryVo implements Serializable {

	private State deliveryState;

	private static final long serialVersionUID = 1L;

	private Long id;

	private String guid;

	private String courier;

	private Date deliveryDate;

	private List<OrderVo> orders;

	public State getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(State deliveryState) {
		this.deliveryState = deliveryState;
	}

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

	public List<OrderVo> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderVo> orders) {
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

}
