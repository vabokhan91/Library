package by.epam.bokhan.receiver;

import by.epam.bokhan.command.CommandType;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.sql.SQLException;


public interface Receiver {
    default void action(CommandType type, RequestContent content) throws ReceiverException {
        type.doReceiver(content);
    }
}
