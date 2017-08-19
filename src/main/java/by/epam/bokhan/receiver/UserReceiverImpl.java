package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.dao.UserDAOImpl;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import org.apache.commons.codec.digest.DigestUtils;
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
    private final String IS_PHOTO_UPLOADED = "isPhotoUploaded";

    @Override
    public void login(RequestContent content) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isRightData = false;
        String login = (String) content.getRequestParameters().get(LOGIN);
        String password = (String) content.getRequestParameters().get(PASSWORD);
        try {
            if (isLoginValid(login) && isPasswordValid(password)) {
                String hashedPassword = DigestUtils.md5Hex(password);
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
        String roleValue = (String) requestContent.getRequestParameters().get(USER_ROLE);
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String password = (String) requestContent.getRequestParameters().get(USER_PASSWORD);
        String confirmPassword = (String) requestContent.getRequestParameters().get(CONFIRM_PASSWORD);
        String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
        Part userPhoto = (Part) requestContent.getMultiTypeParts().get(USER_PHOTO);
        try {
            if (isUserNameValid(name) && isUserSurnameValid(surname) && isUserPatronymicValid(patronymic) &&
                    isUserAddressValid(address) && isUserMobilePhoneValid(phone) && isUserRoleValid(roleValue)) {
                Role role = Role.valueOf(roleValue.toUpperCase());
                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                user.setAddress(address);
                user.setMobilePhone(phone);
                user.setRole(role);
                if (isLoginValid(login) && isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                    String hashedPassword = DigestUtils.md5Hex(password);
                    user.setLogin(login);
                    user.setPassword(hashedPassword);
                }
                if (userPhoto != null) {
                    String image = convertImageToString(userPhoto);
                    user.setPhoto(image);
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
                Role userRole = Role.valueOf(role.toUpperCase());
                user.setId(userId);
                user.setLibraryCardNumber(libraryCard);
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                user.setRole(userRole);
                user.setMobilePhone(phone);
                user.setAddress(address);
                if (isPasswordValid(password) && isPasswordValid(confirmPassword) && isPasswordsEquals(password, confirmPassword)) {
                    String hashedPassword = DigestUtils.md5Hex(password);
                    user.setPassword(hashedPassword);
                }
                if (isLoginValid(login)) {
                    user.setLogin(login);
                }
                if (userPhoto != null) {
                    String image = convertImageToString(userPhoto);
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
                String hashedOldPassword = DigestUtils.md5Hex(oldPassword);
                String oldPasswordFromDB = dao.getUserPassword(libraryCard);
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
    public void uploadPhoto(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isPhotoUploaded = false;
        String userIdValue = (String) requestContent.getRequestParameters().get(USER_ID);
        if (isUserIdValid(userIdValue)) {
            try {
                int userId = Integer.parseInt(userIdValue);
                Part userPhoto = (Part) requestContent.getMultiTypeParts().get(USER_PHOTO);
                if (userPhoto != null) {
                    String image = convertImageToString(userPhoto);
                    User user = new User();
                    user.setId(userId);
                    if (!image.isEmpty()) {
                        user.setPhoto(image);
                        isPhotoUploaded = dao.changePhoto(user);
                    }
                    if (isPhotoUploaded) {
                        ((User) requestContent.getSessionAttributes().get(USER)).setPhoto(image);
                    }
                }
                requestContent.insertAttribute(IS_PHOTO_UPLOADED, isPhotoUploaded);
            } catch (DAOException e) {
                throw new ReceiverException(e);
            }
        }
    }

    private String convertImageToString(Part userPhoto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        String photo = null;
        try {
            is = userPhoto.getInputStream();
            int reads = is.read();
            while (reads != -1) {
                baos.write(reads);
                reads = is.read();
            }
            byte[] b = baos.toByteArray();
            photo = Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, String.format("Can not convert to string. Reason : %s", e));
        }finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, String.format("Can not close stream. Reason : %s", e));
            }
        }
        return photo;
    }
}

