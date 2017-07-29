package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.dao.UserDAOImpl;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private final String IS_VALID = "isValid";
    private final String USER = "user";
    private final String INVALIDATE = "invalidate";
    private final boolean TRUE = true;
    private final String USER_NAME = "username";
    private final String USER_SURNAME = "usersurname";
    private final String USER_PATRONYMIC = "userpatronymic";
    private final String USER_ADDRESS = "useraddress";
    private final String USER_ROLE = "user_role";
    private final String USER_PASSWORD = "userpassword";
    private final String USER_MOBILE_PHONE = "usermobilephone";
    private final String USER_IS_ADDED = "userIsAdded";
    private final String TYPE_OF_SEARCH = "type_of_search";
    private final String BY_LIBRARY_CARD = "by_library_card";
    private final String REMOVE_QUERY_VALUE = "remove_query_value";
    private final String BY_LOGIN = "by_login";
    private final String IS_USER_DELETED = "isUserDeleted";
    private final String FIND_QUERY_VALUE = "find_query_value";
    private final String FOUND_USER = "foundUser";
    private final String BLOCK_QUERY_VALUE = "block_query_value";
    private final String IS_USER_BLOCKED = "isUserBlocked";
    private final String UNBLOCK_QUERY_VALUE = "unblock_query_value";
    private final String IS_USER_UNBLOCKED = "isUserUnblocked";

    @Override
    public void login(RequestContent content) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isRightData = false;
        String login = (String) content.getRequestParameters().get(LOGIN);
        String password = (String) content.getRequestParameters().get(PASSWORD);
        String hash = DigestUtils.md5Hex(password);
        try {
            User user = userDAO.getUserByLogin(login);
            isRightData = user.getLogin().equals(login) && user.getPassword().equals(hash);
            user.setPassword(null);
            content.insertParameter(IS_VALID, isRightData);
            content.insertParameter(USER, user);
        } catch (DAOException e) {
            content.insertParameter(IS_VALID, isRightData);
            throw new ReceiverException(String.format("Can not log in. Reason : %s", e.getMessage()), e);
        }
    }

    @Override
    public void signOut(RequestContent content) {
        content.insertParameter(INVALIDATE, TRUE);
    }

    public void addUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl userDAO = new UserDAOImpl();
        boolean isUserAdded = false;
        String name = (String) requestContent.getRequestParameters().get(USER_NAME);
        String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
        String patronymic = (String) requestContent.getRequestParameters().get(USER_PATRONYMIC);
        String address = (String) requestContent.getRequestParameters().get(USER_ADDRESS);
        int role = Integer.parseInt((String) requestContent.getRequestParameters().get(USER_ROLE));
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(USER_PASSWORD);
        String hashedPassword = password != null ? DigestUtils.md5Hex(password) : null;
        String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
        try {
            isUserAdded = userDAO.addUser(name, surname, patronymic, address, role, login, hashedPassword, phone);
            if (isUserAdded) {
                requestContent.insertParameter(USER_IS_ADDED, isUserAdded);
            } else {
                requestContent.insertParameter(USER_IS_ADDED, isUserAdded);
            }
        } catch (DAOException e) {
            requestContent.insertParameter(USER_IS_ADDED, isUserAdded);
            throw new ReceiverException(String.format("Can not add user. Reason : %s", e.getMessage()), e);
        }


    }

    public void getAllUsers(RequestContent requestContent) throws ReceiverException{
        UserDAO dao = new UserDAOImpl();
        ArrayList<User> users;
        try {
            users = dao.getAllUsers();
            requestContent.insertParameter("users", users);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void removeUser(RequestContent content) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int libraryCard;
        boolean isUserDeleted = false;
        try {
                libraryCard = Integer.parseInt((String) content.getRequestParameters().get(REMOVE_QUERY_VALUE));
                isUserDeleted = dao.removeUserByLibraryCard(libraryCard);
            content.insertParameter(IS_USER_DELETED, isUserDeleted);
        } catch (DAOException e) {
            content.insertParameter(IS_USER_DELETED, isUserDeleted);
            throw new ReceiverException(String.format("Can not remove user. Reason : %s", e.getMessage()), e);
        }
    }

    public void findUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int libraryCard;
        String login;
        User user = null;
        String typeOfSearch = (String) requestContent.getRequestParameters().get(TYPE_OF_SEARCH);
        try {
            if (typeOfSearch.equalsIgnoreCase(BY_LIBRARY_CARD)) {
                libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(FIND_QUERY_VALUE));
                user = dao.findUserByLibraryCard(libraryCard);
            } else if (typeOfSearch.equalsIgnoreCase(BY_LOGIN)) {
                login = (String) requestContent.getRequestParameters().get(FIND_QUERY_VALUE);
                user = dao.findUserByLogin(login);
            }
            if (user != null) {
                requestContent.insertParameter(FOUND_USER, user);
            } else {
                requestContent.insertParameter(FOUND_USER, null);
            }
        } catch (DAOException e) {
            requestContent.insertParameter(FOUND_USER, null);
            throw new ReceiverException(String.format("Can not find user. Reason : %s", e.getMessage()), e);
        }
    }

    public void blockUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int libraryCard;
        boolean isUserBlocked = false;
        try {
            libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(BLOCK_QUERY_VALUE));
            isUserBlocked = dao.blockUserByLibraryCard(libraryCard);
            requestContent.insertParameter(IS_USER_BLOCKED, isUserBlocked);
        } catch (DAOException e) {
            requestContent.insertParameter(IS_USER_BLOCKED, isUserBlocked);
            throw new ReceiverException(String.format("Can not block user. Reason : %s", e.getMessage()), e);
        }
    }

    public void unblockUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int libraryCard;
        boolean isUserUnblocked = false;
        try {
            libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(UNBLOCK_QUERY_VALUE));
            isUserUnblocked = dao.unblockUserByLibraryCard(libraryCard);
            requestContent.insertParameter(IS_USER_UNBLOCKED, isUserUnblocked);
        } catch (DAOException e) {
            requestContent.insertParameter(IS_USER_UNBLOCKED, isUserUnblocked);
            throw new ReceiverException(e);
        }
    }

    public void getBlockedUsers(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        List<User> blockedUsers;
        try {
            blockedUsers = dao.getBlockedUsers();
            requestContent.insertParameter("blocked_users", blockedUsers);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getNotBlockedUsers(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        List<User> blockedUsers;
        try {
            blockedUsers = dao.getNotBlockedUsers();
            requestContent.insertParameter("not_blocked_users", blockedUsers);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getExplicitUserInfo(RequestContent requestContent) throws ReceiverException{
        UserDAOImpl dao = new UserDAOImpl();
        User user;
        int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
        try {
            user = dao.getExplicitUserInfo(libraryCard);
            requestContent.insertParameter("foundUser", user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

}
