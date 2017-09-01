package by.epam.bokhan.pool;

import by.epam.bokhan.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


class ConnectionCreator {
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_CONFIG = "resource.database";
    private static String url;
    private static String login;
    private static String password;

    static {
        try {
            ResourceBundle resourceBundle = PropertyResourceBundle.getBundle(DB_CONFIG);
            url = resourceBundle.getString(DB_URL);
            login = resourceBundle.getString(DB_USER);
            password = resourceBundle.getString(DB_PASSWORD);
        } catch (MissingResourceException e) {
            throw new RuntimeException(String.format("Can not find resource bundle. Reason : %s", e.getMessage()));
        }
    }

    static ProxyConnection getConnection() throws ConnectionPoolException{
        Connection connection ;
        try {
            connection = DriverManager.getConnection(url, login, password);

        } catch (SQLException e) {
            throw new ConnectionPoolException(String.format("Connection was not created. Reason : %s", e.getMessage()), e);
        }
        return new ProxyConnection(connection);
    }
}
