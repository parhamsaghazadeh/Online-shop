package com.onlineShop.springBoot.example.service;


import com.onlineShop.springBoot.example.model.USerModel;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public AccessTokenResponse login (USerModel loginUser) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8081")
                    .realm("my-realm")
                    .clientId("my-backend")
                    .grantType(loginUser.getUsername())
                    .grantType(loginUser.getPassword())
                    .build();
            return keycloak.tokenManager().getAccessToken();
        }catch (Exception e){
            throw new RuntimeException("نام کاربری یا رمز اشتباس");
        }
    }

}
