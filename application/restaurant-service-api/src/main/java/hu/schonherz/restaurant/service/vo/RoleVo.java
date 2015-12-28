package hu.schonherz.restaurant.service.vo;

import java.io.Serializable;

public class RoleVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
