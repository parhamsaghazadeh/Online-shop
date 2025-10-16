package com.onlineShop.springBoot.example.model;

import com.onlineShop.springBoot.example.entity.Person;
import com.onlineShop.springBoot.example.entity.*;

import java.text.DecimalFormat;
import java.math.BigDecimal;


import com.onlineShop.springBoot.example.entity.User;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

@Component
public class Converter {
    public UserModel converterToUser(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setUsername(user.getUsername());
        userModel.setPassword(user.getPassword());
        return userModel;
    }

    public User converterToUser(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        return new User(
                userModel.getId(),
                userModel.getUsername(),
                userModel.getPassword()
        );
    }

    public PersonModel converterToPerson(Person person) {
        if (person == null) {
            return null;
        }
        PersonModel personModel = new PersonModel();
        personModel.setId(person.getId());
        personModel.setName(person.getName());
        personModel.setLastname(person.getLastname());
        personModel.setBirthdate(person.getBirthdate() != null ? person.getBirthdate().toString() : null);
        personModel.setNational_id(person.getNational_id());
        personModel.setUser_id(person.getUser_id());
        personModel.setGender_id(person.getGender_id());
        personModel.setRole_id(person.getRole_id());
        return personModel;
    }

    public Person converterToPersonEntity(PersonModel personModel) {
        if (personModel == null) {
            return null;
        }
        return new Person(
                personModel.getId(),
                personModel.getName(),
                personModel.getLastname(),
                Date.valueOf(personModel.getBirthdate()),
                personModel.getNational_id(),
                personModel.getUser_id(),
                personModel.getGender_id(),
                personModel.getRole_id()
        );
    }

    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    public ProductModel converterToProduct(Product product) {
        if (product == null) {
            return null;
        }
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setName(product.getName());
        productModel.setBrand(product.getBrand());
        productModel.setModel(product.getModel());
        productModel.setMade_in(product.getMade_in());
        productModel.setYear_of_manufacture(product.getYear_of_manufacture() != null ? product.getYear_of_manufacture().toString() : null);
        productModel.setDesign(product.getDesign());
        productModel.setPrice(df.format(product.getPrice()));

        productModel.setCategory_id(product.getCategory_id());
        return productModel;
    }

    public Product converterToProductEntity(ProductModel productModel) {
        if (productModel == null) {
            return null;
        }
        Year year = Year.parse(productModel.getYear_of_manufacture());
        BigDecimal price = new BigDecimal(productModel.getPrice().replace(",", ""));

        return new Product(
                productModel.getId(),
                productModel.getName(),
                productModel.getBrand(),
                productModel.getModel(),
                productModel.getMade_in(),
                year,
                productModel.getDesign(),
                price,
                productModel.getCategory_id()
        );
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderModel converterToOrder(Order order) {
        if (order == null) {
            return null;
        }
        OrderModel orderModel = new OrderModel();
        orderModel.setId(order.getId());
        orderModel.setPerson_id(order.getPerson_id());
        orderModel.setPayment_method(order.getPayment_method());
        orderModel.setPayment_date(order.getPayment_date() != null ? order.getPayment_date().format(formatter) : null);
        return orderModel;
    }

    public Order converterToOrderEntity(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        LocalDateTime dateTime = orderModel.getPayment_date() != null ? LocalDateTime.parse(orderModel.getPayment_date(), formatter) : null;

        return new Order(
                orderModel.getId(),
                orderModel.getPerson_id(),
                orderModel.getPayment_method(),
                dateTime
        );
    }

    public OrderItemModel converterToOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setId(orderItem.getId());
        orderItemModel.setOrder_id(orderItem.getOrder_id());
        orderItemModel.setProduct_id(orderItem.getProduct_id());
        orderItemModel.setQuantity(orderItem.getQuantity());
        orderItemModel.setPrice(df.format(orderItem.getPrice()));
        return orderItemModel;

    }

    public OrderItem converterToOrderItemEntity(OrderItemModel orderItemModel) {
        if (orderItemModel == null) {
            return null;
        }

        BigDecimal price = new BigDecimal(orderItemModel.getPrice().replace(",", ""));

        return new OrderItem(
                orderItemModel.getId(),
                orderItemModel.getOrder_id(),
                orderItemModel.getProduct_id(),
                orderItemModel.getQuantity(),
                price
        );

    }

    public CategoryModel converterToCategory(Category category) {
        if (category == null) {
            return null;
        }
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(category.getId());
        categoryModel.setTitle(category.getTitle());
        return categoryModel;
    }

    public Category converterToCategoryEntity(CategoryModel categoryModel) {
        if (categoryModel == null) {
            return null;
        }
        return new Category(
                categoryModel.getId(),
                categoryModel.getTitle()
        );
    }

    public ProductRegistrationModel converterToProductRegistrationModel(ProductRegistration productRegistration) {
        if (productRegistration == null) {
            return null;
        }
        ProductRegistrationModel productRegistrationModel = new ProductRegistrationModel();
        productRegistrationModel.setId(productRegistration.getId());
        productRegistrationModel.setPerson_id(productRegistration.getPerson_id());
        productRegistrationModel.setProduct_id(productRegistration.getProduct_id());
        productRegistrationModel.setRegistration_date(productRegistration.getRegistration_date() != null ? productRegistration.getRegistration_date().format(formatter) : null);
        return productRegistrationModel;
    }
}
