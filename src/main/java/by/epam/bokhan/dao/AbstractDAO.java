package by.epam.bokhan.dao;

import by.epam.bokhan.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vbokh on 13.07.2017.
 */
public abstract class AbstractDAO<T> {
    protected Connection connection;

    public AbstractDAO() {
    }

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

}
