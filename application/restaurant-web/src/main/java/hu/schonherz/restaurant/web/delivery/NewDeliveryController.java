
package hu.schonherz.restaurant.web.delivery;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
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
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.ProductServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryState;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.ItemVo;
import hu.schonherz.restaurant.service.vo.OrderState;
import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.service.vo.ProductVo;
import hu.schonherz.restaurant.type.PayType;
import hu.schonherz.restaurant.validation.Validator;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.ViolationException;
import hu.schonherz.restaurant.web.UserSessionBean;
import hu.schonherz.restaurant.web.delivery.util.DeliveryUtil;

@ViewScoped
@ManagedBean(name = "newDeliveryBean")
public class NewDeliveryController implements Serializable {

	private static Logger logger = Logger.getLogger(NewDeliveryController.class);

	private static final long serialVersionUID = 1L;

	private DeliveryVo delivery;
	private OrderVo selectedOrder;
	private ItemVo selectedItem;

	// FORMS

	private String address;
	private Date orderDate;
	private PayType selectedPayType;
	private List<ItemVo> orderItems;

	private String productName;
	private String productPrice;

	private int prodQuantity;

	private boolean selected;

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

	@ManagedProperty("#{itemValidator}")
	private Validator<ItemVo> itemValidator;

	@ManagedProperty("#{out}")
	private ResourceBundle resource;

	@PostConstruct
	public void init() {
		if (userSessionBean.getUser() == null) {
			userSessionBean.init();
		}

		selected = false;
		delivery = initDelivery();
	}

	private DeliveryVo initDelivery() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		try {
			String deliveryIdParameter = externalContext.getRequestParameterMap().get("deliveryId");
			Long longId = Long.valueOf(deliveryIdParameter);
			DeliveryVo tmp = deliveryService.getDeliveryById(longId);
			if (tmp.getRestaurant().equals(userSessionBean.getUser().getRestaurant())
					&& tmp.getDeliveryState().equals(DeliveryState.FREE) && !tmp.getIsDeleted()) {
				return tmp;
			}
		} catch (Exception e) {
			logger.warn("Initializing delivery failed");
		}

