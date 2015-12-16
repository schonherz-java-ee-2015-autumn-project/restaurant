package hu.schonherz.restaurant.service;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import hu.schonherz.restaurant.entities.Role;
import hu.schonherz.restaurant.service.vo.RoleVo;

@Service("roleConverter")
public class RoleConverter extends EntityVoConverter<RoleVo, Role> {

	private static final long serialVersionUID = 1L;

	private static Mapper mapper = new DozerBeanMapper();

	@Override
	public Role convert(RoleVo source) {
		return mapper.map(source, Role.class);
	}

	@Override
	public RoleVo reverse(Role source) {
		return mapper.map(source, RoleVo.class);
	}

}
