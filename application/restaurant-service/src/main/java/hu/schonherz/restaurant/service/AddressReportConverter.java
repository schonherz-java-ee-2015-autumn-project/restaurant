package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.dto.AddressReport;
import hu.schonherz.restaurant.dto.OrderCountReport;
import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

public class AddressReportConverter {

	private static Mapper mapper = new DozerBeanMapper();

	public static AddressReportVo<String> toVo(AddressReport<String> report) {
		if (report == null) {
			return null;
		}
		return mapper.map(report, AddressReportVo.class);
	}

	public static AddressReport<String> toDto(AddressReportVo<String> reportVO) {
		if (reportVO == null) {
			return null;
		}
		return mapper.map(reportVO, AddressReport.class);
	}

	public static List<AddressReportVo<String>> toVo(List<AddressReport<String>> report) {
		List<AddressReportVo<String>> rv = new ArrayList<>();
		for (AddressReport<String> reports : report) {
			rv.add(toVo(reports));
		}
		return rv;
	}

	public static List<AddressReport<String>> toDto(List<AddressReportVo<String>> report) {
		List<AddressReport<String>> rv = new ArrayList<>();
		for (AddressReportVo<String> reports : report) {
			rv.add(toDto(reports));
		}
		return rv;
	}
	
	
}
