package com.onlineShop.springBoot.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "product_registration")
public class ProductRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person_id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product_id;
    private LocalDate registration_date;

    public ProductRegistration(Long id, Person person_id, Product product_id, LocalDate registration_date) {
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

    public Person getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Person person_id) {
        this.person_id = person_id;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }
}
