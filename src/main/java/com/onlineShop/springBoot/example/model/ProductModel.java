package com.onlineShop.springBoot.example.model;

public class ProductModel {
    private Long id;
    private String name;
    private String brand;
    private String model;
    private String made_in;
    private String year_of_manufacture;
    private String design;
    private String price;
    private Long category_id;
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

    public String getYear_of_manufacture() {
        return year_of_manufacture;
    }

    public void setYear_of_manufacture(String year_of_manufacture) {
        this.year_of_manufacture = year_of_manufacture;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
