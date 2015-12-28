package hu.schonherz.restaurant.integration;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.service.vo.UserVo;

@WebService
public interface AdminRemoteRefreshService {
	
	/**
	 * 
	 * @return the list of the users modified since we last asked
	 */
	@WebMethod(operationName="refreshUsers")
	@WebResult(name="refreshUsersResult")
	public List<UserVo> refreshUsers();

	/**
	 * 
	 * @return the list of the deliveries modified since we last asked
	 */
	@WebMethod(operationName="refreshDeliveries")
	@WebResult(name="refreshDeliveriesResult")
	public List<DeliveryVo> refreshDeliveries();

	/**
	 * 
	 * @param date
	 * @return the list of the users modified since <param>date</param>
	 */
	@WebMethod(operationName="refreshUsersSince")
	@WebResult(name="refreshUsersSinceResult")
	public List<UserVo> refreshUsersSince(@WebParam(name="date")Date date);

	/**
	 * 
	 * @param date
	 * @return the list of the deliveris modified since <param>date</param>
	 */
	@WebMethod(operationName="refreshDeliveriesSince")
	@WebResult(name="refreshDeliveriesSinceResult")
	public List<DeliveryVo> refreshDeliveriesSince(Date date);

	/**
	 * 
	 * @param delivery
	 *            a new delivery to be placed
	 * @return the error code of the method (0 if successful, 1 if some error
	 *         happened)
	 */
	@WebMethod(operationName="palceNewDelivery")
	@WebResult(name="placeNewDeliveryErrorCode")
	public Long placeNewDelivery(@WebParam(name="delivery")DeliveryVo delivery);

	/**
	 * 
	 * @param delivery
	 *            sets a delivery to canceled status
	 * @return the error code of the method (0 if successful, 1 if some error
	 *         happened)
	 */
	@WebMethod(operationName="deleteDelivery")
	@WebResult(name="deleteDeliveryErrorCode")
	public Long deleteDelivery(@WebParam(name="delivery")DeliveryVo delivery);

}
