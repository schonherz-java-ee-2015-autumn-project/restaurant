package hu.schonherz.restaurant.web.delivery;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.ProductServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryState;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.OrderState;
import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.service.vo.ProductVo;
import hu.schonherz.restaurant.type.PayType;
import hu.schonherz.restaurant.validation.Validator;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.ViolationException;
import hu.schonherz.restaurant.web.UserSessionBean;
import hu.schonherz.restaurant.web.generator.GuidGenerator;

@ViewScoped
@ManagedBean(name = "newDeliveryBean")
public class NewDeliveryController implements Serializable {

	private static Logger logger = Logger.getLogger(NewDeliveryController.class);

	private static final long serialVersionUID = 1L;

	private DeliveryVo delivery;
	private OrderVo selectedOrder;
	private ProductVo selectedProduct;

	// FORMS

	private String address;
	private Date orderDate;
	private PayType selectedPayType;
	private List<ProductVo> orderProducts;

	private String productName;
	private String productPrice;

	@EJB
	private DeliveryServiceLocal deliveryService;

	@EJB
	private ProductServiceLocal productService;

	@ManagedProperty("#{userSessionBean}")
	private UserSessionBean userSessionBean;

	@ManagedProperty("#{deliveryValidator}")
	private Validator<DeliveryVo> deliveryValidator;

	@ManagedProperty("#{orderValidator}")
	private Validator<OrderVo> orderValidator;

	@ManagedProperty("#{productValidator}")
	private Validator<ProductVo> productValidator;

	@ManagedProperty("#{out}")
	private ResourceBundle resource;

	@PostConstruct
	public void init() {
		if (userSessionBean.getUser() == null) {
			userSessionBean.init();
		}

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		if (externalContext.getRequestParameterMap().get("deliveryId") == null) {
			delivery = new DeliveryVo();
			delivery.setDeliveryState(DeliveryState.FREE);
			delivery.setCourier("");
			delivery.setOrders(new ArrayList<>());
		} else {
			delivery = deliveryService.getDeliveryByGuid(externalContext.getRequestParameterMap().get("deliveryId"));
		}

	}

	public List<String> completeProducts(String query) {
		List<ProductVo> products = productService.getProductsByNameStartingWithAndRestaurantId(query,
				userSessionBean.getUser().getRestaurant().getId());

		return products.stream().map(p -> p.getName()).collect(Collectors.toList());
	}

	public OrderVo initOrder() {
		OrderVo res = new OrderVo();
		res.setOrderState(OrderState.IN_PROGRESS);
		res.setProducts(orderProducts);
		res.setPayType(selectedPayType);
		res.setAddress(address);
		res.setDeadline(orderDate);
		return res;
	}

	public ProductVo initProduct() {
		ProductVo res = new ProductVo();
		res.setName(productName);
		res.setPrice(Integer.valueOf(productPrice));
		res.setRestaurant(userSessionBean.getUser().getRestaurant());
		return res;
	}

	public void onAddOrderButtonClick(ActionEvent e) {
		selectedProduct = null;
		selectedOrder = null;
		orderProducts = new ArrayList<>();
	}

	public void onSaveOrderButtonClick(ActionEvent e) {
		try {
			OrderVo order = initOrder();

			orderValidator.validate(order);

			order.setTotalPrice(order.getProducts().stream().mapToInt(p -> p.getPrice()).sum());

			if (selectedOrder == null) {
				delivery.getOrders().add(order);
			} else {
				selectedOrder.setAddress(order.getAddress());
				selectedOrder.setDeadline(order.getDeadline());
				selectedOrder.setOrderState(order.getOrderState());
				selectedOrder.setPayType(order.getPayType());
				selectedOrder.setProducts(order.getProducts());
				selectedOrder.setTotalPrice(order.getTotalPrice());
			}

			RequestContext reqContext = RequestContext.getCurrentInstance();
			reqContext.execute("PF('newOrderW').hide();");
			reqContext.update("orderPanel");

			selectedOrder = null;
			resetOrderInput();
		} catch (ViolationException ve) {
			addMessage(ve);
		}
	}

	private void fillOrderInput(OrderVo order) {
		address = order.getAddress();
		selectedPayType = order.getPayType();
		orderDate = order.getDeadline();
		orderProducts = new ArrayList<>(order.getProducts());
	}

	private void fillProductInput(ProductVo product) {
		productName = product.getName();
		productPrice = String.valueOf(product.getPrice());
	}

	private void resetOrderInput() {
		address = "";
		orderDate = new Date();
		selectedPayType = PayType.MONEY;
		orderProducts = new ArrayList<>();
	}

	private void resetProductInput() {
		productName = "";
		productPrice = "";
	}

	public void onModifyOrderButtonClick(ActionEvent e) {
		fillOrderInput(selectedOrder);
	}

