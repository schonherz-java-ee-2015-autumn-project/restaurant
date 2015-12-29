package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.FinancialReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

public interface ReportServiceRemote {

	
	public List<OrderCountReportVo> dailyQuery();
	public List<OrderCountReportVo> weeklyQuery();
	public List<OrderCountReportVo> monthlyQuery();
	public List<OrderCountReportVo> annualQuery();
	public List<OrderCountReportVo> overallQuery();
	public List<AddressReportVo> addressQuery();
	
	public List<FinancialReportVo> dailyFinancialQuery(Long id);
	public List<FinancialReportVo> weeklyFinancialQuery(Long id);
	public List<FinancialReportVo> monthlyFinancialQuery(Long id);
	public List<FinancialReportVo> annualFinancialQuery(Long id);
	public List<FinancialReportVo> overallFinancialQuery(Long id);
}
