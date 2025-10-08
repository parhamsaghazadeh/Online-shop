package com.onlineShop.springBoot.example.db;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
    T handel (ResultSet resultSet) throws SQLException;
}
