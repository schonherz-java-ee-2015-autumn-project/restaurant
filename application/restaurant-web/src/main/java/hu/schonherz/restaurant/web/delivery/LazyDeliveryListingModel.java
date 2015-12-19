package hu.schonherz.restaurant.web.delivery;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.converter.Converter;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.web.vo.DeliveryListingVo;

@Service("deliveryTableModel")
public class LazyDeliveryListingModel extends LazyDataModel<DeliveryListingVo> {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(LazyDeliveryListingModel.class);

	@Autowired
	private Converter<DeliveryVo, DeliveryListingVo> deliveryListingConverter;

	@EJB
	private DeliveryServiceLocal deliveryService;

	private List<DeliveryListingVo> data;

	@Override
	public List<DeliveryListingVo> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		String filter = "";
		String filterColumnName = "";
		if (filters.keySet().size() > 0) {
			filter = (String) filters.values().toArray()[0];
			filterColumnName = filters.keySet().iterator().next();
		}

		int dir = sortOrder.equals(SortOrder.ASCENDING) ? 1 : 2;

		data = deliveryListingConverter.convert(
				deliveryService.getDeliveries(first / pageSize, pageSize, sortField, dir, filter, filterColumnName));

		int size = deliveryService.getDeliveryCount();

		setRowCount(size);

		return data;
	}

	@Override
	public DeliveryListingVo getRowData(String rowKey) {
		if (rowKey == null) {
			logger.warn("Null rowkey!");
			return null;
		}

		for (DeliveryListingVo listingVo : data) {
			if (listingVo.getGuid().equals(rowKey)) {
				return listingVo;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(DeliveryListingVo object) {
		return object.getGuid();
	}

}