package com.onlineShop.controller;

import com.*;
import com.onlineShop.factory.SBExampleApplication;
import com.onlineShop.db.CrudRepository;
import com.onlineShop.db.DatabaseConnection;
import com.onlineShop.db.ResultSetHandler;
import com.onlineShop.db.TransactionManager;
import com.onlineShop.entity.User;
import com.onlineShop.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlineShop.model.Converter;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private Converter converter;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        log.info("getAllUsers");
        ArrayList<UserModel> userModel;
        try (Connection connection = DatabaseConnection.getConnection()) {
            ResultSetHandler<User> userHandler = resultSet -> new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
            );
            CrudRepository<User> userRepository = new CrudRepository<>(connection, "users", userHandler);
            List<User> users = userRepository.readAll("SELECT * FROM users");
            userModel = users.stream().map(converter::converterToUser).peek(System.out::println)
                    .collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(userModel);
        } catch (Exception e) {
            log.error("getAllUsers{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<UserModel>> searchUser(@RequestParam Long id) {
        log.info("searchUser {}", id);
        ArrayList<UserModel> userModel;
        try (Connection connection = DatabaseConnection.getConnection()) {
            ResultSetHandler<User> userHandler = resultSet -> new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
            );

            CrudRepository<User> userRepository = new CrudRepository<>(connection, "users", userHandler);

            List<User> users = userRepository.readAll("SELECT * FROM users where username WHERE id = ?", id);
            userModel = users.stream().map(converter::converterToUser).peek(System.out::println)
                    .collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(userModel);
        } catch (Exception e) {
            log.error("searchUser error{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
