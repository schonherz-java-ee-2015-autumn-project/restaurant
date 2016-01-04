package hu.schonherz.restaurant.integration;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import hu.schonherz.administrator.NoRestaurantAssignedUserException_Exception;
import hu.schonherz.administrator.NotAllowedRoleException_Exception;
import hu.schonherz.administrator.SynchronizationService;
import hu.schonherz.administrator.SynchronizationServiceImpl;
import hu.schonherz.administrator.UserRole;
import hu.schonherz.administrator.WebRestaurantDTO;
import hu.schonherz.administrator.WebUserDTO;
import hu.schonherz.restaurant.integration.converter.RestaurantConverter;
import hu.schonherz.restaurant.integration.converter.UserConverter;
import hu.schonherz.restaurant.integration.exception.RefresherException;
import hu.schonherz.restaurant.service.UserServiceLocal;
import hu.schonherz.restaurant.service.vo.UserVo;

@Stateless(mappedName = "userRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class UserRefresher implements RefresherLocal, RefresherRemote {

	SynchronizationService synchronizationService;

	@EJB
	UserServiceLocal userService;

	@Override
	public void refresh() throws RefresherException {
		refreshSince(new Date(0));
	}

	@Override
	public void refreshSince(Date date) throws RefresherException {
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			List<WebUserDTO> users = synchronizationService.getUsersByRoleAndDate(UserRole.RESTAURANT,calendar);
			for (WebUserDTO user:users){
				
				WebRestaurantDTO restaurant = synchronizationService.findRestaurantByUserId(user.getId());
				
				UserVo userVo = UserConverter.toVo(user);
				
				userVo.setRestaurant(RestaurantConverter.toVo(restaurant));
				
				userService.save(userVo);
			}

		} catch (NotAllowedRoleException_Exception | DatatypeConfigurationException | NoRestaurantAssignedUserException_Exception e) {
			throw new RefresherException("Exception happened");
		}
	}

	@PostConstruct
	public void init() {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("wsdllocation.properties"));
			URL url = new URL(prop.getProperty("user.wsdl"));

			QName name = new QName("http://wsserviceapi.administration.schonherz.hu/", "SynchronizationServiceImpl");

			SynchronizationServiceImpl synchronizationServiceImpl = new SynchronizationServiceImpl(url, name);
			synchronizationService = synchronizationServiceImpl.getSynchronizationServiceImplPort();
		} catch (IOException e) {
			System.out.println("exception happend");
		}
	}

}
