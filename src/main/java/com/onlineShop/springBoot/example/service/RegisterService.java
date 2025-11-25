package com.onlineShop.springBoot.example.service;

import com.onlineShop.springBoot.example.model.RegisterModel;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import jakarta.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterService {

    private final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl("http://localhost:8081")
            .realm("master")
            .clientId("admin-cli")
            .username("admin")
            .password("admin")
            .grantType(OAuth2Constants.PASSWORD)
            .build();

    public String registerUser(RegisterModel registerModel) {

        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerModel.getUsername());
        user.setEnabled(true);

        //اطلعات اضافه
        Map<String, List<String>> roles = new HashMap<>();
        roles.put("firstname", List.of(registerModel.getFirstName()));
        roles.put("lastname", List.of(registerModel.getLastName()));
        roles.put("email", List.of(registerModel.getEmail()));
        user.setAttributes(roles);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerModel.getPassword());
        user.setCredentials(List.of(credential));

        RealmResource realmResource = keycloak.realm("my-realm");
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);
        if (response.getStatus()==201){
            return "User registered successfully";
        }
        return "User not registered";
    }
}
