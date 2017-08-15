package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.dao.UserDAOImpl;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.epam.bokhan.receiver.ReceiverConstant.*;
import static by.epam.bokhan.validator.UserValidator.*;

public class UserReceiverImpl implements UserReceiver {


    @Override
    public void login(RequestContent content) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isRightData = false;
        String login = (String) content.getRequestParameters().get(LOGIN);
        String password = (String) content.getRequestParameters().get(PASSWORD);
        try {
            if (isLoginValid(login) && isPasswordValid(password)) {
                String hash = DigestUtils.md5Hex(password);
                User user = userDAO.getUserByLogin(login);
                if (login.equals(user.getLogin()) && hash.equals(user.getPassword())) {
                    isRightData = true;
                    user.setPassword(null);
                    content.insertAttribute(USER, user);
                }
            }
            content.insertParameter(IS_VALID, isRightData);

        } catch (DAOException e) {
            content.insertParameter(IS_VALID, isRightData);
            throw new ReceiverException(String.format("Can not log in. Reason : %s", e.getMessage()), e);
        }

        content.insertParameter(IS_VALID, isRightData);
    }

    @Override
    public void signOut(RequestContent content) {
        content.insertParameter(INVALIDATE, TRUE);
    }

    @Override
    public void registerUser(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isUserRegistered = false;
        String name = (String) requestContent.getRequestParameters().get(USER_NAME);
        String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
        String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(USER_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        try {
            if (isUserNameValid(name) && isUserSurnameValid(surname) && isLibraryCardIdValid(libraryCardValue)
                    && isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                int libraryCard = Integer.parseInt(libraryCardValue);
                User user = userDAO.getExplicitUserInfo(libraryCard);
                if (name.equalsIgnoreCase(user.getName()) && surname.equalsIgnoreCase(user.getSurname())) {
                    String hashedPassword = DigestUtils.md5Hex(password);
                    user.setPassword(hashedPassword);
                    user.setLogin(login);
                    isUserRegistered = userDAO.registerUser(user);
                }
            }
            requestContent.insertAttribute(IS_USER_REGISTERED, isUserRegistered);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addUser(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isUserAdded = false;
        String name = (String) requestContent.getRequestParameters().get(USER_NAME);
        String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
        String patronymic = (String) requestContent.getRequestParameters().get(USER_PATRONYMIC);
        String address = (String) requestContent.getRequestParameters().get(USER_ADDRESS);
        int role = Integer.parseInt((String) requestContent.getRequestParameters().get(USER_ROLE));
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(USER_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
        try {
            if (isUserNameValid(name) && isUserSurnameValid(surname) && isUserPatronymicValid(patronymic) &&
                    isUserAddressValid(address) && isUserMobilePhoneValid(phone)) {
                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                user.setAddress(address);
                user.setMobilePhone(phone);
                if (isLoginValid(login) && isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                    String hashedPassword = DigestUtils.md5Hex(password);
                    user.setLogin(login);
                    user.setPassword(hashedPassword);
                }
                isUserAdded = userDAO.addUser(user, role);
            }
            requestContent.insertAttribute(USER_IS_ADDED, isUserAdded);
        } catch (DAOException e) {
            requestContent.insertParameter(USER_IS_ADDED, isUserAdded);
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getAllUsers(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        ArrayList<User> users;
        try {
            users = dao.getAllUsers();
            requestContent.insertParameter(USERS, users);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void removeUser(RequestContent content) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isUserDeleted = false;
        try {
            String userIdValue = (String) content.getRequestParameters().get(USER_ID);
            if (isUserIdValid(userIdValue)) {
                int userId = Integer.parseInt(userIdValue);
                isUserDeleted = dao.removeUserById(userId);
            }
            content.insertAttribute(IS_USER_DELETED, isUserDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void findUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        List<User> user = new ArrayList<>();
        String queryValue = (String) requestContent.getRequestParameters().get(FIND_QUERY_VALUE);
        try {
            if (isLibraryCardIdValid(queryValue)) {
                int libraryCardId = Integer.parseInt(queryValue);
                user = dao.findUserByLibraryCard(libraryCardId);
            } else if (isUserSurnameValid(queryValue)) {
                user = dao.findUserBySurname(queryValue);
            }
            requestContent.insertParameter(FOUND_USER, user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void blockUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int userId;
        boolean isUserBlocked = false;
        try {
            String queryValue = (String) requestContent.getRequestParameters().get(BLOCK_QUERY_VALUE);
            if (isUserIdValid(queryValue)) {
                userId = Integer.parseInt(queryValue);
                isUserBlocked = dao.blockUser(userId);
            }
            requestContent.insertAttribute(IS_USER_BLOCKED, isUserBlocked);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void unblockUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int userId;
        boolean isUserUnblocked = false;
        try {
            String queryValue = (String) requestContent.getRequestParameters().get(UNBLOCK_QUERY_VALUE);
            if (isUserIdValid(queryValue)) {
                userId = Integer.parseInt(queryValue);
                isUserUnblocked = dao.unblockUser(userId);
            }
            requestContent.insertAttribute(IS_USER_UNBLOCKED, isUserUnblocked);
        } catch (DAOException e) {
            requestContent.insertAttribute(IS_USER_UNBLOCKED, isUserUnblocked);
            throw new ReceiverException(e);
        }
    }

    public void getBlockedUsers(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        List<User> blockedUsers;
        try {
            blockedUsers = dao.getBlockedUsers();
            requestContent.insertParameter(BLOCKED_USERS, blockedUsers);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getNotBlockedUsers(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        List<User> notBlockedUsers;
        try {
            notBlockedUsers = dao.getNotBlockedUsers();
            requestContent.insertParameter(NOT_BLOCKED_USERS, notBlockedUsers);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getExplicitUserInfo(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        User user = null;
        String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
        try {
            if (isLibraryCardIdValid(libraryCardValue)) {
                int libraryCard = Integer.parseInt(libraryCardValue);
                user = dao.getExplicitUserInfo(libraryCard);
            }
            requestContent.insertParameter(FOUND_USER, user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        int libraryCard;
        List<User> user = new ArrayList<>();
        try {
            String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
            if (isLibraryCardIdValid(libraryCardValue)) {
                libraryCard = Integer.parseInt(libraryCardValue);
                user = dao.findUserByLibraryCard(libraryCard);
            }
            requestContent.insertParameter(FOUND_USER, user);
        } catch (DAOException e) {
            requestContent.insertParameter(FOUND_USER, user);
            throw new ReceiverException(e);
        }
    }

    public void editUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isUserEdited = false;
        User user = new User();
        String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
        String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
        try {
            if (isLibraryCardIdValid(libraryCardValue) && isUserIdValid(userIdValue)) {
                int libraryCard = Integer.parseInt(libraryCardValue);
                int userId = Integer.parseInt(userIdValue);
                String name = (String) requestContent.getRequestParameters().get(USER_NAME);
                String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
                String patronymic = (String) requestContent.getRequestParameters().get(USER_PATRONYMIC);
                String address = (String) requestContent.getRequestParameters().get(USER_ADDRESS);
                String role = (String) requestContent.getRequestParameters().get(USER_ROLE);
                Role userRole = Role.valueOf(role.toUpperCase());
                String login = (String) requestContent.getRequestParameters().get(LOGIN);
                String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
//                todo isuservalid?
                if (isUserNameValid(name) && isUserSurnameValid(surname) && isUserPatronymicValid(patronymic)
                        && isUserMobilePhoneValid(phone)) {
                    user.setId(userId);
                    user.setLibraryCardNumber(libraryCard);
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPatronymic(patronymic);
                    user.setRole(userRole);
                    if (isLoginValid(login)) {
                        user.setLogin(login);
                    }
                    user.setMobilePhone(phone);
                    if (isUserAddressValid(address)) {
                        user.setAddress(address);
                    }
                    isUserEdited = dao.editUser(user);
                }
            }
            requestContent.insertAttribute(IS_USER_EDITED, isUserEdited);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

//    client part

    @Override
    public void changePassword(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isPasswordChanged = false;
        int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARY_CARD));
        String oldPassword = (String) requestContent.getRequestParameters().get(OLD_PASSWORD);
        String newPassword = (String) requestContent.getRequestParameters().get(NEW_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        try {
            if (isPasswordValid(oldPassword) && isPasswordValid(newPassword) && isPasswordValid(confirmPassword) && isPasswordsEquals(newPassword, confirmPassword)) {
                String hashedOldPassword = DigestUtils.md5Hex(oldPassword);
                String oldPasswordFromDB = dao.getPassword(libraryCard);
                if (isPasswordsEquals(hashedOldPassword, oldPasswordFromDB)) {
                    String hashedNewPassword = DigestUtils.md5Hex(newPassword);
                    isPasswordChanged = dao.changePassword(libraryCard, hashedNewPassword);
                }
            }
            requestContent.insertAttribute(IS_PASSWORD_CHANGED, isPasswordChanged);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void changeLogin(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isLoginChanged = false;
        int userId = Integer.parseInt((String) requestContent.getRequestParameters().get(USER_ID));
        String newLogin = (String) requestContent.getRequestParameters().get(NEW_LOGIN);
        try {
            if (isLoginValid(newLogin)) {
                isLoginChanged = dao.changeLogin(userId, newLogin);
            }
            requestContent.insertAttribute(IS_LOGIN_CHANGED, isLoginChanged);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}

