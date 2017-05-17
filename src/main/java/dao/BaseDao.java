package dao;

import java.applet.;
import java.sql.Connection;

/**
 * Created by lama on 17.05.17, 02:34.
 */
public class BaseDao {
    private Connection connection;

    public BaseDao() {
        Connection connection = Application.getApp().getConnection();
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
