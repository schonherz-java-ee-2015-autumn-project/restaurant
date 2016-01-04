package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.dto.PayTypeReport;
import hu.schonherz.restaurant.service.vo.PayTypeReportVo;

public class PayTypeReportConverter {

	private static Mapper mapper = new DozerBeanMapper();

	public static PayTypeReportVo toVo(PayTypeReport report) {
		if (report == null) {
			return null;
		}
		return mapper.map(report, PayTypeReportVo.class);
	}

	public static PayTypeReport toDto(PayTypeReportVo reportVO) {
		if (reportVO == null) {
			return null;
		}
		return mapper.map(reportVO, PayTypeReport.class);
	}

	public static List<PayTypeReportVo> toVo(List<PayTypeReport> report) {
		List<PayTypeReportVo> rv = new ArrayList<>();
		for (PayTypeReport reports : report) {
			rv.add(toVo(reports));
		}
		return rv;
	}

	public static List<PayTypeReport> toDto(List<PayTypeReportVo> report) {
		List<PayTypeReport> rv = new ArrayList<>();
		for (PayTypeReportVo reports : report) {
			rv.add(toDto(reports));
		}
		return rv;
	}
	
	
}
