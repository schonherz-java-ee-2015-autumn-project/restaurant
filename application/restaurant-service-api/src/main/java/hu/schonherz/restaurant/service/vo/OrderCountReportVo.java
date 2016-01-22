package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;

public class OrderCountReportVo implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2892204695125157395L;
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

	

	public OrderCountReportVo(Object groupped, Long quantity) {
		super();
		this.groupped = groupped;
		this.quantity = quantity;
	}

	public OrderCountReportVo() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupped == null) ? 0 : groupped.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderCountReportVo other = (OrderCountReportVo) obj;
		if (groupped == null) {
			if (other.groupped != null)
				return false;
		} else if (!groupped.equals(other.groupped))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	

}