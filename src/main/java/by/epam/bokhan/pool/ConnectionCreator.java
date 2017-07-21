package by.epam.bokhan.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by vbokh on 13.07.2017.
 */
class ConnectionCreator {
    private static ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("/resource/database");
    private static String url = resourceBundle.getString("db.url");
    private static String login = resourceBundle.getString("db.user");
    private static String password = resourceBundle.getString("db.password");

    static ProxyConnection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, login, password);
        return new ProxyConnection(connection);
    }
}
