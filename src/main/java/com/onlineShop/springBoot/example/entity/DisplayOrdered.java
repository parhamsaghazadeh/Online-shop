package com.onlineShop.springBoot.example.entity;

import lombok.Data;

import java.util.List;
@Data
public class DisplayOrdered {
    private Product products;
    private Category categories;
    private OrderItem orderItems;
    private ProductRegistration productRegistrations;
    private Order orders;
    private Person persons;
    private OrderLocation orderLocations;
    private Location locations;

    public DisplayOrdered(Product products,Category categories , OrderItem orderItem , ProductRegistration productRegistrations , Order orders , Person persons , OrderLocation orderLocations , Location locations) {
        this.products = products;
        this.categories = categories;
        this.orderItems = orderItem;
        this.productRegistrations = productRegistrations;
        this.orders = orders;
        this.persons = persons;
        this.orderLocations = orderLocations;
        this.locations = locations;
    }

    public DisplayOrdered() {

    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
        this.categories = categories;
    }

    public OrderItem getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem orderItems) {
        this.orderItems = orderItems;
    }

    public ProductRegistration getProductRegistrations() {
        return productRegistrations;
    }

    public void setProductRegistrations(ProductRegistration productRegistrations) {
        this.productRegistrations = productRegistrations;
    }

    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }

    public Person getPersons() {
        return persons;
    }

    public void setPersons(Person persons) {
        this.persons = persons;
    }

    public OrderLocation getOrderLocations() {
        return orderLocations;
    }

    public void setOrderLocations(OrderLocation orderLocations) {
        this.orderLocations = orderLocations;
    }

    public Location getLocations() {
        return locations;
    }

    public void setLocations(Location locations) {
        this.locations = locations;
    }
}
