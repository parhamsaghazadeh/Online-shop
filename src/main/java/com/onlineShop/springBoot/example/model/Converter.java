package com.onlineShop.springBoot.example.model;

import com.onlineShop.springBoot.example.entity.Person;
import com.onlineShop.springBoot.example.entity.*;
import com.onlineShop.springBoot.example.entity.User;
import org.springframework.stereotype.Component;

import java.sql.Date;

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

}
