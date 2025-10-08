package com.onlineShop.springBoot.example.controller;

import com.onlineShop.springBoot.example.db.DatabaseConnection;
import com.onlineShop.springBoot.example.entity.Person;
import com.onlineShop.springBoot.example.model.PersonModel;
import com.onlineShop.springBoot.example.db.*;
import com.onlineShop.springBoot.example.model.Converter;
import org.springframework.beans.factory.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;
import java.sql.Connection;

@RestController
@RequestMapping("/person")
@Slf4j


public class PersonController {
    @Autowired
    private Converter converter;

    @GetMapping
    public ResponseEntity<List<PersonModel>> personAll() {
        log.info("Get all persons");
        ArrayList<PersonModel> personModels;
        try (Connection connection = DatabaseConnection.getConnection()) {
            ResultSetHandler personHandler = resultSet -> new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastname"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("national_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("gender_id"),
                    resultSet.getLong("role_id")
            );

            CrudRepository personRepository = new CrudRepository(connection, "person", personHandler);
            List<Person> persons = personRepository.readAll("SELECT * FROM person ");
            personModels = persons.stream().map(converter::converterToPerson).peek(System.out::println)
                    .collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(personModels);
        } catch (Exception e) {
            log.error("Error getting persons{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<PersonModel>> searchPerson(@RequestParam Long id) {
        log.info("Search person with id {}", id);
        ArrayList<PersonModel> personModels;
        try (Connection connection = DatabaseConnection.getConnection()) {
            ResultSetHandler personHandler = resultSet -> new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastname"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("national_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("gender_id"),
                    resultSet.getLong("role_id")
            );

            CrudRepository personRepository = new CrudRepository(connection, "person", personHandler);
            List<Person> persons = personRepository.readAll("SELECT * FROM person WHERE id = ? ");
            personModels = persons.stream().map(converter::converterToPerson).
                    collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(personModels);
        } catch (Exception e) {
            log.error("Error getting persons{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
