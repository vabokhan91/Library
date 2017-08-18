package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.sql.SQLException;

public interface UserReceiver extends Receiver {
    void registerUser(RequestContent requestContent) throws ReceiverException;

    void login(RequestContent content) throws ReceiverException;

    void signOut(RequestContent content) throws ReceiverException;

    void changePassword(RequestContent requestContent) throws ReceiverException;

    void changeLogin(RequestContent requestContent) throws ReceiverException;

    void findUser(RequestContent requestContent) throws ReceiverException;

    void removeUser(RequestContent requestContent) throws ReceiverException;

    void getAllUsers(RequestContent requestContent) throws ReceiverException;

    void addUser(RequestContent requestContent) throws ReceiverException;

    void uploadPhoto(RequestContent requestContent) throws ReceiverException;
}
