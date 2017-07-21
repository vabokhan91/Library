package by.epam.bokhan.receiver;

import by.epam.bokhan.command.CommandType;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;

import java.sql.SQLException;

/**
 * Created by vbokh on 14.07.2017.
 */
public interface Receiver {
    default void action(CommandType type, RequestContent content) throws DAOException {
        type.doReceiver(content);
    }

}
