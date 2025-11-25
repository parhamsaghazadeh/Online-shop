package com.onlineShop.springBoot.example.controller;


import com.onlineShop.springBoot.example.model.RegisterModel;
import com.onlineShop.springBoot.example.model.USerModel;
import com.onlineShop.springBoot.example.service.LoginService;
import com.onlineShop.springBoot.example.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("entry")
public class AuthController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterModel registerModel) {
        return ResponseEntity.ok(registerService.registerUser(registerModel));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody USerModel loginModel) {
        try {
            return ResponseEntity.ok(loginService.login(loginModel));
        }
        catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
