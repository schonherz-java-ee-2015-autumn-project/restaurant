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

	public static AddressReportVo toVo(AddressReport report) {
		if (report == null) {
			return null;
		}
		return mapper.map(report, AddressReportVo.class);
	}

	public static AddressReport toDto(AddressReportVo reportVO) {
		if (reportVO == null) {
			return null;
		}
		return mapper.map(reportVO, AddressReport.class);
	}

	public static List<AddressReportVo> toVo(List<AddressReport> report) {
		List<AddressReportVo> rv = new ArrayList<>();
		for (AddressReport reports : report) {
			rv.add(toVo(reports));
		}
		return rv;
	}

	public static List<AddressReport> toDto(List<AddressReportVo> report) {
		List<AddressReport> rv = new ArrayList<>();
		for (AddressReportVo reports : report) {
			rv.add(toDto(reports));
		}
		return rv;
	}
	
	
}
