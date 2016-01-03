package hu.schonherz.restaurant.dto;

import java.io.Serializable;

import hu.schonherz.restaurant.type.PayType;

public class PayTypeReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PayType payType;
	Long price;
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public PayTypeReport(PayType payType, Long price) {
		super();
		this.payType = payType;
		this.price = price;
	}
	public PayTypeReport() {
		super();
		
	}
	
	

}
