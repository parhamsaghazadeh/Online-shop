package com.onlineShop.springBoot.example.service;


import com.onlineShop.springBoot.example.model.USerModel;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public Keycloak getKeycloak(USerModel loginUser) {

        String serverUrl = "http://localhost:8180";
        String realm = "my-realm";
        String clientId = "my-backend";

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .grantType(OAuth2Constants.PASSWORD)
                .username(loginUser.getUsername())
                .password(loginUser.getPassword())
                .build();
        return keycloak;
    }

    public String login(USerModel loginUser) {
        try {
            Keycloak keycloak = getKeycloak(loginUser);
            System.out.println("Login successful" + loginUser.getUsername());
            return "Successfully logged in " + loginUser.getUsername();
        }catch (Exception e) {
            System.out.println(e);
            System.out.println("Login failed" + loginUser.getUsername());

            return "Login failed" + loginUser.getUsername();
        }
    }
}