		DeliveryVo delivery = null;
		delivery = new DeliveryVo();
		delivery.setDeliveryState(DeliveryState.FREE);
		delivery.setCourier("");
		delivery.setOrders(new ArrayList<>());
		delivery.setRestaurant(userSessionBean.getUser().getRestaurant());
		return delivery;
	}

	public List<String> completeProducts(String query) {
		List<ProductVo> products = productService.getProductsByNameStartingWithAndRestaurantId(query,
				userSessionBean.getUser().getRestaurant().getId());
		Set<String> set = new TreeSet<String>();
		set.addAll(products.stream().map(p -> p.getName()).collect(Collectors.toList()));
		set.addAll(DeliveryUtil.products(delivery).stream().map(p -> p.getName()).filter(pn -> pn.startsWith(query))
				.collect(Collectors.toList()));
		return new ArrayList<>(set);
	}

	public OrderVo initOrder() {
		OrderVo res = new OrderVo();
		res.setOrderState(OrderState.IN_PROGRESS);
		res.setItems(orderItems);
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

	public ItemVo initItem() {
		ItemVo res = new ItemVo();
		res.setQuantity(prodQuantity);
		return res;
	}

	public void onAddOrderButtonClick(ActionEvent e) {
		selectedItem = null;
		selectedOrder = null;
		orderItems = new ArrayList<>();
	}

	public void onSaveOrderButtonClick(ActionEvent e) {
		try {
			OrderVo order = initOrder();

			orderValidator.validate(order);

			order.setTotalPrice(
					order.getItems().stream().mapToInt(i -> i.getProduct().getPrice() * i.getQuantity()).sum());

			if (selectedOrder == null) {
				for (ItemVo itemVo : order.getItems()) {
					itemVo.setOfOrder(order);
				}
				delivery.getOrders().add(order);
			} else {
				Long idTmp = selectedOrder.getId();
				Long globalIdTmp = selectedOrder.getGlobalId();
				BeanUtils.copyProperties(selectedOrder, order);
				selectedOrder.setId(idTmp);
				selectedOrder.setGlobalId(globalIdTmp);

				for (ItemVo itemVo : selectedOrder.getItems()) {
					itemVo.setOfOrder(selectedOrder);
				}
			}

			RequestContext reqContext = RequestContext.getCurrentInstance();
			reqContext.execute("PF('newOrderW').hide();");
			reqContext.update("orderPanel");

			selectedOrder = null;
			resetOrderInput();
		} catch (ViolationException ve) {
			addMessage(ve);
		} catch (IllegalAccessException | InvocationTargetException e1) {
			// TODO
		}
	}

	private void fillOrderInput(OrderVo order) {
		address = order.getAddress();
		selectedPayType = order.getPayType();
		orderDate = order.getDeadline();
		orderItems = new ArrayList<>(order.getItems());
	}

	private void fillProductInput(ItemVo item) {
		productName = item.getProduct().getName();
		productPrice = String.valueOf(item.getProduct().getPrice());
		prodQuantity = item.getQuantity();
	}

	private void fillProductInput(ProductVo product) {
		productName = product.getName();
		productPrice = String.valueOf(product.getPrice());
	}

	private void resetOrderInput() {
		address = "";
		orderDate = new Date();
		selectedPayType = PayType.MONEY;
		orderItems = new ArrayList<>();
	}

	private void resetProductInput() {
		productName = "";
		productPrice = "";
		prodQuantity = 1;
	}

	public void onModifyOrderButtonClick(ActionEvent e) {
		fillOrderInput(selectedOrder);
	}

	public void onModifyProductButtonClick(ActionEvent e) {
		fillProductInput(selectedItem);
	}

	public void onCancelOrderButtonClick(ActionEvent e) {
		selectedOrder = null;
		resetOrderInput();
	}

	public void onCancelProductButtonClick(ActionEvent e) {
		selectedItem = null;
		resetProductInput();
	}

	public void onAddProductButtonClick(ActionEvent e) {
		selectedItem = null;
	}

	public void onSaveProductButtonClick(ActionEvent e) {
		try {
			ItemVo item = initItem();
			ProductVo prod = initProduct();

			productValidator.validate(prod);

			ProductVo productTemp = findExistingProduct(prod);
			if (productTemp != null) {

				prod = productTemp;

				if (!selected) {
					FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							resource.getString("product_already_exist"), "");

					addMessage(fmsg);
				}

			}

			item.setProduct(prod);
			itemValidator.validate(item);

			ItemVo containerItem = findProductsItemInOrderItems(prod);
			if (containerItem != null) {
				ItemVo tmp = new ItemVo();
				BeanUtils.copyProperties(tmp, containerItem);
				tmp.setQuantity(tmp.getQuantity() + item.getQuantity());
				itemValidator.validate(tmp);
				BeanUtils.copyProperties(containerItem, tmp);
			} else if (selectedItem != null) {
				Long idTemp = selectedItem.getId();
				Long globalIdTemp = selectedItem.getGlobalId();
				BeanUtils.copyProperties(selectedItem, item);
				selectedItem.setId(idTemp);
				selectedItem.setGlobalId(globalIdTemp);
			} else {
				orderItems.add(item);
			}

			RequestContext reqContext = RequestContext.getCurrentInstance();
			reqContext.execute("PF('newProductW').hide();");
			reqContext.update("orderDetails");

			resetProductInput();
			selectedItem = null;
			selected = false;
		} catch (ViolationException ve) {
			addMessage(ve);
		} catch (NumberFormatException nfe) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, resource.getString("value_must_be_int"), ""));
		} catch (IllegalAccessException | InvocationTargetException e1) {
			// TODO addMessage
			logger.error("BeanUtils exception", e1);
		}
	}

	private ItemVo findProductsItemInOrderItems(ProductVo prod) {
		for (ItemVo itemVo : orderItems) {
			if (itemVo.getProduct().equals(prod) && itemVo != selectedItem) {
				return itemVo;
			}
		}
		return null;
	}

	private ProductVo findExistingProduct(ProductVo prod) {
		ProductVo productTemp = productService.getProductByNameAndRestaurantId(prod.getName(),
				prod.getRestaurant().getId());

		if (productTemp == null) {
			for (OrderVo order : delivery.getOrders()) {
				if (order != selectedOrder) {

					for (ItemVo itemVo : order.getItems()) {
						if (itemVo.getProduct().equals(prod)) {
							return itemVo.getProduct();
						}
					}

				}
			}
		}

		return productTemp;
	}

	public void onOrderRowSelect(SelectEvent e) {
		selectedOrder = (OrderVo) e.getObject();
		fillOrderInput(selectedOrder);
	}

	public void onProductRowSelect(SelectEvent e) {
		selectedItem = (ItemVo) e.getObject();
		fillProductInput(selectedItem);
	}

	public void onOrderRowDelete(ActionEvent e) {
		selectedOrder = null;
		OrderVo deleted = (OrderVo) e.getComponent().getAttributes().get("deleted");
		delivery.getOrders().remove(deleted);
	}

	public void onProductRowDelete(ActionEvent e) {
		selectedItem = null;
		ItemVo deleted = (ItemVo) e.getComponent().getAttributes().get("deleted");
		orderItems.remove(deleted);
	}

	public void onProductSelect(SelectEvent e) {
		ProductVo prod = productService.getProductByNameAndRestaurantId((String) e.getObject(),
				userSessionBean.getUser().getRestaurant().getId());

		if (prod == null) {
			prod = DeliveryUtil.getProductByNameAndRestaurantId(delivery, (String) e.getObject(),
					userSessionBean.getUser().getRestaurant().getId());
		}

		fillProductInput(prod);

		selected = true;

		RequestContext reqContext = RequestContext.getCurrentInstance();
		reqContext.update("productDetails");

	}

	public void onProductNameInputChange(AjaxBehaviorEvent e) {
		selected = false;
	}

	public String saveDelivery() {
		try {
			deliveryValidator.validate(delivery);
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

	public ItemVo getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ItemVo selectedItem) {
		this.selectedItem = selectedItem;
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

	public List<ItemVo> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<ItemVo> orderItems) {
		this.orderItems = orderItems;
	}

	public ProductServiceLocal getProductService() {
		return productService;
	}

	public void setProductService(ProductServiceLocal productService) {
		this.productService = productService;
	}

	public int getProdQuantity() {
		return prodQuantity;
	}

	public void setProdQuantity(int prodQuantity) {
		this.prodQuantity = prodQuantity;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Validator<ItemVo> getItemValidator() {
		return itemValidator;
	}

	public void setItemValidator(Validator<ItemVo> itemValidator) {
		this.itemValidator = itemValidator;
	}

}
