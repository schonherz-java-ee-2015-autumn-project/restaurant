package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.dto.OrderCountReport;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

public class OrderCountReportConverter {

	private static Mapper mapper = new DozerBeanMapper();

	public static OrderCountReportVo toVo(OrderCountReport report) {
		if (report == null) {
			return null;
		}
		return mapper.map(report, OrderCountReportVo.class);
	}

	public static OrderCountReport toDto(OrderCountReportVo reportVO) {
		if (reportVO == null) {
			return null;
		}
		return mapper.map(reportVO, OrderCountReport.class);
	}

	public static List<OrderCountReportVo> toVo(List<OrderCountReport> report) {
		List<OrderCountReportVo> rv = new ArrayList<>();
		for (OrderCountReport reports : report) {
			rv.add(toVo(reports));
		}
		return rv;
	}

	public static List<OrderCountReport> toDto(List<OrderCountReportVo> report) {
		List<OrderCountReport> rv = new ArrayList<>();
		for (OrderCountReportVo reports : report) {
			rv.add(toDto(reports));
		}
		return rv;
	}
	
	
}
