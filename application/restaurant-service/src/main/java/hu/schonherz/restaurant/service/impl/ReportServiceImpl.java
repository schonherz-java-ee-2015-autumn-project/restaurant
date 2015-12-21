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

import hu.schonherz.restaurant.dao.ReportDao;
import hu.schonherz.restaurant.service.AddressReportConverter;
import hu.schonherz.restaurant.service.OrderCountReportConverter;
import hu.schonherz.restaurant.service.ReportServiceLocal;
import hu.schonherz.restaurant.service.ReportServiceRemote;
import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

@Stateless(mappedName = "ReportService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(ReportServiceLocal.class)
@Remote(ReportServiceRemote.class)

public class ReportServiceImpl implements ReportServiceLocal, ReportServiceRemote{
	
	@Autowired
	private ReportDao reportDao;

	@Override
	public List<OrderCountReportVo<String, Integer>> dailyQuery() {
		
		return OrderCountReportConverter.toVo(reportDao.dailyQuery());
	}

	@Override
	public List<OrderCountReportVo<String, Integer>> weeklyQuery() {
		return OrderCountReportConverter.toVo(reportDao.weeklyQuery());
		
	}

	@Override
	public List<OrderCountReportVo<String, Integer>> monthlyQuery() {
		return OrderCountReportConverter.toVo(reportDao.monthlyQuery());
		
	}

	@Override
	public List<OrderCountReportVo<String, Integer>> annualQuery() {
		return OrderCountReportConverter.toVo(reportDao.annualQuery());
		
	}

	@Override
	public List<OrderCountReportVo<String, Integer>> overallQuery() {
		return OrderCountReportConverter.toVo(reportDao.overallQuery());
		
	}

	@Override
	public List<AddressReportVo<String>> addressQuery() {
		return AddressReportConverter.toVo(reportDao.addressQuery());
		
	}
	
	


}
