package com.onlineShop.springBoot.example.controller;
import com.onlineShop.springBoot.example.db.DatabaseConnection;
import com.onlineShop.springBoot.example.db.CrudRepository;
import com.onlineShop.springBoot.example.db.TransactionManager;
import com.onlineShop.springBoot.example.db.ResultSetHandler;
import com.onlineShop.springBoot.example.entity.Order;
import com.onlineShop.springBoot.example.entity.Product;
import com.onlineShop.springBoot.example.model.Converter;
import com.onlineShop.springBoot.example.model.OrderItemModel;
import com.onlineShop.springBoot.example.entity.OrderItem;
import com.onlineShop.springBoot.example.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Order")
@Slf4j
// add order
public class OrderController {
    private Converter converter;
    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody OrderModel orderModel) {
        log.info("Order added: {}", orderModel);
        try (Connection connection = DatabaseConnection.getConnection()) {
            TransactionManager transactionManager = new TransactionManager(connection);
            transactionManager.beginTransaction();

            ResultSetHandler<Order> orderHandler = resultSet -> new Order(
                    resultSet.getLong("id"),
                    resultSet.getLong("person_id"),
                    resultSet.getString("payment_method"),
                    resultSet.getTimestamp("payment_date").toLocalDateTime()
            );

            CrudRepository<Order> orderRepository = new CrudRepository<>(connection, "orders", orderHandler);

            int orderId = orderRepository.create(
                    "INSERT INTO orders(person_id, payment_method, payment_date) VALUES (?, ?, NOW())",
                    orderModel.getPerson_id(),
                    orderModel.getPayment_method()
            );

            CrudRepository<Product> productRepository = new CrudRepository<>(connection, "product", resultSet -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getString("made_in"),
                    Year.of(resultSet.getInt("year_of_manufacture")),
                    resultSet.getString("design"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getLong("category_id")
            ));

            CrudRepository<OrderItem> orderItemRepository = new CrudRepository<>(connection, "orders_item", resultSet -> new OrderItem(
                    resultSet.getLong("id"),
                    resultSet.getLong("order_id"),
                    resultSet.getLong("product_id"),
                    resultSet.getInt("quantity"),
                    resultSet.getBigDecimal("price")
            ));

            BigDecimal totalPrice = BigDecimal.ZERO;

            for (OrderItemModel orderItemModel : orderModel.getItems()) {
                Product product = productRepository.read("SELECT * FROM product WHERE id = ?", orderItemModel.getProduct_id());

                if (product == null) {
                    throw new SQLException("Product not found with id: " + orderItemModel.getProduct_id());
                }

                BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(orderItemModel.getQuantity()));
                totalPrice = totalPrice.add(itemPrice);

                orderItemRepository.create(
                        "INSERT INTO orders_item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)",
                        orderId,
                        orderItemModel.getProduct_id(),
                        orderItemModel.getQuantity(),
                        itemPrice
                );
            }

            transactionManager.commitTransaction();

            log.info("Order created successfully with id: {}", orderId);
            return ResponseEntity.ok("Order created successfully. Total price: " + totalPrice);

        } catch (Exception e) {
            log.error("Error creating order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order: " + e.getMessage());
        }
    }
}
