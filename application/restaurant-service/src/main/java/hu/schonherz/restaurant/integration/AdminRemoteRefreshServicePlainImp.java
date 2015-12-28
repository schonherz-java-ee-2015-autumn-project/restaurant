package hu.schonherz.restaurant.integration;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.UserVo;

@Stateless
@Local(AdminRemoteRefreshService.class)
@Remote(AdminRemoteRefreshService.class)
@WebService(endpointInterface="hu.schonherz.restaurant.integration.AdminRemoteRefreshService")
public class AdminRemoteRefreshServicePlainImp implements AdminRemoteRefreshService{

	@Override
	public List<UserVo> refreshUsers() {
		return Collections.<UserVo>emptyList();
	}

	@Override
	public List<DeliveryVo> refreshDeliveries() {
		return Collections.<DeliveryVo>emptyList();
	}

	@Override
	public List<UserVo> refreshUsersSince(Date date) {
		return Collections.<UserVo>emptyList();	
	}

	@Override
	public List<DeliveryVo> refreshDeliveriesSince(Date date) {
		return Collections.<DeliveryVo>emptyList();
	}

	@Override
	public Long placeNewDelivery(DeliveryVo delivery) {
		return 0L;
	}

	@Override
	public Long deleteDelivery(DeliveryVo delivery) {
		return 0L;
	}

}
