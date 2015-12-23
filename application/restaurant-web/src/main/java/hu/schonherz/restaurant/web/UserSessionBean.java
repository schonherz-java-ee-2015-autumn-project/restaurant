package hu.schonherz.restaurant.web;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import hu.schonherz.restaurant.service.UserServiceLocal;
import hu.schonherz.restaurant.service.vo.UserVo;

@SessionScoped
@ManagedBean(name = "userSessionBean")
public class UserSessionBean {

	private static Logger logger = Logger.getLogger(UserSessionBean.class);

	@EJB
	private UserServiceLocal userService;

	private UserVo user;

	@PostConstruct
	public synchronized void init() {
		try {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			synchronized (UserSessionBean.class) {
				if (user == null) {
					user = null;
					Principal principal = req.getUserPrincipal();
					if (principal != null) {
						String username = principal.getName();
						try {
							user = getUserService().findUserByName(username);

							logger.info("UserVo is set!");
						} catch (Exception e) {
							// TODO Nice logging message
							logger.info("Exception thrown + " + e.getMessage());
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("Excepion during USB initialization", e);
		}

	}

	public UserServiceLocal getUserService() {
		return userService;
	}

	public void setUserService(UserServiceLocal userService) {
		this.userService = userService;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

}
