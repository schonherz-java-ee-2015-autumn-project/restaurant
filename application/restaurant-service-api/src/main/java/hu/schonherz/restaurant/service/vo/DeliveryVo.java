package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
public class DeliveryVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private DeliveryState deliveryState;

	private String guid;

	private String courier;

	private Date deliveryDate;

	private List<OrderVo> orders;

	public DeliveryState getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(DeliveryState deliveryState) {
		this.deliveryState = deliveryState;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

}
