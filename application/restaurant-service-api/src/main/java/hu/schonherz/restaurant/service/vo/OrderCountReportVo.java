package hu.schonherz.restaurant.service.vo;

public class OrderCountReportVo<T, S> {
	
	private static final long serialVersionUID = 1L;

	T groupped;
	S quantity;

	public T getGroupped() {
		return groupped;
	}

	public void setGroupped(T groupped) {
		this.groupped = groupped;
	}

	public S getQuantity() {
		return quantity;
	}

	public void setQuantity(S quantity) {
		this.quantity = quantity;
	}

	

}