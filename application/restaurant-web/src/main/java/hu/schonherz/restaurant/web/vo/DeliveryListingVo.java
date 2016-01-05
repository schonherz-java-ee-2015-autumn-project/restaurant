package hu.schonherz.restaurant.web.vo;

import java.io.Serializable;

import hu.schonherz.restaurant.service.vo.DeliveryState;

public class DeliveryListingVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long globalId;

	private String courierName;

	private int numberOfAddresses;

	private int total;

	private DeliveryState state;

	public DeliveryListingVo() {
	}

	public DeliveryListingVo(Long id, Long guid, String courierName, int numberOfAddresses, int total,
			DeliveryState state) {
		super();
		this.id = id;
		this.globalId = guid;
		this.courierName = courierName;
		this.numberOfAddresses = numberOfAddresses;
		this.total = total;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Long globalId) {
		this.globalId = globalId;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public DeliveryState getState() {
		return state;
	}

	public void setState(DeliveryState state) {
		this.state = state;
	}

}
