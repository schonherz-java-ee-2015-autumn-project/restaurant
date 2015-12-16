package hu.schonherz.restaurant.web.vo;

import java.io.Serializable;

public class DeliveryListingVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String guid;

	private String status;

	private String courierName;

	private int numberOfAddresses;

	private double total;

	public DeliveryListingVo() {
	}

	public DeliveryListingVo(String guid, String status, String courierName, int numberOfAddresses, double total) {
		super();
		this.guid = guid;
		this.status = status;
		this.courierName = courierName;
		this.numberOfAddresses = numberOfAddresses;
		this.total = total;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
