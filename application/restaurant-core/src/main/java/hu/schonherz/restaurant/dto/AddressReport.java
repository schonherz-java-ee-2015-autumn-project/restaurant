package hu.schonherz.restaurant.dto;

public class AddressReport<T> {

	T Address;

	public T getAddress() {
		return Address;
	}

	public void setAddress(T address) {
		Address = address;
	}

	public AddressReport(T address) {
		super();
		Address = address;
	}



}