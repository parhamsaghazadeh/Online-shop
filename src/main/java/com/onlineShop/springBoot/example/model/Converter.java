package com.onlineShop.springBoot.example.model;

import com.onlineShop.springBoot.example.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Converter {
        public UserModel converterToUser(User user){
                UserModel userModel = new UserModel();
                userModel.setId(user.getId());
                userModel.setUsername(user.getUsername());
                userModel.setPassword(user.getPassword());
                return userModel;
        }

        public User converterToUser(UserModel userModel){
                if(userModel == null){
                        return null;
                }
                return new User(
                        userModel.getId(),
                        userModel.getUsername(),
                        userModel.getPassword()
                );
        }
}
