package hu.schonherz.restaurant.integration;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;

import com.sun.xml.ws.api.server.SDDocument.WSDL;

import hu.schonherz.administrator.NotAllowedRoleException_Exception;
import hu.schonherz.administrator.SynchronizationService;
import hu.schonherz.administrator.SynchronizationServiceImpl;
import hu.schonherz.administrator.UserRole;
import hu.schonherz.administrator.WebUserDTO;
import hu.schonherz.restaurant.service.UserServiceLocal;

@Stateless(mappedName = "userRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class UserRefresher implements RefresherLocal, RefresherRemote {

	SynchronizationService synchronizationService;

	@EJB
	UserServiceLocal userService;

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
			System.out.println("exception happened");
		}
	}

	@PostConstruct
	public void init() {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("wsdllocation.properties"));
			URL url = new URL(prop.getProperty("wsdl"));
			
			QName name = new QName("http://wsserviceapi.administration.schonherz.hu/", "SynchronizationServiceImpl");

			SynchronizationServiceImpl synchronizationServiceImpl = new SynchronizationServiceImpl(url, name);
			synchronizationService = synchronizationServiceImpl.getSynchronizationServiceImplPort();
		} catch (IOException e) {
			System.out.println("exception happend");
		}
	}

}
