package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;
import java.util.List;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;
	private String phoneNumber;
	private String name;
	private List<RoleVo> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleVo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVo> roles) {
		this.roles = roles;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}