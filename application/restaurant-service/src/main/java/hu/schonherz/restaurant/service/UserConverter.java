package hu.schonherz.restaurant.service;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import hu.schonherz.restaurant.entities.User;
import hu.schonherz.restaurant.service.vo.UserVo;

@Service("userConverter")
public class UserConverter extends EntityVoConverter<UserVo, User> {

	private static final long serialVersionUID = 1L;

	private static Mapper mapper = new DozerBeanMapper();

	@Override
	public User toEntity(UserVo source) {
		return mapper.map(source, User.class);
	}

	@Override
	public UserVo toVo(User source) {
		return mapper.map(source, UserVo.class);
	}

}
