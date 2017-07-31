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
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static ResourceBundle resourceBundle;
    static {
        try {
            resourceBundle = PropertyResourceBundle.getBundle("/resource/database");
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, String.format("Can not find resourcebundle. Reason : %s", e.getMessage()));
        }
    }
    private static String url = resourceBundle.getString(DB_URL);
    private static String login = resourceBundle.getString(DB_USER);
    private static String password = resourceBundle.getString(DB_PASSWORD);

    static ProxyConnection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, login, password);
        return new ProxyConnection(connection);
    }
}
