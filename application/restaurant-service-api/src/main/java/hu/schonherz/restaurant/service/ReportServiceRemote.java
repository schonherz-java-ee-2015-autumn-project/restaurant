package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

public interface ReportServiceRemote {

	
	public List<OrderCountReportVo<String, Integer>> dailyQuery();
	public List<OrderCountReportVo<String, Integer>> weeklyQuery();
	public List<OrderCountReportVo<String, Integer>> monthlyQuery();
	public List<OrderCountReportVo<String, Integer>> annualQuery();
	public List<OrderCountReportVo<String, Integer>> overallQuery();
	public List<AddressReportVo<String>> addressQuery();
}
