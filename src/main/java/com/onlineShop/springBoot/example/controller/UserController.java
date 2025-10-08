package com.onlineShop.springBoot.example.controller;

import com.onlineShop.springBoot.example.db.CrudRepository;
import com.onlineShop.springBoot.example.db.DatabaseConnection;
import com.onlineShop.springBoot.example.db.ResultSetHandler;
import com.onlineShop.springBoot.example.db.TransactionManager;
import com.onlineShop.springBoot.example.entity.User;
import com.onlineShop.springBoot.example.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlineShop.springBoot.example.model.Converter;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

            List<User> users = userRepository.readAll("SELECT * FROM users WHERE id = ?", id);
            userModel = users.stream().map(converter::converterToUser).peek(System.out::println)
                    .collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(userModel);
        } catch (Exception e) {
            log.error("searchUser error{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            TransactionManager transactionManager = new TransactionManager(connection);
            ResultSetHandler<User> userHandler = resultSet -> new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
            );

            CrudRepository<User> userRepository = new CrudRepository<>(connection, "users", userHandler);

            transactionManager.beginTransaction();
            int value = userRepository.create("INSERT INTO users(username,password) VALUES (?,?)",userModel.getUsername(), userModel.getPassword());
            transactionManager.commitTransaction();

            User user = userRepository.read("SELECT * FROM users WHERE id = ?", value);
            userModel = converter.converterToUser(user);
            log.info("createUser {}", userModel);
            return ResponseEntity.ok(userModel);
        } catch (Exception e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PutMapping
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel userModel) {
        log.info("updateUser {}", userModel);
        try (Connection connection = DatabaseConnection.getConnection()){
            TransactionManager transactionManager = new TransactionManager(connection);
            ResultSetHandler<User> userHandler = resultSet -> new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
            );

            CrudRepository<User> userRepository = new CrudRepository<>(connection, "users", userHandler);

            transactionManager.beginTransaction();
            int row = userRepository.update(
                    "UPDATE users SET username = ? , password = ? WHERE id = ?",
                    userModel.getUsername(),
                    userModel.getPassword(),
                    userModel.getId()
            );
            transactionManager.commitTransaction();

            if (row == 0){
                log.warn("user request: update failed , not found user with id {} ", userModel.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return Optional.ofNullable(userRepository.read("SELECT * FROM users WHERE id = ?", userModel.getId()))
                    .map(converter::converterToUser)
                    .map(updated-> {log.info("user request:update successful{}",updated);
                    return ResponseEntity.ok(updated);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());


        }catch (Exception e) {
            log.error("updateUser error{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        log.info("deleteUser {}", id);
        try (Connection connection = DatabaseConnection.getConnection()){
            TransactionManager transactionManager = new TransactionManager(connection);

            CrudRepository<User> userRepository = new CrudRepository<>(connection,"users",null);

            transactionManager.beginTransaction();
            int row = userRepository.delete("DELETE FROM users WHERE id = ?",id);
            transactionManager.commitTransaction();

            if (row == 0){
                log.warn("user request: delete failed , not found user with id {} ", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            log.info("user request: delete successful{}",id);
            return ResponseEntity.ok("user delete successfully");
        }catch (Exception e ){
            log.error("deleteUser error{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
