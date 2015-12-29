package hu.schonherz.restaurant.web.vo;

import java.io.Serializable;

import hu.schonherz.restaurant.service.vo.DeliveryState;

public class DeliveryListingVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String guid;

	private String courierName;

	private int numberOfAddresses;

	private double total;

	private DeliveryState state;

	public DeliveryListingVo() {
	}

	public DeliveryListingVo(String guid, String courierName, int numberOfAddresses, double total,
			DeliveryState state) {
		super();
		this.guid = guid;
		this.courierName = courierName;
		this.numberOfAddresses = numberOfAddresses;
		this.total = total;
		this.state = state;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public int getNumberOfAddresses() {
		return numberOfAddresses;
	}

	public void setNumberOfAddresses(int numberOfAddresses) {
		this.numberOfAddresses = numberOfAddresses;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public DeliveryState getState() {
		return state;
	}

	public void setState(DeliveryState state) {
		this.state = state;
	}

}
