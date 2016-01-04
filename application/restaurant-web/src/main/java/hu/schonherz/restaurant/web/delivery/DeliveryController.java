package hu.schonherz.restaurant.web.delivery;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.ItemServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.web.vo.DeliveryItem;
import hu.schonherz.restaurant.web.vo.DeliveryListingVo;
import hu.schonherz.restaurant.web.vo.converter.ItemVosToDeliveryItemsConverter;

@ViewScoped
@ManagedBean(name = "deliveryBean")
public class DeliveryController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{deliveryTableModel}")
	private LazyDataModel<DeliveryListingVo> tableData;

	@EJB
	private DeliveryServiceLocal deliveryService;

	@EJB
	private ItemServiceLocal itemService;

	private DeliveryVo selectedDelivery;
	private DeliveryListingVo selectedDeliveryListing;
	private List<DeliveryItem> items;

	public void onRowSelect(SelectEvent e) {
		selectedDelivery = deliveryService.getDeliveryById(selectedDeliveryListing.getId());
		items = ItemVosToDeliveryItemsConverter
				.toDeliveryItems(itemService.getItemsByDeliveryId(selectedDelivery.getId()));
	}

	public String onModifyButtonClick() {
		if (selectedDelivery == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder("new?faces-redirect=true");
		sb.append("&deliveryId=").append(selectedDelivery.getId());
		sb.append("&includeViewParams=true");
		return sb.toString();
	}

	public void onDeleteButtonClick(ActionEvent e) {
		deliveryService.deleteDeliveryById(selectedDelivery.getId());
		selectedDelivery = null;
		selectedDeliveryListing = null;
	}

	public LazyDataModel<DeliveryListingVo> getTableData() {
		return tableData;
	}

	public void setTableData(LazyDataModel<DeliveryListingVo> tableData) {
		this.tableData = tableData;
	}

	public DeliveryServiceLocal getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryServiceLocal deliveryService) {
		this.deliveryService = deliveryService;
	}

	public DeliveryVo getSelectedDelivery() {
		return selectedDelivery;
	}

	public void setSelectedDelivery(DeliveryVo selectedDelivery) {
		this.selectedDelivery = selectedDelivery;
	}

	public DeliveryListingVo getSelectedDeliveryListing() {
		return selectedDeliveryListing;
	}

	public void setSelectedDeliveryListing(DeliveryListingVo selectedDeliveryListing) {
		this.selectedDeliveryListing = selectedDeliveryListing;
	}

	public ItemServiceLocal getItemService() {
		return itemService;
	}

	public void setItemService(ItemServiceLocal itemService) {
		this.itemService = itemService;
	}

	public List<DeliveryItem> getItems() {
		return items;
	}

	public void setItems(List<DeliveryItem> items) {
		this.items = items;
	}

}

