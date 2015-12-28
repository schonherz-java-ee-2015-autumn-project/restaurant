package hu.schonherz.restaurant.entities;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "deliveries")
@Where(clause = "isDeleted='false'")
public class Delivery extends BaseEntity {

    @Column(name = "delivery_state",  nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;

    @Column(name = "courier")
    private String courier;

    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @ManyToMany
    private List<Order> orders;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public DeliveryState getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(DeliveryState deliveryState) {
        this.deliveryState = deliveryState;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
