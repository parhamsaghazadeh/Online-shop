package com.onlineShop.springBoot.example.service;

import com.onlineShop.springBoot.example.db.ResultSetHandler;
import com.onlineShop.springBoot.example.db.CrudRepository;
import com.onlineShop.springBoot.example.db.DatabaseConnection;
import com.onlineShop.springBoot.example.entity.*;
import com.onlineShop.springBoot.example.model.Converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
import java.time.Year;
import java.sql.SQLException;

import java.util.Collections;



@Slf4j
@Component
public class Service {
    Converter converter = new Converter();

    public static final ResultSetHandler<DisplayOrdered> displayOrderedHandler = resultSet -> {
        DisplayOrdered displayOrdered = new DisplayOrdered();

        Product product = new Product();
        product.setName(resultSet.getString("product_name"));
        product.setBrand(resultSet.getString("product_brand"));
        product.setModel(resultSet.getString("product_model"));
        product.setMade_in(resultSet.getString("product_made_in"));
        product.setYear_of_manufacture(Year.of(resultSet.getInt("product_year")));
        product.setDesign(resultSet.getString("product_design"));
        product.setPrice(resultSet.getBigDecimal("product_price"));

        Category category = new Category();
        category.setTitle(resultSet.getString("category_title"));

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(resultSet.getInt("order_quantity"));
        orderItem.setPrice(resultSet.getBigDecimal("order_price"));

        ProductRegistration productRegistration = new ProductRegistration();
        productRegistration.setRegistration_date(resultSet.getDate("product_registration_date").toLocalDate());

        Order order = new Order();
        order.setPayment_method(resultSet.getString("order_payment_method"));
        order.setPayment_date(resultSet.getTimestamp("order_payment_date").toLocalDateTime());

        Person person = new Person();
        person.setName(resultSet.getString("person_name"));
        person.setLastname(resultSet.getString("person_lastname"));
        person.setNational_id(resultSet.getString("person_national_id"));

        Location location = new Location();
        location.setTitle(resultSet.getString("location_title"));
        location.setType(resultSet.getString("location_type"));
        location.setOpen_time(resultSet.getTime("location_open_time").toLocalTime());


        displayOrdered.setProducts(product);
        displayOrdered.setCategories(category);
        displayOrdered.setOrderItems(orderItem);
        displayOrdered.setProductRegistrations(productRegistration);
        displayOrdered.setOrders(order);
        displayOrdered.setPersons(person);
        displayOrdered.setLocations(location);

        return displayOrdered;

    };

    public List<DisplayOrdered> displayOrderedList(){
        log.info("displayOrderedList");
        try (Connection connection = DatabaseConnection.getConnection()){
            CrudRepository<DisplayOrdered> displayOrderedRepository = new CrudRepository<>(connection,"product", displayOrderedHandler);

            List<DisplayOrdered> displayOrderedList = displayOrderedRepository.readAll("SELECT p.name                 AS person_name,\n" +
                    "       p.lastname             AS person_lastname,\n" +
                    "       p.national_id          AS person_national_id,\n" +
                    "       c.title                AS category_title,\n" +
                    "       po.name                AS product_name,\n" +
                    "       po.brand               AS product_brand,\n" +
                    "       po.model               AS product_model,\n" +
                    "       po.made_in             AS product_made_in,\n" +
                    "       po.year_of_manufacture AS product_year,\n" +
                    "       po.design              AS product_design,\n" +
                    "       po.price               AS product_price,\n" +
                    "       o.payment_method       AS order_payment_method,\n" +
                    "       o.payment_date         AS order_payment_date,\n" +
                    "       orr.quantity           AS order_quantity,\n" +
                    "       orr.price              AS order_price,\n" +
                    "       pr.registration_date   AS product_registration_date,\n" +
                    "       l.title                AS location_title,\n" +
                    "       l.type                 AS location_type,\n" +
                    "       l.open_time            AS location_open_time\n" +
                    "From product po\n" +
                    "         join categories c on po.category_id = c.id\n" +
                    "          join orders_item orr on po.id = orr.product_id\n" +
                    "          join product_registration pr on po.id = pr.product_id\n" +
                    "          join orders o on orr.order_id = o.id\n" +
                    "          join person p on o.person_id = p.id\n" +
                    "          join order_location lo on o.id = lo.order_id\n" +
                    "          join location l on lo.location_id = l.id;");

            return displayOrderedList;
        }catch (SQLException e){
            log.error("displayOrderedList{}", e.getMessage());
            return Collections.emptyList();
        }
    }

}

