package by.epam.bokhan.dao.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by vbokh on 13.07.2017.
 */
class ConnectionCreator {
    static ProxyConnection getConnection() throws SQLException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("/resource/database");
        String url = resourceBundle.getString("db.url");
        String login = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");

        Connection connection = DriverManager.getConnection(url, login, password);
        return new ProxyConnection(connection);
    }
}
