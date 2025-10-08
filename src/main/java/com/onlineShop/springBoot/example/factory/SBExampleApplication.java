package com.onlineShop.springBoot.example.factory;
import com.onlineShop.springBoot.example.db.DatabaseConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.SQLException;
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.onlineShop"})

public class SBExampleApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SBExampleApplication.class, args);
        try (Connection connection = DatabaseConnection.getConnection()) {
            DatabaseConnection.initializeSchema(connection);
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
