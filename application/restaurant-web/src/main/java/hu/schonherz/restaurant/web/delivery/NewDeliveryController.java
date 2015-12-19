package hu.schonherz.restaurant.web.delivery;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.service.vo.States.State;

@ViewScoped
@ManagedBean(name = "newDeliveryBean")
public class NewDeliveryController implements Serializable {

	private static final long serialVersionUID = 1L;

	private DeliveryVo delivery;
	private OrderVo selectedOrder;

	private boolean canModify;

	@PostConstruct
	public void init() {
		delivery = new DeliveryVo();
		delivery.setDeliveryState(State.AVAILABLE);
		delivery.setCourier("");
		delivery.setOrders(new ArrayList<>());

		selectedOrder = new OrderVo();
		selectedOrder.setOrderState(State.AVAILABLE);
		selectedOrder.setProducts(new ArrayList<>());

		canModify = false;
	}

	public void onOrderRowSelect(SelectEvent e) {
		canModify = true;
	}

	public void onOrderRowUnselect(SelectEvent e) {
		canModify = false;
	}

	public void onOrderRowDelete(ActionEvent e) {
		OrderVo deleted = (OrderVo) e.getComponent().getAttributes().get("deleted");
		delivery.getOrders().remove(deleted);
	}

	public String saveDelivery() {
		if (!delivery.getOrders().isEmpty()) {
			return "index?faces-redirect=true";
		}
		return null;
	}

	public DeliveryVo getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryVo delivery) {
		this.delivery = delivery;
	}

	public OrderVo getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(OrderVo selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public boolean isCanModify() {
		return canModify;
	}

	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}

}
