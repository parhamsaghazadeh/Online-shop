package com.onlineShop.springBoot.example.factory;
import com.onlineShop.springBoot.example.db.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
@SpringBootApplication(scanBasePackages = {"com.onlineShop"})

public class SBExampleApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SBExampleApplication.class, args);
        DatabaseConnection.getConnection();
    }
}
