package hu.schonherz.restaurant.service.vo;

import java.util.Date;

public class FinancialReportVo {

	
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
	public void setDate(Object date) {
		this.date = date;
	}
	
	
	public FinancialReportVo(Long price, Object date) {
		super();
		this.price = price;
		this.date = date;
	
		
	}
	public FinancialReportVo() {
		super();
		
	}
	
	
	
}
