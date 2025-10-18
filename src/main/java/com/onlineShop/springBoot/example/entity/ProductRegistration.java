package com.onlineShop.springBoot.example.entity;

import java.time.LocalDate;

public class ProductRegistration {
    private Long id;
    private Long person_id;
    private Long product_id;;
    private LocalDate registration_date;

    public ProductRegistration(Long id, Long person_id, Long product_id, LocalDate registration_date) {
        this.id = id;
        this.person_id = person_id;
        this.product_id = product_id;
        this.registration_date = registration_date;
    }

    public ProductRegistration() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }
}
