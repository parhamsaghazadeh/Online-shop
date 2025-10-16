package com.onlineShop.springBoot.example.entity;

public class OrderLocation {
    private Long id;
    private Long order_id;
    private Long location_id;

    public OrderLocation(Long id, Long order_id, Long location_id) {
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

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }
}
