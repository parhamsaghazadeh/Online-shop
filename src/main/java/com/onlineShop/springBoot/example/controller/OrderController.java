package com.onlineShop.springBoot.example.controller;
import com.onlineShop.springBoot.example.db.DatabaseConnection;
import com.onlineShop.springBoot.example.db.CrudRepository;
import com.onlineShop.springBoot.example.db.TransactionManager;
import com.onlineShop.springBoot.example.db.ResultSetHandler;
import com.onlineShop.springBoot.example.entity.Order;
import com.onlineShop.springBoot.example.model.Converter;
import com.onlineShop.springBoot.example.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Order")
@Slf4j

public class OrderController {
    private Converter converter;
    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody OrderModel orderModel) {
        log.info("Order added: " + orderModel);
        try(Connection connection = DatabaseConnection.getConnection()){

            connection.setAutoCommit(false);

            CrudRepository<Order> orderRepository = new CrudRepository<>(connection,"orders",resultSet -> new Order(
                    resultSet.getLong("id"),
                    resultSet.getLong("person_id"),
                    resultSet.getString("payment_method"),
                    resultSet.getTimestamp("payment_date").toLocalDateTime()
            ));

            int orderId= orderRepository.create(
                    "INSERT INTO orders(person_id,payment_method,payment_date) VALUES (?,?,NOW()) ",
                    orderModel.getPerson_id(),
                    orderModel.getPayment_method()
            );

        }
    }
}
