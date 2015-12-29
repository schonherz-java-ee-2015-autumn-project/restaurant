package hu.schonherz.restaurant.service.vo;

public class ItemVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private OrderVo order;

	private ProductVo product;

	private Integer quantity;

	public OrderVo getOrder() {
		return order;
	}

	public void setOrder(OrderVo order) {
		this.order = order;
	}

	public ProductVo getProduct() {
		return product;
	}

	public void setProduct(ProductVo product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
