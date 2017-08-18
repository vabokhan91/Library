package by.epam.bokhan.dao;

import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;

import java.util.ArrayList;
import java.util.List;


public interface UserDAO {
    boolean addUser(User user) throws DAOException;

    boolean registerUser(User user) throws DAOException;

    boolean removeUserById(int id) throws DAOException;

    List<User> findUserByLibraryCard(int libraryCard) throws DAOException ;

    List<User> findUserBySurname(String surname) throws DAOException ;

    List<User> getNotBlockedUsers() throws DAOException;

    List<User> getBlockedUsers() throws DAOException;

    boolean blockUser(int userId) throws DAOException;

    boolean unblockUser(int userId) throws DAOException;

    User getExplicitUserInfo(int libraryCard) throws DAOException;

    User getUserByLogin(String login) throws DAOException;

    ArrayList<User> getAllUsers() throws DAOException;

    boolean editUser(User user) throws DAOException;

    boolean changePassword(int libraryCard, String newPassword) throws DAOException ;

    String getPassword(int libraryCard) throws DAOException;

    boolean changeLogin(int userId, String login) throws DAOException;

    boolean changePhoto(User user) throws DAOException;

}
