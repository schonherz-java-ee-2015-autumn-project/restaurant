package hu.schonherz.restaurant.dto;

import java.io.Serializable;

public class AddressReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String Address;

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public AddressReport(String address) {
		super();
		Address = address;
	}



}