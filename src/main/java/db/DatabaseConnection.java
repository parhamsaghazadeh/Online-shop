package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //Mysql configuration
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/shop";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "parham86sa";

    private static Connection connection = null;
    public static boolean schemaInitialized = false;

    // add Database connection
    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection(MYSQL_URL,MYSQL_USER,MYSQL_PASSWORD);
            System.out.println("Database connection successfully");
        }catch (SQLException e){
            System.out.println("Database connection failed");
        }
        return connection;
    }


}
