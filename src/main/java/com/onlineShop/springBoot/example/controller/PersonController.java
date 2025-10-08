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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
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
            ResultSetHandler<Person> personHandler = resultSet -> new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastname"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("national_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("gender_id"),
                    resultSet.getLong("role_id")
            );

            CrudRepository<Person> personRepository = new CrudRepository<>(connection, "person", personHandler);
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
            ResultSetHandler<Person> personHandler = resultSet -> new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastname"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("national_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("gender_id"),
                    resultSet.getLong("role_id")
            );

            CrudRepository<Person> personRepository = new CrudRepository<>(connection, "person", personHandler);
            List<Person> persons = personRepository.readAll("SELECT * FROM person WHERE id = ? ");
            personModels = persons.stream().map(converter::converterToPerson).
                    collect(Collectors.toCollection(ArrayList::new));
            return ResponseEntity.ok(personModels);
        } catch (Exception e) {
            log.error("Error getting persons{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<PersonModel> addPerson(@RequestBody PersonModel personModel) {
        log.info("Add person {}", personModel);
        ArrayList<PersonModel> personModels;
        try (Connection connection = DatabaseConnection.getConnection()){
            TransactionManager transactionManager = new TransactionManager(connection);
            ResultSetHandler<Person> personHandler;
            personHandler = resultSet -> new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastname"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("national_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("gender_id"),
                    resultSet.getLong("role_id")
            );

            CrudRepository<Person> personRepository = new CrudRepository<>(connection, "person", personHandler);

            transactionManager.commitTransaction();
            int value = personRepository.create("INSERT INTO person (name,lastname,birthdate,national_id,user_id,gender_id,role_id) VALUES (?,?,?,?,?,?,?)",
                    personModel.getName(),personModel.getLastname(),personModel.getBirthdate(),personModel.getNational_id(),personModel.getUser_id(),personModel.getGender_id(),personModel.getRole_id());
            transactionManager.beginTransaction();
            Person person = personRepository.read("SELECT * FROM person WHERE id = ?", value);
            personModel = converter.converterToPerson(person);
            return ResponseEntity.ok(personModel);
        }catch (Exception e){
            log.error("Error adding person {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<PersonModel> updatePerson(@RequestBody PersonModel personModel) {
        log.info("Update person {}", personModel);
        ArrayList<PersonModel> personModels;
        try (Connection connection = DatabaseConnection.getConnection()){
            TransactionManager transactionManager = new TransactionManager(connection);
            ResultSetHandler<Person> personHandler = resultSet -> new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastname"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("national_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("gender_id"),
                    resultSet.getLong("role_id")
            );

            CrudRepository<Person> personRepository = new CrudRepository<>(connection, "person", personHandler);

            transactionManager.commitTransaction();
            int row = personRepository.update(
                    "UPDATE person SET name =? , lastname=? , birthdate=?,national_id=?,user_id=?,gender_id=?,role_id=? WHERE id = ? ",
                    personModel.getName(),
                    personModel.getLastname(),
                    personModel.getBirthdate(),
                    personModel.getNational_id(),
                    personModel.getNational_id(),
                    personModel.getUser_id(),
                    personModel.getGender_id(),
                    personModel.getRole_id(),
                    personModel.getId()
            );
            transactionManager.beginTransaction();

            if (row == 0){
                log.warn("person request : update failed , not found {}", personModel.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return Optional.ofNullable(personRepository.read("SELECT * FROM person WHERE id = ?",personModel.getId()))
                    .map(converter::converterToPerson)
                    .map(updatePerson->{log.info("update person successful {}", updatePerson);
                    return ResponseEntity.ok(updatePerson);
                    })
                    .orElseGet(()->ResponseEntity.notFound().build());
        }catch (Exception e){
            log.error("Error updating person {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
