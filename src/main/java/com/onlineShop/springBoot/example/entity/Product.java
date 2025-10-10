package com.onlineShop.springBoot.example.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Year;

public class Product {
    private Long id;
    private String name;
    private String brand;
    private String model;
    private String made_in;
    private Year year_of_manufacture;
    private String design;
    private BigDecimal price;
    private Long category_id;

    public Product(Long id, String name, String brand , String model , String made_in , Year year_of_manufacture , String design , BigDecimal price, Long category_id){
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.made_in = made_in;
        this.year_of_manufacture = year_of_manufacture;
        this.design = design;
        this.price = price;
        this.category_id = category_id;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMade_in() {
        return made_in;
    }

    public void setMade_in(String made_in) {
        this.made_in = made_in;
    }

    public Year getYear_of_manufacture() {
        return year_of_manufacture;
    }

    public void setYear_of_manufacture(Year year_of_manufacture) {
        this.year_of_manufacture = year_of_manufacture;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
