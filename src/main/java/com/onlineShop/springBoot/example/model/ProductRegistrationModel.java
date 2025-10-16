package com.onlineShop.springBoot.example.model;

import java.time.LocalDate;

public class ProductRegistrationModel {
    private Long id;
    private Long Person_id;
    private Long Product_id;
    private String registration_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerson_id() {
        return Person_id;
    }

    public void setPerson_id(Long person_id) {
        Person_id = person_id;
    }

    public Long getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(Long product_id) {
        Product_id = product_id;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }
}
