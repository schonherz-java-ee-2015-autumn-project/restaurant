package hu.schonherz.restaurant.service.vo;

public class AddressReportVo<T> {
	
	private static final long serialVersionUID = 1L;

	T Address;

	public T getAddress() {
		return Address;
	}

	public void setAddress(T address) {
		Address = address;
	}

	public AddressReportVo(T address) {
		super();
		Address = address;
	}



}