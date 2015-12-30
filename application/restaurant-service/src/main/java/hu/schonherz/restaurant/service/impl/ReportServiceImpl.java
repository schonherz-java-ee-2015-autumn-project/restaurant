package hu.schonherz.restaurant.service.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.restaurant.dao.OrderDao;
import hu.schonherz.restaurant.dao.RestaurantDao;
import hu.schonherz.restaurant.entities.Restaurant;
import hu.schonherz.restaurant.service.AddressReportConverter;
import hu.schonherz.restaurant.service.FinancialReportConverter;
import hu.schonherz.restaurant.service.OrderCountReportConverter;
import hu.schonherz.restaurant.service.ReportServiceLocal;
import hu.schonherz.restaurant.service.ReportServiceRemote;
import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.FinancialReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(ReportServiceLocal.class)
@Remote(ReportServiceRemote.class)

public class ReportServiceImpl implements ReportServiceLocal, ReportServiceRemote{
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private RestaurantDao restaurantDao;
	
	public String actualUser(Long id){
		
		Restaurant rest = restaurantDao.findOne(id);
		String name = rest.getName().replace(' ', '_');
		
		
		return name;
		
		
		
	}

	@Override
	public List<OrderCountReportVo> dailyQuery() {
		
		return OrderCountReportConverter.toVo(orderDao.dailyQuery());
	}

	@Override
	public List<OrderCountReportVo> weeklyQuery() {
		return OrderCountReportConverter.toVo(orderDao.weeklyQuery());
		
	}

	@Override
	public List<OrderCountReportVo> monthlyQuery() {
		return OrderCountReportConverter.toVo(orderDao.monthlyQuery());
//		return null;
	}

	@Override
	public List<OrderCountReportVo> annualQuery() {
		return OrderCountReportConverter.toVo(orderDao.annualQuery());
//		return null;
	}

	@Override
	public List<OrderCountReportVo> overallQuery() {
		return OrderCountReportConverter.toVo(orderDao.overallQuery());
//		return null;
	}

	@Override
	public List<AddressReportVo> addressQuery() {
		return AddressReportConverter.toVo(orderDao.addressQuery());
//		return null;
	}

	@Override
	public List<FinancialReportVo> dailyFinancialQuery(Long id) {
		
		return FinancialReportConverter.toVo(orderDao.dailyFinancialQuery(actualUser(id)));
	}

	@Override
	public List<FinancialReportVo> weeklyFinancialQuery(Long id) {
		// TODO Auto-generated method stub
		return FinancialReportConverter.toVo(orderDao.weeklyFinancialQuery(actualUser(id)));
	}

	@Override
	public List<FinancialReportVo> monthlyFinancialQuery(Long id) {
		// TODO Auto-generated method stub
		return FinancialReportConverter.toVo(orderDao.monthlyFinancialQuery(actualUser(id)));
	}

	@Override
	public List<FinancialReportVo> annualFinancialQuery(Long id) {
		// TODO Auto-generated method stub
		return FinancialReportConverter.toVo(orderDao.annualFinancialQuery(actualUser(id)));
	}

	@Override
	public List<FinancialReportVo> overallFinancialQuery(Long id) {
		
			return FinancialReportConverter.toVo(orderDao.overallFinancialQuery(actualUser(id)));
		
	}

	
	
	
	

}

	
	
