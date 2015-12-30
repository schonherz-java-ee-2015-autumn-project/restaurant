package hu.schonherz.restaurant.integration;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import hu.schonherz.restaurant.integration.exception.RefresherException;

@ManagedBean
public class UserRefresherManagedBean {

	@EJB(mappedName="userRefresher")
	RefresherLocal userRefresher;
	
	public void refresh() throws RefresherException{
		userRefresher.refresh();
	}
	
}
