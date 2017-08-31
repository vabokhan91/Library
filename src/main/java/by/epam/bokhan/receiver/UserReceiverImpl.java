package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.dao.UserDAOImpl;
import by.epam.bokhan.encoder.PasswordEncoder;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
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

import static by.epam.bokhan.receiver.ReceiverConstant.*;
import static by.epam.bokhan.validator.UserValidator.*;

public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void login(RequestContent content) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isRightData = false;
        String login = (String) content.getRequestParameters().get(LOGIN);
        String password = (String) content.getRequestParameters().get(PASSWORD);
        try {
            if (isLoginValid(login) && isPasswordValid(password)) {
                String hashedPassword = PasswordEncoder.encodePassword(password);
                User user = userDAO.getUserByLogin(login);
                if (login.equals(user.getLogin()) && hashedPassword.equals(user.getPassword())) {
                    isRightData = true;
                    user.setPassword(null);
                    content.insertAttribute(USER, user);
                }
            }
            content.insertParameter(IS_VALID, isRightData);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void logout(RequestContent content) {
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
                User user = new User();
                int libraryCard = Integer.parseInt(libraryCardValue);
                String hashedPassword = PasswordEncoder.encodePassword(password);
                user.setName(name);
                user.setSurname(surname);
                user.setLibraryCardNumber(libraryCard);
                user.setLogin(login);
                user.setPassword(hashedPassword);
                isUserRegistered = userDAO.registerUser(user);
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
        String roleValue = (String) requestContent.getRequestParameters().get(USER_ROLE);
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(USER_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
        Part userPhoto = (Part) requestContent.getMultiTypeParts().get(USER_PHOTO);
        try {
            if (isUserNameValid(name) && isUserSurnameValid(surname) && isUserPatronymicValid(patronymic) &&
                    isUserAddressValid(address) && isUserMobilePhoneValid(phone) && isUserRoleValid(roleValue)) {
                User user = new User();
                Role role = Role.valueOf(roleValue.toUpperCase());
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                user.setAddress(address);
                user.setMobilePhone(phone);
                user.setRole(role);
                if (isLoginValid(login) && isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                    String hashedPassword = PasswordEncoder.encodePassword(password);
                    user.setLogin(login);
                    user.setPassword(hashedPassword);
                }
                if (userPhoto != null) {
                    String image = convertImageToBase64(userPhoto);
                    if (!image.isEmpty()) {
                        user.setPhoto(image);
                    }
                }
                isUserAdded = userDAO.addUser(user);
            }
            requestContent.insertAttribute(USER_IS_ADDED, isUserAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

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

    @Override
    public void removeUser(RequestContent content) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isUserDeleted = false;
        try {
            String userIdValue = (String) content.getRequestParameters().get(USER_ID);
            if (isUserIdValid(userIdValue)) {
                int userId = Integer.parseInt(userIdValue);
                isUserDeleted = dao.removeUser(userId);
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
                if (isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                    String hashedPassword = PasswordEncoder.encodePassword(password);
                    user.setPassword(hashedPassword);
                }
                if (isLoginValid(login)) {
                    user.setLogin(login);
                }
                if (userPhoto != null) {
                    String image = convertImageToBase64(userPhoto);
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

    @Override
    public void changePassword(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isPasswordChanged = false;
        String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
        String oldPassword = (String) requestContent.getRequestParameters().get(OLD_PASSWORD);
        String newPassword = (String) requestContent.getRequestParameters().get(NEW_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        try {
            if (isPasswordValid(oldPassword) && isPasswordValid(newPassword) && isPasswordValid(confirmPassword)
                    && isPasswordsEquals(newPassword, confirmPassword) && isLibraryCardIdValid(libraryCardValue)) {
                int libraryCard = Integer.parseInt(libraryCardValue);
                String hashedOldPassword = PasswordEncoder.encodePassword(oldPassword);
                String hashedNewPassword = PasswordEncoder.encodePassword(newPassword);
                isPasswordChanged = dao.changePassword(libraryCard, hashedOldPassword, hashedNewPassword);
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
                String convertedPhoto = convertImageToBase64(userPhoto);
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

    @Override
    public void getUserOrders(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        try {
            String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
            if (isLibraryCardIdValid(libraryCardValue)) {
                int libraryCard = Integer.parseInt(libraryCardValue);
                user = userDAO.getUserOrders(libraryCard);
            }
            requestContent.insertAttribute(USER_ORDERS, user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

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

    private String convertImageToBase64(Part userPhoto) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        String photo = null;
        try {
            inputStream = userPhoto.getInputStream();
            int reads = inputStream.read();
            while (reads != -1) {
                byteArrayOutputStream.write(reads);
                reads = inputStream.read();
            }
            byte[] b = byteArrayOutputStream.toByteArray();
            photo = Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, String.format("Can not convert to string. Reason : %s", e));
        } finally {
            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, String.format("Can not close stream. Reason : %s", e));
            }
        }
        return photo;
    }
}

