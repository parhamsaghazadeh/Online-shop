package com.onlineShop.db;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
    T handel (ResultSet resultSet) throws SQLException;
}
