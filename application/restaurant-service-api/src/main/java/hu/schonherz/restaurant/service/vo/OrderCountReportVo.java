package hu.schonherz.restaurant.service.vo;

public class OrderCountReportVo {
	
	private static final long serialVersionUID = 1L;

	String groupped;
	Long quantity;

	public String getGroupped() {
		return groupped;
	}

	public void setGroupped(String groupped) {
		this.groupped = groupped;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public OrderCountReportVo(String groupped, Long quantity) {
		super();
		this.groupped = groupped;
		this.quantity = quantity;
	}

	public OrderCountReportVo() {
		super();
	}

	

}