package com.onlineShop.springBoot.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_location")
public class OrderLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order_id;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location_id;

    public OrderLocation(Long id, Order order_id, Location location_id) {
        this.id = id;
        this.order_id = order_id;
        this.location_id = location_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Order order_id) {
        this.order_id = order_id;
    }

    public Location getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Location location_id) {
        this.location_id = location_id;
    }
}
