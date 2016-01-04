package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.dto.FinancialReport;
import hu.schonherz.restaurant.service.vo.FinancialReportVo;


public class FinancialReportConverter {

	
	private static Mapper mapper = new DozerBeanMapper();

	public static FinancialReportVo toVo(FinancialReport report) {
		if (report == null) {
			return null;
		}
		return mapper.map(report, FinancialReportVo.class);
	}

	public static FinancialReport toDto(FinancialReportVo reportVO) {
		if (reportVO == null) {
			return null;
		}
		return mapper.map(reportVO, FinancialReport.class);
	}

	public static List<FinancialReportVo> toVo(List<FinancialReport> report) {
		List<FinancialReportVo> rv = new ArrayList<>();
		for (FinancialReport reports : report) {
			rv.add(toVo(reports));
		}
		return rv;
	}

	public static List<FinancialReport> toDto(List<FinancialReportVo> report) {
		List<FinancialReport> rv = new ArrayList<>();
		for (FinancialReportVo reports : report) {
			rv.add(toDto(reports));
		}
		return rv;
	}
	
}
