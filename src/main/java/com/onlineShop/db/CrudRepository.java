package com.onlineShop.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudRepository<T> {
    private final Connection connection;
    private final String tableName;
    private final ResultSetHandler<T> handler;

    public CrudRepository(Connection connection, String tableName, ResultSetHandler<T> handler) {
        this.connection = connection;
        this.tableName = tableName;
        this.handler = handler;
    }

    public int create(String insertQuery , Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)){
            setparameters(statement,params);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public T read(String selectQuery, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)){
            setparameters(statement,params);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return handler.handel(resultSet);
                }
            }
        }
        return null;
    }

    public List<T> readAll(String selectQuery, Object... params) throws SQLException {
        List<T> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)){
            setparameters(statement,params);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    result.add(handler.handel(resultSet));
                }
            }
        }
        return result;
    }

    public int update(String updateQuery , Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)){
            setparameters(statement,params);
            return statement.executeUpdate();
        }
    }

    public int delete(String deleteQuery , Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)){
            setparameters(statement,params);
            return statement.executeUpdate();
        }
    }


    private void setparameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}
