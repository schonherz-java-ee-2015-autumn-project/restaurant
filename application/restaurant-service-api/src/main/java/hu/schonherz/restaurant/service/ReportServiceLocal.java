package hu.schonherz.restaurant.service;

import java.util.Date;
import java.util.List;

import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.FinancialReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;
import hu.schonherz.restaurant.service.vo.PayTypeReportVo;

public interface ReportServiceLocal {

	
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
	
	public List<PayTypeReportVo>dailyPayTypeQuery(Long id);
	public List<PayTypeReportVo>weeklyPayTypeQuery(Long id);
	public List<PayTypeReportVo>monthlyPayTypeQuery(Long id);
	public List<PayTypeReportVo>annualPayTypeQuery(Long id);
	public List<PayTypeReportVo>overallPayTypeQuery(Long id);
	
	public List<OrderCountReportVo>customDailyQuery(Date beginDate, Date endDate, Long id);
	public List<OrderCountReportVo>customMonthQuery(Date beginDate, Date endDate, Long id);
	public List<OrderCountReportVo>customOverallQuery(Date beginDate, Date endDate, Long id);

	public List<FinancialReportVo>customDailyFinancialQuery(Date beginDate, Date endDate, Long id);
	public List<FinancialReportVo>customMonthFinancialQuery(Date beginDate, Date endDate, Long id);
	public List<FinancialReportVo>customOverallFinancialQuery(Date beginDate, Date endDate, Long id);

	public List<PayTypeReportVo>customDailyPayTypeQuery(Date beginDate, Date endDate, Long id);
	public List<PayTypeReportVo>customMonthPayTypeQuery(Date beginDate, Date endDate, Long id);
	public List<PayTypeReportVo>customOverallPayTypeQuery(Date beginDate, Date endDate, Long id);

	

	}
