package by.epam.bokhan.pool;

import by.epam.bokhan.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by vbokh on 13.07.2017.
 */
class ConnectionCreator {
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_CONFIG = "/resource/database";
    private static ResourceBundle resourceBundle;
    private static String url;
    private static String login;
    private static String password;

    static {
        try {
            resourceBundle = PropertyResourceBundle.getBundle(DB_CONFIG);
            url = resourceBundle.getString(DB_URL);
            login = resourceBundle.getString(DB_USER);
            password = resourceBundle.getString(DB_PASSWORD);
        } catch (MissingResourceException e) {
            throw new RuntimeException(String.format("Can not find resourcebundle. Reason : %s", e.getMessage()));
        }
    }

    static ProxyConnection getConnection() throws ConnectionPoolException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, login, password);

        } catch (SQLException e) {
            throw new ConnectionPoolException(String.format("Connection was not created. Reason : %s", e.getMessage()), e);
        }
        return new ProxyConnection(connection);
    }
}
