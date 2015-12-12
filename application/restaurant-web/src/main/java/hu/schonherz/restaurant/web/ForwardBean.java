package hu.schonherz.restaurant.web;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ViewScoped
@ManagedBean(name = "forward")
public class ForwardBean {
	@PostConstruct
	public void doForward() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		NavigationHandler navigationHandler = ctx.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(ctx, null, "outcome");
	}
}
