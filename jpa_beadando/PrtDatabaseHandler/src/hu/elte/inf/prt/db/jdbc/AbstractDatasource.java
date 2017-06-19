package hu.elte.inf.prt.db.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatasource {

    protected final ConnectionFactory connectionFactory;

    protected AbstractDatasource(String url, String user, String password) {
        ConnectionFactory.initializeInstance(url, user, password);
        connectionFactory = ConnectionFactory.getInstance();
    }

    public Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstance().getConnection();
    }
}
