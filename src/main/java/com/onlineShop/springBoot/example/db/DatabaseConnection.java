package com.onlineShop.springBoot.example.db;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@Slf4j
public class DatabaseConnection {

    //Mysql configuration
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/shop";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "parham86sa";

    private static Connection connection = null;
    public static boolean schemaInitialized = false;

    // add Database connection
    public static Connection getConnection() {
            try {
                Connection connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                System.out.println(" Database connection successfully");
                return connection;
            } catch (SQLException e) {
                System.err.println(" Database connection failed: " + e.getMessage());
                throw new RuntimeException("Database connection failed", e);
            }
        }


    public static void initializeSchema(Connection con) {
        if (schemaInitialized) return;
        log.info("[DatabaseConnection] Initializing schema from ddl.sql..");
        try {
            InputStream ddlStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("ddl.sql");
            if (ddlStream == null) {
                log.info("[DatabaseConnection] ddl.sql not found in resources");
                return;
            }
            String ddl = new Scanner(ddlStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            // split by semicolon , ignoring empty statements
            for (String line : ddl.split(";")) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("#") && !trimmedLine.startsWith("--")) {
                    log.info("[DatabaseConnection] Executing : " + trimmedLine);
                    try (Statement s = con.createStatement()) {
                        s.execute(trimmedLine);
                    } catch (SQLException e) {
                        log.error("[DatabaseConnection] Error " + trimmedLine, e.getMessage());
                        // Ignore errors for "create database" or "use" if not supported
                        if (!trimmedLine.toLowerCase().startsWith("create database") && !trimmedLine.toLowerCase().startsWith("use")) {
                            throw e;
                        }
                    }
                }
            }
            schemaInitialized = true;
            log.info("[DatabaseConnection] Schema initialized completed");
        } catch (SQLException e) {
            log.error("[DatabaseConnection] Error initializing schema{}", e.getMessage());
            throw new RuntimeException("Error initializing schema", e);
        }
    }

}
