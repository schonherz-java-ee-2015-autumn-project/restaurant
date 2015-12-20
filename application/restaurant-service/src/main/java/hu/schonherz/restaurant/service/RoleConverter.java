package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.entities.Role;
import hu.schonherz.restaurant.service.vo.RoleVo;

public class RoleConverter {

	static Mapper mapper = new DozerBeanMapper();

	public static RoleVo toVo(Role entity) {
		if (entity == null) {
			return null;
		}
		return mapper.map(entity, RoleVo.class);
	}

	public static Role toEntity(RoleVo vo) {
		if (vo == null) {
			return null;
		}
		return mapper.map(vo, Role.class);
	}

	public static List<RoleVo> toVo(List<Role> entities) {
		List<RoleVo> rv = new ArrayList<>();
		for (Role roles : entities) {
			rv.add(toVo(roles));
		}
		return rv;
	}

	public static List<Role> toEntity(List<RoleVo> vos) {
		List<Role> rv = new ArrayList<>();
		for (RoleVo role : vos) {
			rv.add(toEntity(role));
		}
		return rv;
	}
}
