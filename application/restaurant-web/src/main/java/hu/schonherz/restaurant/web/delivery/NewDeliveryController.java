package hu.schonherz.restaurant.web.delivery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.service.vo.ProductVo;
import hu.schonherz.restaurant.service.vo.States.State;
import hu.schonherz.restaurant.type.PayType;
import hu.schonherz.restaurant.web.UserSessionBean;
import hu.schonherz.restaurant.web.generator.GuidGenerator;

@ViewScoped
@ManagedBean(name = "newDeliveryBean")
public class NewDeliveryController implements Serializable {

	private static final long serialVersionUID = 1L;

	private DeliveryVo delivery;
	private OrderVo selectedOrder;
	private ProductVo selectedProduct;
	private PayType selectedPayType;

	private boolean canModify;
	private boolean canModifyProduct;

	@EJB
	private DeliveryServiceLocal deliveryService;

	@ManagedProperty("#{userSessionBean}")
	private UserSessionBean userSessionBean;

	@ManagedProperty("#{out}")
	private ResourceBundle resource;

	@PostConstruct
	public void init() {
		if (userSessionBean.getUser() == null) {
			userSessionBean.init();
		}

		delivery = new DeliveryVo();
		delivery.setDeliveryState(State.AVAILABLE);
		delivery.setCourier("");
		delivery.setOrders(new ArrayList<>());

		canModify = false;
		canModifyProduct = false;
	}

	public void initOrder() {
		selectedOrder = new OrderVo();
		selectedOrder.setOrderState(State.AVAILABLE);
		selectedOrder.setProducts(new ArrayList<>());
	}

	public void initProduct() {
		selectedProduct = new ProductVo();
		selectedProduct.setName("");
		selectedProduct.setPrice(0);
	}

	public void onAddOrderButtonClick(ActionEvent e) {
		initOrder();
		selectedProduct = null;
		canModify = false;
	}

	public void onSaveOrderButtonClick(ActionEvent e) {
		if ("".equals(selectedOrder.getAddress().trim()) || selectedOrder.getDeadline().before(new Date())
				|| selectedOrder.getProducts().isEmpty()) {
			// TODO add error message
			return;
		}

		selectedOrder.setPayType(selectedPayType);
		selectedOrder.setTotalPrice(selectedOrder.getProducts().stream().mapToInt(p -> p.getPrice()).sum());
		delivery.getOrders().add(selectedOrder);

		RequestContext reqContext = RequestContext.getCurrentInstance();
		reqContext.execute("PF('newOrderW').hide();");
		reqContext.update("orderPanel");

		selectedOrder = null;
	}

	public void onCancelOrderButtonClick(ActionEvent e) {
		canModify = false;
		selectedOrder = null;
	}

	public void onAddProductButtonClick(ActionEvent e) {
		canModifyProduct = false;
		initProduct();
	}

	public void onSaveProductButtonClick(ActionEvent e) {
		if ("".equals(selectedProduct.getName().trim()) || selectedProduct.getPrice() < 0) {
			// TODO error message
			return;
		}

		selectedOrder.getProducts().add(selectedProduct);

		RequestContext reqContext = RequestContext.getCurrentInstance();
		reqContext.execute("PF('newProductW').hide();");
		reqContext.update("orderDetails");

		selectedProduct = null;
	}

	public void onOrderRowSelect(SelectEvent e) {
		selectedPayType = selectedOrder.getPayType();
		canModify = true;
	}

	public void onProductRowSelect(SelectEvent e) {
		canModifyProduct = true;
	}

	public void onOrderRowDelete(ActionEvent e) {
		OrderVo deleted = (OrderVo) e.getComponent().getAttributes().get("deleted");
		delivery.getOrders().remove(deleted);
	}

	public String saveDelivery() {
		if (!delivery.getOrders().isEmpty()) {
			if (delivery.getGuid() == null || "".equals(delivery.getGuid())) {
				delivery.setGuid(GuidGenerator.generate(userSessionBean.getUser().getRestaurant()));
			}
			deliveryService.saveDelivery(delivery);
			return "index?faces-redirect=true";
		}
		return null;
	}

	public String payTypeLabel(PayType pt) {
		return resource.getString(pt.name().toLowerCase());
	}

	public PayType[] getPayTypes() {
		return PayType.values();
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

	public boolean isCanModifyProduct() {
		return canModifyProduct;
	}

	public void setCanModifyProduct(boolean canModifyProduct) {
		this.canModifyProduct = canModifyProduct;
	}

	public ProductVo getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(ProductVo selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public DeliveryServiceLocal getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryServiceLocal deliveryService) {
		this.deliveryService = deliveryService;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	public ResourceBundle getResource() {
		return resource;
	}

	public void setResource(ResourceBundle resource) {
		this.resource = resource;
	}

	public PayType getSelectedPayType() {
		return selectedPayType;
	}

	public void setSelectedPayType(PayType selectedPayType) {
		this.selectedPayType = selectedPayType;
	}

}
