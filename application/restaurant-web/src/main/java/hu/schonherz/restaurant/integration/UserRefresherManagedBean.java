package hu.schonherz.restaurant.integration;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import hu.schonherz.restaurant.integration.exception.RefresherException;

@ManagedBean
public class UserRefresherManagedBean {

	@EJB(mappedName="userRefresher")
	RefresherLocal userRefresher;
	
	public void refresh(){
		try {
			userRefresher.refresh();
		} catch (RefresherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
