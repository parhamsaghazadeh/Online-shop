package com.onlineShop.springBoot.example.entity;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long id;
    private long person_id;
    private String payment_method;
    private LocalDateTime payment_date;

    private List<OrderItem> items;


    public Order(Long id, long person_id, String payment_method, LocalDateTime payment_date) {
        this.id = id;
        this.person_id = person_id;
        this.payment_method = payment_method;
        this.payment_date = payment_date;
    }

    public Order() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(long person_id) {
        this.person_id = person_id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
