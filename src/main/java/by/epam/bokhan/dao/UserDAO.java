package by.epam.bokhan.dao;

import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;

import java.util.List;


public interface UserDAO {
    /*Adds new user to database*/
    boolean addUser(User user) throws DAOException;

    /*Registers new user*/
    boolean registerUser(User user) throws DAOException;

    /*Checks if login already exist in database*/
    boolean isLoginExist(User user) throws DAOException;

   /*Removes user by id*/
    boolean removeUserById(int id) throws DAOException;

    /*Gets user by library card*/
    List<User> getUserByLibraryCard(int libraryCard) throws DAOException;

    /*Gets user by surname*/
    List<User> getUserBySurname(String surname) throws DAOException;

    /*Gets not blocked users*/
    List<User> getNotBlockedUsers() throws DAOException;

    /*Gets blocked users*/
    List<User> getBlockedUsers() throws DAOException;

    /*Blocks user by id*/
    boolean blockUser(int userId) throws DAOException;

   /*Unblocks user by id*/
    boolean unblockUser(int userId) throws DAOException;

    /*Gets explicit user information*/
    User getExplicitUserInfo(int libraryCard) throws DAOException;

    /*Gets user by login*/
    User getUserByLogin(String login) throws DAOException;

    /*Gets all users*/
    List<User> getAllUsers() throws DAOException;

    /*Edits user*/
    boolean editUser(User user) throws DAOException;

    /*Changes user password*/
    boolean changePassword(int libraryCard, String oldPassword, String newPassword) throws DAOException;

    /*Changes user login*/
    boolean changeLogin(int userId, String login) throws DAOException;

    /*Changes user photo*/
    boolean changePhoto(User user) throws DAOException;

    /*Gets user and his orders*/
    User getUserWithOrders(int libraryCard) throws DAOException;

    /*Gets user and his online orders*/
    User getUserOnlineOrders(int libraryCard) throws DAOException;

}
