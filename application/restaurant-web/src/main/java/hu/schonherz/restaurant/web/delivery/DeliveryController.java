package hu.schonherz.restaurant.web.delivery;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.web.vo.DeliveryListingVo;

@ViewScoped
@ManagedBean(name = "deliveryBean")
public class DeliveryController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{deliveryTableModel}")
	private LazyDataModel<DeliveryListingVo> tableData;

	@EJB
	private DeliveryServiceLocal deliveryService;

	private DeliveryVo selectedDelivery;
	private DeliveryListingVo selectedDeliveryListing;

	public void onRowSelect(SelectEvent e) {
		selectedDelivery = deliveryService.getDeliveryByGuid(selectedDeliveryListing.getGuid());
	}

	public String onModifyButtonClick() {
		if (selectedDelivery == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder("new?faces-redirect=true");
		sb.append("&deliveryId=").append(selectedDelivery.getGuid());
		sb.append("&includeViewParams=true");
		return sb.toString();
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

}
