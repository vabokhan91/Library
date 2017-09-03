package by.epam.bokhan.dao;

import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;

import java.util.List;


public interface UserDAO {
    /**
     * Adds new user to database
     *
     * @param user user to be added
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean addUser(User user) throws DAOException;

    /**
     * Registers new user
     *
     * @param user user to be registered
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean registerUser(User user) throws DAOException;

    /**
     * Removes user by id
     *
     * @param id user id
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean removeUserById(int id) throws DAOException;

    /**
     * Gets user by library card
     *
     * @param libraryCard user library card
     * @return List with user
     * @throws DAOException
     */
    List<User> getUserByLibraryCard(int libraryCard) throws DAOException;

    /**
     * Gets user by surname
     *
     * @param surname user surname
     * @return List with user
     * @throws DAOException
     */
    List<User> getUserBySurname(String surname) throws DAOException;

    /**
     * Gets not blocked users
     *
     * @return List with users
     * @throws DAOException
     */
    List<User> getNotBlockedUsers() throws DAOException;

    /**
     * Gets blocked users
     *
     * @return List with users
     * @throws DAOException
     */
    List<User> getBlockedUsers() throws DAOException;

    /**
     * Blocks user by id
     *
     * @param userId user id
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean blockUser(int userId) throws DAOException;

    /**
     * Unblocks user by id
     *
     * @param userId user id
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean unblockUser(int userId) throws DAOException;

    /**
     * Gets explicit user information
     *
     * @param libraryCard user library card
     * @return found user
     * @throws DAOException
     */
    User getExplicitUserInfo(int libraryCard) throws DAOException;

    /**
     * Gets user by login
     *
     * @param login user login
     * @return found user
     * @throws DAOException
     */
    User getUserByLogin(String login) throws DAOException;

    /**
     * Gets all users
     *
     * @return List of users
     * @throws DAOException
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * Edits user
     *
     * @param user user to be edited
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean editUser(User user) throws DAOException;

    /**
     * Changes user password
     * @param libraryCard user library card
     * @param oldPassword user old password
     * @param newPassword user new password
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean changePassword(int libraryCard, String oldPassword, String newPassword) throws DAOException;

    /**
     * Changes user login
     * @param userId user id
     * @param login user login
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean changeLogin(int userId, String login) throws DAOException;

    /**
     * Changes user photo
     * @param user user, whose photo will be changed
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean changePhoto(User user) throws DAOException;

    /**
     * Gets user and his orders
     * @param libraryCard user library card
     * @return user with his orders
     * @throws DAOException
     */
    User getUserWithOrders(int libraryCard) throws DAOException;

    /**
     * Gets user and his online orders
     * @param libraryCard user library card
     * @return user with his online orders
     * @throws DAOException
     */
    User getUserOnlineOrders(int libraryCard) throws DAOException;

}
