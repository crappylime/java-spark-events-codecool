package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lama on 17.05.17, 02:11.
 */
public class SqliteJDBCConnector {
    private Connection connection;

    public void createTables() throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS events\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR NOT NULL,\n" +
                "    description TEXT,\n" +
                "    date DATE NOT NULL\n" +
                ")");
    }
}
