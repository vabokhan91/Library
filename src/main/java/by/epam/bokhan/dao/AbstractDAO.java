package by.epam.bokhan.dao;

import by.epam.bokhan.entity.User;
import by.epam.bokhan.manager.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by vbokh on 13.07.2017.
 */
public abstract class AbstractDAO {
    private static final Logger LOGGER = LogManager.getLogger();
    protected Connection connection;

    public AbstractDAO() {
    }

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, String.format("Can not close statement. Reason : %s", e.getMessage()));
            }
        }
    }

    void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, String.format("Can not close connection. Reason : %s", e.getMessage()));
            }
        }
    }

}
