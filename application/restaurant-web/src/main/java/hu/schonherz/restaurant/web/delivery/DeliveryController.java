package hu.schonherz.restaurant.web.delivery;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import hu.schonherz.restaurant.web.vo.DeliveryListingVo;

@ViewScoped
@ManagedBean(name = "deliveryBean")
public class DeliveryController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{deliveryTableModel}")
	private LazyDataModel<DeliveryListingVo> tableData;

	private DeliveryListingVo selectedDelivery;

	public void onRowSelect(SelectEvent e) {
		// TODO
		selectedDelivery = (DeliveryListingVo) e.getObject();
	}

	public DeliveryListingVo getSelectedDelivery() {
		return selectedDelivery;
	}

	public void setSelectedDelivery(DeliveryListingVo selectedDelivery) {
		this.selectedDelivery = selectedDelivery;
	}

	public LazyDataModel<DeliveryListingVo> getTableData() {
		return tableData;
	}

	public void setTableData(LazyDataModel<DeliveryListingVo> tableData) {
		this.tableData = tableData;
	}

}
