package hu.schonherz.restaurant.integration;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.lang3.Validate;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryVo;

@Stateless(mappedName = "deliveryRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class DeliveryRefresher implements RefresherLocal, RefresherRemote {

//	@WebServiceRef(wsdlLocation = "http://drake:8088/mockAdminRemoteRefreshService?WSDL")
	 @EJB
	AdminRemoteRefreshService remoteRefreshService;

	@EJB
	DeliveryServiceLocal deliveryService;

	@Override
	public void refresh() {
		List<DeliveryVo> deliveries = remoteRefreshService.refreshDeliveries();
		Validate.noNullElements(deliveries);
		for (DeliveryVo delivery : deliveries) {
			deliveryService.saveDelivery(delivery);
		}
	}

	@Override
	public void refreshSince(Date date) {
		List<DeliveryVo> deliveries = remoteRefreshService.refreshDeliveriesSince(date);
		Validate.noNullElements(deliveries);
		for (DeliveryVo delivery : deliveries) {
			deliveryService.saveDelivery(delivery);
		}
	}

}
