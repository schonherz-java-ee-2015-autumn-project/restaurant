package hu.schonherz.restaurant.entities;

import java.util.List;

import javax.persistence.*;

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

	@Column(name = "banned", nullable = false)
	private Boolean banned;

	@Column(name = "cost_of_service", nullable = false)
	private float costOfService;

	@Column(name = "global_id", nullable = false, unique = true)
	private Long globalId;

	public float getCostOfService() {
		return costOfService;
	}

	public void setCostOfService(float costOfService) {
		this.costOfService = costOfService;
	}

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

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	public Long getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Long globalId) {
		this.globalId = globalId;
	}
}
