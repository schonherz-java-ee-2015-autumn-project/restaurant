package hu.schonherz.restaurant.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderCountReport implements Serializable{
	
	private static final long   serialVersionUID    = 1L;

	Date groupped;
	Long quantity;

	public Date getGroupped() {
		return groupped;
	}

	public void setGroupped(Date groupped) {
		this.groupped = groupped;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public OrderCountReport(Date groupped, Long quantity) {
		super();
		this.groupped = groupped;
		this.quantity = quantity;
	}

}