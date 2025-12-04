package com.onlineShop.springBoot.example.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "person" )
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private Date birthdate;
    private String national_id;
    private long user_id;
    private long gender_id;
    private long role_id;

    public Person(Long id, String name, String lastname, Date birthdate, String national_id, long user_id, long gender_id, long role_id) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.national_id = national_id;
        this.user_id = user_id;
        this.gender_id = gender_id;
        this.role_id = role_id;
    }

    public Person() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getGender_id() {
        return gender_id;
    }

    public void setGender_id(long gender_id) {
        this.gender_id = gender_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }
}
