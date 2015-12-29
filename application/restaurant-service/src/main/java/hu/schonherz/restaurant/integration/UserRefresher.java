package hu.schonherz.restaurant.integration;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.validator.Validator;
import javax.xml.ws.WebServiceRef;

import org.apache.commons.lang3.Validate;

import hu.schonherz.restaurant.service.UserServiceLocal;
import hu.schonherz.restaurant.service.vo.UserVo;

@Stateless(mappedName = "userRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class UserRefresher implements RefresherLocal, RefresherRemote {

	Logger logger = Logger.getLogger("UserRefresher");

	@WebServiceRef(wsdlLocation = "http://drake:8088/mockAdminRemoteRefreshService?WSDL")
	// @EJB
	AdminRemoteRefreshService remoteRefreshService;

	@EJB
	UserServiceLocal userService;

	@Override
	public void refresh() {
		List<UserVo> users = remoteRefreshService.refreshUsers();
		Validate.noNullElements(users);
		for (UserVo user : users) {
			userService.save(user);
		}
		logger.info("User refresh done successfuly!");
	}

	@Override
	public void refreshSince(Date date) {
		List<UserVo> users = remoteRefreshService.refreshUsersSince(date);
		Validate.noNullElements(users);
		for (UserVo user : users) {
			userService.save(user);
		}
	}

}