	public void onModifyProductButtonClick(ActionEvent e) {
		fillProductInput(selectedProduct);
	}

	public void onCancelOrderButtonClick(ActionEvent e) {
		selectedOrder = null;
		resetOrderInput();
	}

	public void onCancelProductButtonClick(ActionEvent e) {
		selectedProduct = null;
		resetProductInput();
	}

	public void onAddProductButtonClick(ActionEvent e) {
		selectedProduct = null;
	}

	public void onSaveProductButtonClick(ActionEvent e) {
		try {
			ProductVo prod = initProduct();

			productValidator.validate(prod);

			ProductVo productTemp = productService.getProductByNameAndRestaurantId(prod.getName(),
					prod.getRestaurant().getId());

			if (productTemp != null) {
				FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						resource.getString("product_already_exist"), "");

				BeanUtils.copyProperties(prod, productTemp);
				addMessage(fmsg);

			}

			if (selectedProduct != null) {
				BeanUtils.copyProperties(selectedProduct, prod);
			} else {
				orderProducts.add(prod);
			}

			RequestContext reqContext = RequestContext.getCurrentInstance();
			reqContext.execute("PF('newProductW').hide();");
			reqContext.update("orderDetails");

			resetProductInput();
			selectedProduct = null;
		} catch (ViolationException ve) {
			addMessage(ve);
		} catch (NumberFormatException nfe) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, resource.getString("value_must_be_int"), ""));
		} catch (IllegalAccessException | InvocationTargetException e1) {
			// TODO addMessage
			logger.error("BeanUtils exception", e1);
		}
	}

	public void onOrderRowSelect(SelectEvent e) {
		selectedOrder = (OrderVo) e.getObject();
		fillOrderInput(selectedOrder);
	}

	public void onProductRowSelect(SelectEvent e) {
		selectedProduct = (ProductVo) e.getObject();
	}

	public void onOrderRowDelete(ActionEvent e) {
		selectedOrder = null;
		OrderVo deleted = (OrderVo) e.getComponent().getAttributes().get("deleted");
		delivery.getOrders().remove(deleted);
	}

	public void onProductRowDelete(ActionEvent e) {
		selectedProduct = null;
		ProductVo deleted = (ProductVo) e.getComponent().getAttributes().get("deleted");
		orderProducts.remove(deleted);
	}

	public void onProductSelect(SelectEvent e) {
		ProductVo prod = productService.getProductByNameAndRestaurantId((String) e.getObject(),
				userSessionBean.getUser().getRestaurant().getId());
		try {
			if (selectedProduct == null) {
				orderProducts.add(prod);
			} else {
				BeanUtils.copyProperties(selectedProduct, prod);
			}

			RequestContext reqContext = RequestContext.getCurrentInstance();
			reqContext.execute("PF('newProductW').hide();");
			reqContext.update("orderDetails");

			selectedProduct = null;
		} catch (IllegalAccessException | InvocationTargetException e1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resource.getString("cant_modify_product"),
					"");
			addMessage(msg);
		}

	}

	public String saveDelivery() {
		try {
			deliveryValidator.validate(delivery);

			if (delivery.getGuid() == null || "".equals(delivery.getGuid())) {
				delivery.setGuid(GuidGenerator.generate(userSessionBean.getUser().getRestaurant()));
			}
			deliveryService.saveDelivery(delivery);
			return "list?faces-redirect=true";
		} catch (ViolationException ve) {
			addMessage(ve);
		}
		return null;
	}

	public void addMessage(FacesMessage fm) {
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	protected void addMessage(ViolationException ve) {
		for (Violation e : ve.getViolations()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resource.getString(e.getMessage()),
					e.getDetails());
			addMessage(msg);
		}
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

	public PayType getSelectedPayType() {
		return selectedPayType;
	}

	public void setSelectedPayType(PayType selectedPayType) {
		this.selectedPayType = selectedPayType;
	}

	public Validator<OrderVo> getOrderValidator() {
		return orderValidator;
	}

	public void setOrderValidator(Validator<OrderVo> orderValidator) {
		this.orderValidator = orderValidator;
	}

	public ResourceBundle getResource() {
		return resource;
	}

	public void setResource(ResourceBundle resource) {
		this.resource = resource;
	}

	public Validator<ProductVo> getProductValidator() {
		return productValidator;
	}

	public void setProductValidator(Validator<ProductVo> productValidator) {
		this.productValidator = productValidator;
	}

	public Validator<DeliveryVo> getDeliveryValidator() {
		return deliveryValidator;
	}

	public void setDeliveryValidator(Validator<DeliveryVo> deliveryValidator) {
		this.deliveryValidator = deliveryValidator;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<ProductVo> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<ProductVo> orderProducts) {
		this.orderProducts = orderProducts;
	}

}
