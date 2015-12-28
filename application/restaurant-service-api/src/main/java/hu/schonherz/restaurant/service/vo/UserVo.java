package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;
import java.util.List;

public class UserVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String phoneNumber;
	private String name;
	private List<RoleVo> roles;
	private RestaurantVo restaurant;
	private Boolean banned;

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

	public RestaurantVo getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantVo restaurant) {
		this.restaurant = restaurant;
	}

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}
}
