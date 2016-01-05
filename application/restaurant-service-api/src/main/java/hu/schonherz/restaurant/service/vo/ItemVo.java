package hu.schonherz.restaurant.service.vo;

public class ItemVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private ProductVo product;

	private OrderVo ofOrder;

	private Integer quantity;

	private Long globalId;

	public OrderVo getOfOrder() {
		return ofOrder;
	}

	public void setOfOrder(OrderVo ofOrder) {
		this.ofOrder = ofOrder;
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

	public Long getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Long globalId) {
		this.globalId = globalId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ItemVo)) {
			return false;
		}
		ItemVo other = (ItemVo) obj;
		if (product == null) {
			if (other.product != null) {
				return false;
			}
		} else if (!product.equals(other.product)) {
			return false;
		}
		if (quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!quantity.equals(other.quantity)) {
			return false;
		}
		return true;
	}

}
