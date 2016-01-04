package hu.schonherz.restaurant.integration;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import hu.schonherz.administrator.InvalidDateException_Exception;
import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.administrator.SynchronizationService;
import hu.schonherz.administrator.SynchronizationServiceImpl;
import hu.schonherz.restaurant.integration.exception.RefresherException;

@Stateless(mappedName = "deliveryRefresher")
@Local(RefresherLocal.class)
@Remote(RefresherRemote.class)
public class DeliveryRefresher implements RefresherLocal, RefresherRemote {

	SynchronizationService synchronizationService;

	@Override
	public void refresh() throws RefresherException {
		refreshSince(new Date(0));
	}

	@Override
	public void refreshSince(Date date) throws RefresherException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		try {
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			List<RemoteCargoDTO> cargos = synchronizationService.getCargosByDate(calendar);
			
			//TODO convert to our dto

		} catch (DatatypeConfigurationException | InvalidDateException_Exception e) {
			throw new RefresherException();
		}
	}

	@PostConstruct
	public void init() {
		System.out.println("init");
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
