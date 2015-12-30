package hu.schonherz.restaurant.integration;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import hu.schonherz.administrator.NotAllowedRoleException_Exception;
import hu.schonherz.administrator.SynchronizationService;
import hu.schonherz.administrator.UserRole;
import hu.schonherz.administrator.WebUserDTO;

@Stateless(mappedName = "deliveryRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class DeliveryRefresher implements RefresherLocal, RefresherRemote {

	//TODO init
	SynchronizationService synchronizationService;
	
	@Override
	public void refresh() {
		refreshSince(new Date());
	}

	@Override
	public void refreshSince(Date date) {
		try {
			List<WebUserDTO> users = synchronizationService.getUsersByRole(UserRole.RESTAURANT);
			System.out.println();
		} catch (NotAllowedRoleException_Exception e) {
			
		} 
	}

}
