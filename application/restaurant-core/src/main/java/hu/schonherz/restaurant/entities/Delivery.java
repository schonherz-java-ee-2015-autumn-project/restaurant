package hu.schonherz.restaurant.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "deliveries")
public class Delivery extends BaseEntity {

    @Column(name = "state",  nullable = false)
    private String state;

    @Column(name = "courier")
    private String courier;

    @ManyToMany
    private List<Order> orders;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
