package hu.schonherz.restaurant.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 16..
 */
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    @Column(name = "pay_type")
    private String payType;

    @ManyToMany
    private List<Product> products;

    @Column(name = "total_price", length = 50)
    private Integer totalPrice;

    @Column(name = "order_state",  nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
