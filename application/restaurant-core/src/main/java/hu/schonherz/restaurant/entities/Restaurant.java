package hu.schonherz.restaurant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurant extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "restaurant_name")
	private String name;

	@Column(name = "resturant_address")
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
