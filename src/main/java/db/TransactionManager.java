package db;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private final Connection connection;

    public TransactionManager(Connection connection) {
        this.connection = connection;
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
    }

}
