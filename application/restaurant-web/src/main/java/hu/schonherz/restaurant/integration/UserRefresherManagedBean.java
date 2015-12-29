package hu.schonherz.restaurant.integration;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class UserRefresherManagedBean {

	@EJB(mappedName="userRefresher")
	RefresherLocal userRefresher;
	
	public void refresh(){
		userRefresher.refresh();
	}
	
}
