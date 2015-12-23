package hu.schonherz.restaurant.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "restaurant_name")
	private String name;

	@Column(name = "resturant_address")
	private String address;

	@OneToMany(mappedBy = "restaurant")
	private List<Product> products;

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


	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
