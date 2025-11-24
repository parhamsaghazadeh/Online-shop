package com.onlineShop.springBoot.example.model;

import lombok.Data;

@Data
public class RegisterModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
