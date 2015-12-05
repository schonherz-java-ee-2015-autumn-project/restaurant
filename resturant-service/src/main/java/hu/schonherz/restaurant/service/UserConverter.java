package hu.schonherz.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.restaurant.entities.User;
import hu.schonherz.restaurant.service.vo.UserVo;

public class UserConverter {

	private static Mapper mapper = new DozerBeanMapper();

	public static UserVo toVo(User user) {
		if (user == null) {
			return null;
		}
		return mapper.map(user, UserVo.class);
	}

	public static User toEntity(UserVo userVO) {
		if (userVO == null) {
			return null;
		}
		return mapper.map(userVO, User.class);
	}

	public static List<UserVo> toVo(List<User> user) {
		List<UserVo> rv = new ArrayList<>();
		for (User users : user) {
			rv.add(toVo(users));
		}
		return rv;
	}

	public static List<User> toEntity(List<UserVo> user) {
		List<User> rv = new ArrayList<>();
		for (UserVo users : user) {
			rv.add(toEntity(users));
		}
		return rv;
	}
}
