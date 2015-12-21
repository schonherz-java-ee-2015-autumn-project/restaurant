package hu.schonherz.restaurant.dto;

public class OrderCountReport<T, S> {

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

	public OrderCountReport(T groupped, S quantity) {
		super();
		this.groupped = groupped;
		this.quantity = quantity;
	}

}