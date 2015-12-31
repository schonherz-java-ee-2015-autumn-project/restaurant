package hu.schonherz.restaurant.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class OrderCountReport implements Serializable{
	
	private static final long   serialVersionUID    = 1L;

	Object groupped;
	Long quantity;

	public Object getGroupped() {
		
		return groupped;
	}

	public void setGroupped(Object groupped) {
		this.groupped = groupped;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public OrderCountReport(Object groupped, Long quantity) {
		super();
		this.groupped = groupped;
		this.quantity = quantity;
	}

	public OrderCountReport() {
		super();
	}
	
}