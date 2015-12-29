package hu.schonherz.restaurant.service.vo;

public class AddressReportVo {
	
	private static final long serialVersionUID = 1L;

	String Address;

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public AddressReportVo(String address) {
		super();
		Address = address;
	}



}