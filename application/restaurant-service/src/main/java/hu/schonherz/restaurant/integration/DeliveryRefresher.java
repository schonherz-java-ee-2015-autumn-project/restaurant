package hu.schonherz.restaurant.integration;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.vo.DeliveryVo;

@Stateless(mappedName = "deliveryRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class DeliveryRefresher implements RefresherLocal, RefresherRemote {
	
	Logger logger = Logger.getLogger("DeliveryRefresher");

	@WebServiceRef(wsdlLocation="http://drake:8088/mockAdminRemoteRefreshService?WSDL")
//	@EJB
	AdminRemoteRefreshService remoteRefreshService;
	
	@EJB
	DeliveryServiceLocal deliveryService;

	@Override
	public void refresh() {
		List<DeliveryVo> deliveries = remoteRefreshService.refreshDeliveries();
		for (DeliveryVo delivery : deliveries) {
			deliveryService.saveDelivery(delivery);
		}
		logger.info("Delivery refresh done successfuly!");
	}

	@Override
	public void refreshSince(Date date) {
		List<DeliveryVo> deliveries = remoteRefreshService.refreshDeliveriesSince(date);
		for (DeliveryVo delivery : deliveries) {
			deliveryService.saveDelivery(delivery);
		}
	}

}
