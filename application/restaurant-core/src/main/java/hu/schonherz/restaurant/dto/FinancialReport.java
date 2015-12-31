package hu.schonherz.restaurant.dto;

import java.io.Serializable;
import java.util.Date;

public class FinancialReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long price;
	Object date;
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public FinancialReport(Object date, Long price) {
		super();
		this.price = price;
		this.date = date;
	}
	public FinancialReport() {
		super();
	}
	
	

}
