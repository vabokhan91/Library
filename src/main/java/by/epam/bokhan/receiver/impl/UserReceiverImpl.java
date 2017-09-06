package by.epam.bokhan.receiver.impl;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.dao.impl.UserDAOImpl;
import by.epam.bokhan.util.ImageConverter;
import by.epam.bokhan.util.PasswordEncoder;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static by.epam.bokhan.receiver.impl.ReceiverConstant.*;
import static by.epam.bokhan.util.UserValidator.*;

public class UserReceiverImpl implements UserReceiver {



    /**
     * Login user and moves him to personal cabinet
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void login(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isRightData = false;
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(PASSWORD);
        try {
            if (isLoginValid(login) && isPasswordValid(password)) {
                String hashedPassword = PasswordEncoder.encodePassword(password);
                User user = userDAO.getUserByLogin(login);
                if (login.equals(user.getLogin()) && hashedPassword.equals(user.getPassword())) {
                    isRightData = true;
                    user.setPassword(null);
                    requestContent.insertAttribute(USER, user);
                }
            }
            requestContent.insertParameter(IS_VALID, isRightData);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Logout user and invalidates session
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void logout(RequestContent requestContent) {
        requestContent.insertParameter(INVALIDATE, TRUE);
    }

    /**
     * Registers new user
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void registerUser(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isUserRegistered;
        String name = (String) requestContent.getRequestParameters().get(USER_NAME);
        String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
        String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(USER_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        try {
            if (isUserNameValid(name) && isUserSurnameValid(surname) && isLibraryCardIdValid(libraryCardValue)
                    && isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword) && isLoginValid(login)) {
                User user = new User();
                user.setLogin(login);
                boolean isLoginExist = userDAO.isLoginExist(user);
                if (!isLoginExist) {
                    int libraryCard = Integer.parseInt(libraryCardValue);
                    String hashedPassword = PasswordEncoder.encodePassword(password);
                    user.setName(name);
                    user.setSurname(surname);
                    user.setLibraryCardNumber(libraryCard);
                    user.setPassword(hashedPassword);
                    isUserRegistered = userDAO.registerUser(user);
                    requestContent.insertAttribute(IS_LOGIN_EXIST, isLoginExist);
                    requestContent.insertAttribute(IS_USER_REGISTERED, isUserRegistered);
                } else {
                    requestContent.insertAttribute(IS_LOGIN_EXIST, isLoginExist);
                }
            }
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Adds new user
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void addUser(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isUserAdded;
        String name = (String) requestContent.getRequestParameters().get(USER_NAME);
        String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
        String patronymic = (String) requestContent.getRequestParameters().get(USER_PATRONYMIC);
        String address = (String) requestContent.getRequestParameters().get(USER_ADDRESS);
        String roleValue = (String) requestContent.getRequestParameters().get(USER_ROLE);
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(USER_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
        Part userPhoto = (Part) requestContent.getMultiTypeParts().get(USER_PHOTO);
        try {
            User user = new User();
            if (isLoginValid(login) && isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                user.setLogin(login);
                boolean isLoginExist = userDAO.isLoginExist(user);
                if (!isLoginExist && isUserNameValid(name) && isUserSurnameValid(surname) && isUserPatronymicValid(patronymic) &&
                        isUserAddressValid(address) && isUserMobilePhoneValid(phone) && isUserRoleValid(roleValue)) {
                    String hashedPassword = PasswordEncoder.encodePassword(password);
                    user.setPassword(hashedPassword);
                    Role role = Role.valueOf(roleValue.toUpperCase());
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPatronymic(patronymic);
                    user.setAddress(address);
                    user.setMobilePhone(phone);
                    user.setRole(role);
                    if (userPhoto != null) {
                        String image = ImageConverter.convertImageToBase64(userPhoto);
                        if (!image.isEmpty()) {
                            user.setPhoto(image);
                        }
                    }
                    isUserAdded = userDAO.addUser(user);
                    requestContent.insertAttribute(USER_IS_ADDED, isUserAdded);
                }else {
                    requestContent.insertAttribute(IS_LOGIN_EXIST, isLoginExist);
                }
            }else if(isUserNameValid(name) && isUserSurnameValid(surname) && isUserPatronymicValid(patronymic) &&
                    isUserAddressValid(address) && isUserMobilePhoneValid(phone) && isUserRoleValid(roleValue)){
                Role role = Role.valueOf(roleValue.toUpperCase());
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                user.setAddress(address);
                user.setMobilePhone(phone);
                user.setRole(role);
                if (userPhoto != null) {
                    String image = ImageConverter.convertImageToBase64(userPhoto);
                    if (!image.isEmpty()) {
                        user.setPhoto(image);
                    }
                }
                isUserAdded = userDAO.addUser(user);
                requestContent.insertAttribute(USER_IS_ADDED, isUserAdded);
            }
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Gets all users and inserts them into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void getAllUsers(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        try {
            List<User> users = dao.getAllUsers();
            requestContent.insertParameter(USERS, users);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Removes user
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void removeUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isUserDeleted = false;
        try {
            String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
            if (isUserIdValid(userIdValue)) {
                int userId = Integer.parseInt(userIdValue);
                isUserDeleted = dao.removeUserById(userId);
            }
            requestContent.insertAttribute(IS_USER_DELETED, isUserDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Gets user and inserts him into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void getUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        List<User> user = new ArrayList<>();
        String queryValue = (String) requestContent.getRequestParameters().get(FIND_QUERY_VALUE);
        try {
            if (isLibraryCardIdValid(queryValue)) {
                int libraryCardId = Integer.parseInt(queryValue);
                user = dao.getUserByLibraryCard(libraryCardId);
            } else if (isUserSurnameValid(queryValue)) {
                user = dao.getUserBySurname(queryValue);
            }
            requestContent.insertParameter(FOUND_USER, user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Blocks user and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void blockUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isUserBlocked = false;
        try {
            String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
            if (isUserIdValid(userIdValue)) {
                int userId = Integer.parseInt(userIdValue);
                isUserBlocked = dao.blockUser(userId);
            }
            requestContent.insertAttribute(IS_USER_BLOCKED, isUserBlocked);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Unblocks user and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void unblockUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isUserUnblocked = false;
        try {
            String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
            if (isUserIdValid(userIdValue)) {
                int userId = Integer.parseInt(userIdValue);
                isUserUnblocked = dao.unblockUser(userId);
            }
            requestContent.insertAttribute(IS_USER_UNBLOCKED, isUserUnblocked);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Gets all blocked users and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
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

    /**
     * Gets all not blocked users and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
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

    /**
     * Gets explicit user information and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
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

    /**
     * Edits user and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void editUser(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isUserEdited = false;
        String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
        String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
        String name = (String) requestContent.getRequestParameters().get(USER_NAME);
        String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
        String patronymic = (String) requestContent.getRequestParameters().get(USER_PATRONYMIC);
        String address = (String) requestContent.getRequestParameters().get(USER_ADDRESS);
        String role = (String) requestContent.getRequestParameters().get(USER_ROLE);
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(NEW_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
        Part userPhoto = (Part) requestContent.getMultiTypeParts().get(USER_PHOTO);
        try {
            if (isLibraryCardIdValid(libraryCardValue) && isUserIdValid(userIdValue) && isUserNameValid(name)
                    && isUserSurnameValid(surname) && isUserPatronymicValid(patronymic) && isUserMobilePhoneValid(phone) && isUserAddressValid(address)) {
                User user = new User();
                int libraryCard = Integer.parseInt(libraryCardValue);
                int userId = Integer.parseInt(userIdValue);
                Role userRole = Role.valueOf(role);
                user.setId(userId);
                user.setLibraryCardNumber(libraryCard);
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                user.setRole(userRole);
                user.setMobilePhone(phone);
                user.setAddress(address);
                user.setLogin(login);
                if (isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                    String hashedPassword = PasswordEncoder.encodePassword(password);
                    user.setPassword(hashedPassword);
                }
                if (isLoginValid(login)) {
                    user.setLogin(login);
                }
                if (userPhoto != null) {
                    String image = ImageConverter.convertImageToBase64(userPhoto);
                    if (!image.isEmpty()) {
                        user.setPhoto(image);
                    }
                }
                isUserEdited = dao.editUser(user);
            }
            requestContent.insertAttribute(IS_USER_EDITED, isUserEdited);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Changes user password and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void changePassword(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isPasswordChanged = false;
        String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
        String oldPassword = (String) requestContent.getRequestParameters().get(OLD_PASSWORD);
        String newPassword = (String) requestContent.getRequestParameters().get(NEW_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        try {
            if (isPasswordValid(oldPassword) && isPasswordValid(newPassword) && isPasswordValid(confirmPassword)
                    && isPasswordsEquals(newPassword, confirmPassword) && isUserIdValid(userIdValue)) {
                int userId = Integer.parseInt(userIdValue);
                String hashedOldPassword = PasswordEncoder.encodePassword(oldPassword);
                String hashedNewPassword = PasswordEncoder.encodePassword(newPassword);
                isPasswordChanged = dao.changePassword(userId, hashedOldPassword, hashedNewPassword);
            }
            requestContent.insertAttribute(IS_PASSWORD_CHANGED, isPasswordChanged);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Changes user login and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void changeLogin(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isLoginChanged = false;
        String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
        String newLogin = (String) requestContent.getRequestParameters().get(NEW_LOGIN);
        try {
            if (isUserIdValid(userIdValue) && isLoginValid(newLogin)) {
                int userId = Integer.parseInt(userIdValue);
                isLoginChanged = dao.changeLogin(userId, newLogin);
            }
            requestContent.insertAttribute(IS_LOGIN_CHANGED, isLoginChanged);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Upload user photo and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void uploadUserPhoto(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isPhotoUploaded = false;
        String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
        Part userPhoto = (Part) requestContent.getMultiTypeParts().get(USER_PHOTO);
        try {
            if (isUserIdValid(userIdValue) && userPhoto != null) {
                User user = new User();
                int userId = Integer.parseInt(userIdValue);
                String convertedPhoto = ImageConverter.convertImageToBase64(userPhoto);
                user.setId(userId);
                user.setPhoto(convertedPhoto);
                isPhotoUploaded = dao.changePhoto(user);
                if (isPhotoUploaded) {
                    ((User) requestContent.getSessionAttributes().get(USER)).setPhoto(convertedPhoto);
                }
            }
            requestContent.insertAttribute(IS_PHOTO_UPLOADED, isPhotoUploaded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Gets user orders and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void getUserOrders(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        try {
            String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
            if (isLibraryCardIdValid(libraryCardValue)) {
                int libraryCard = Integer.parseInt(libraryCardValue);
                user = userDAO.getUserWithOrders(libraryCard);
            }
            requestContent.insertAttribute(USER_ORDERS, user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Gets user online orders and inserts result of operation into RequestContent object
     *
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     */
    @Override
    public void getUserOnlineOrders(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        try {
            String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
            if (isLibraryCardIdValid(libraryCardValue)) {
                int libraryCard = Integer.parseInt(libraryCardValue);
                user = userDAO.getUserOnlineOrders(libraryCard);
            }
            requestContent.insertParameter(USER_ORDERS, user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }


}

