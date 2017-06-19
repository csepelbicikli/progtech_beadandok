package hu.elte.inf.prt.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    protected final String password;
    protected final String url;
    protected final String user;

    private ConnectionFactory(String url, String user, String password) {
        this.password = password;
        this.url = url;
        this.user = user;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int obtainNewId() throws SQLException {
        int id;
        try (
                final Connection connection = getConnection();
                final Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                final ResultSet rs = stmt.executeQuery("SELECT VALUUE FROM SEQ")) {
            rs.next();
            id = rs.getInt("VALUUE") + 1;
            rs.updateInt("VALUUE", id);
            rs.updateRow();
        }
        return id;
    }
    protected static ConnectionFactory instance = null;

    public static void initializeInstance(String url, String user, String password) {
        instance = new ConnectionFactory(url, user, password);
    }

    public static ConnectionFactory getInstance() {
        return instance;
    }
}
