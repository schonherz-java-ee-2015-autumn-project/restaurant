package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.FinancialReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

public interface ReportServiceRemote {

	
	public List<OrderCountReportVo> dailyQuery(Long id);
	public List<OrderCountReportVo> weeklyQuery(Long id);
	public List<OrderCountReportVo> monthlyQuery(Long id);
	public List<OrderCountReportVo> annualQuery(Long id);
	public List<OrderCountReportVo> overallQuery(Long id);
	public List<AddressReportVo> addressQuery(Long id);
	
	public List<FinancialReportVo> dailyFinancialQuery(Long id);
	public List<FinancialReportVo> weeklyFinancialQuery(Long id);
	public List<FinancialReportVo> monthlyFinancialQuery(Long id);
	public List<FinancialReportVo> annualFinancialQuery(Long id);
	public List<FinancialReportVo> overallFinancialQuery(Long id);
}
