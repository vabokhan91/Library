package by.epam.bokhan.dao;

import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;

import java.util.ArrayList;

/**
 * Created by vbokh on 25.07.2017.
 */
public interface UserDAO {
    boolean addUser(String name, String surname, String patronymic, String address, int roleId, String login, String password, String mobilephone) throws DAOException;

    boolean removeUserByLibraryCard(int id) throws DAOException;

    User findUserByLibraryCard(int libraryCard) throws DAOException ;

    User findUserByLogin(String login) throws DAOException ;

    boolean blockUserByLibraryCard(int librarianCard) throws DAOException;

    boolean unblockUserByLibraryCard(int libraryCard) throws DAOException ;

    User getUserByLogin(String login) throws DAOException;

    ArrayList<User> getAllUsers() throws DAOException;
}
