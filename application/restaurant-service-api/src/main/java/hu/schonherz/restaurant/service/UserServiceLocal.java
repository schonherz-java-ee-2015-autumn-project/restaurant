package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.UserVo;

public interface UserServiceLocal {

	public UserVo findUserByName(String name) throws Exception;

	public List<UserVo> getUsers();

	public UserVo findById(Long id);
	
	public void save(UserVo user);

}
