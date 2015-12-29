package hu.schonherz.restaurant.service.vo;

import java.util.Date;

public class FinancialReportVo {

	
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
	
	
	public FinancialReportVo(Integer price, Date date) {
		super();
		this.price = price;
		this.date = date;
	}
	
	
}
