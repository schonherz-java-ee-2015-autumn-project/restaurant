package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
public class OrderVo implements Serializable {

	private String address;

	private Date deadline;

	private String payType;

	private List<ProductVo> products;

	private Integer totalPrice;

	private States.State orderState;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public List<ProductVo> getProducts() {
		return products;
	}

	public void setProducts(List<ProductVo> products) {
		this.products = products;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public States.State getOrderState() {
		return orderState;
	}

	public void setOrderState(States.State orderState) {
		this.orderState = orderState;
	}
}
