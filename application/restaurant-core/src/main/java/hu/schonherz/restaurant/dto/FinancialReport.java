package hu.schonherz.restaurant.dto;

import java.io.Serializable;
import java.util.Date;

public class FinancialReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer price;
	Date date;
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public FinancialReport(Integer price, Date date) {
		super();
		this.price = price;
		this.date = date;
	}
	
	

}
