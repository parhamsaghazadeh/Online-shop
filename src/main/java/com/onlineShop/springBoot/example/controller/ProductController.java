package com.onlineShop.springBoot.example.controller;

import com.onlineShop.springBoot.example.db.CrudRepository;
import com.onlineShop.springBoot.example.db.DatabaseConnection;
import com.onlineShop.springBoot.example.db.ResultSetHandler;
import com.onlineShop.springBoot.example.db.TransactionManager;
import com.onlineShop.springBoot.example.model.*;
import com.onlineShop.springBoot.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private Converter converter;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        log.info("getAllProducts");
        ArrayList<ProductModel> productModel;
        try (Connection connection = DatabaseConnection.getConnection()) {
            ResultSetHandler<Product> productHandler = resultSet -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getString("made_id"),
                    Year.of(resultSet.getInt("year_of_manufacture")),
                    resultSet.getString("design"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getLong("category_id")
            );

            CrudRepository<Product> productRepository = new CrudRepository<>(connection, "product", productHandler);

            List<Product> product = productRepository.readAll("SELECT * FROM product");
            productModel = product.stream().map(converter::converterToProduct).
                    peek(System.out::println).
                    collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(productModel);
        } catch (Exception e) {
            log.error("getAllProducts{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<ProductModel>> searchProducts(@RequestParam long id) {
        log.info("searchProducts {}", id);
        ArrayList<ProductModel> productModel;
        try (Connection connection = DatabaseConnection.getConnection()) {
            ResultSetHandler<Product> productHandler = resultSet -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getString("made_id"),
                    Year.of(resultSet.getInt("year_of_manufacture")),
                    resultSet.getString("design"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getLong("category_id")
            );

            CrudRepository<Product> productRepository = new CrudRepository<>(connection, "product", productHandler);

            List<Product> products = productRepository.readAll("SELECT * FROM product WHERE id = ?", id);
            productModel = products.stream().map(converter::converterToProduct)
                    .peek(System.out::println)
                    .collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(productModel);
        } catch (Exception e) {
            log.error("searchProducts{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel productModel) {
        log.info("addProduct {}", productModel);
        try (Connection connection = DatabaseConnection.getConnection()) {
            TransactionManager transactionManager = new TransactionManager(connection);
            ResultSetHandler<Product> productHandler = resultSet -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getString("made_id"),
                    Year.of(resultSet.getInt("year_of_manufacture")),
                    resultSet.getString("design"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getLong("category_id")
            );

            CrudRepository<Product> productRepository = new CrudRepository<>(connection, "product", productHandler);

            transactionManager.beginTransaction();
            int value = productRepository.create("INSERT INTO product(name,brand,model,made_id,year_of_manufacture,design,price,category_id) VALUE (?,?,?,?,?,?,?,?)",
                    productModel.getName(), productModel.getBrand(), productModel.getModel(), productModel.getMade_in(), productModel.getYear_of_manufacture(), productModel.getDesign(), productModel.getPrice(), productModel.getCategory_id());
            transactionManager.commitTransaction();

            Product product = productRepository.read("SELECT * FROM product WHERE id = ?", value);
            productModel = converter.converterToProduct(product);
            return ResponseEntity.ok(productModel);
        } catch (Exception e) {
            log.error("addProduct {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
