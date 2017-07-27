package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.sql.SQLException;

public interface UserReceiver extends Receiver {

    void login(RequestContent content) throws ReceiverException;
    void signOut(RequestContent content) throws ReceiverException;
}
