package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.dto.AddressReport;
import hu.schonherz.restaurant.dto.OrderCountReport;
import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

public class OrderCountReportConverter {

	private static Mapper mapper = new DozerBeanMapper();

	public static OrderCountReportVo<String, Integer> toVo(OrderCountReport<String, Integer> report) {
		if (report == null) {
			return null;
		}
		return mapper.map(report, OrderCountReportVo.class);
	}

	public static OrderCountReport<String, Integer> toDto(OrderCountReportVo<String, Integer> reportVO) {
		if (reportVO == null) {
			return null;
		}
		return mapper.map(reportVO, OrderCountReport.class);
	}

	public static List<OrderCountReportVo<String, Integer>> toVo(List<OrderCountReport<String, Integer>> report) {
		List<OrderCountReportVo<String, Integer>> rv = new ArrayList<>();
		for (OrderCountReport<String, Integer> reports : report) {
			rv.add(toVo(reports));
		}
		return rv;
	}

	public static List<OrderCountReport<String, Integer>> toDto(List<OrderCountReportVo<String, Integer>> report) {
		List<OrderCountReport<String, Integer>> rv = new ArrayList<>();
		for (OrderCountReportVo<String, Integer> reports : report) {
			rv.add(toDto(reports));
		}
		return rv;
	}
	
	
}
