package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.dao.UserDAOImpl;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserReceiverImpl implements UserReceiver {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private final String IS_VALID = "isValid";
    private final String USER = "user";
    private final String INVALIDATE = "invalidate";
    private final boolean TRUE = true;
    private final String USER_NAME = "user_name";
    private final String USER_SURNAME = "user_surname";
    private final String USER_PATRONYMIC = "user_patronymic";
    private final String USER_ADDRESS = "user_address";
    private final String USER_ROLE = "user_role";
    private final String USER_PASSWORD = "user_password";
    private final String CONFIRM_PASSWORD = "confirm_password";
    private final String USER_MOBILE_PHONE = "user_mobilephone";
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
    protected final String USER_ID = "user_id";
    private final String BY_SURNAME = "by_surname";
    private final String NOT_BLOCKED_USERS = "not_blocked_users";
    private final String BLOCKED_USERS = "blocked_users";
    private final String LIBRARY_CARD = "library_card";

    @Override
    public void login(RequestContent content) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();
        boolean isRightData = false;
        String login = (String) content.getRequestParameters().get(LOGIN);
        String password = (String) content.getRequestParameters().get(PASSWORD);
        String hash = DigestUtils.md5Hex(password);

        if (login != null && !login.isEmpty() && password != null && !password.isEmpty()) {
            try {
                User user = userDAO.getUserByLogin(login);
                isRightData = login.equals(user.getLogin()) && hash.equals(user.getPassword());
                if (isRightData) {
                    user.setPassword(null);
                    content.insertAttribute(USER, user);
                }
                content.insertParameter(IS_VALID, isRightData);

            } catch (DAOException e) {
                content.insertParameter(IS_VALID, isRightData);
                throw new ReceiverException(String.format("Can not log in. Reason : %s", e.getMessage()), e);
            }
        } else {
            content.insertParameter(IS_VALID, isRightData);
        }
    }

    @Override
    public void signOut(RequestContent content) {
        content.insertParameter(INVALIDATE, TRUE);
    }

    public void addUser(RequestContent requestContent) throws ReceiverException {
//        make validation
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
        try {
            if (password.equals(confirmPassword)) {
                String hashedPassword = null;
                if (password != null) {
                    hashedPassword = DigestUtils.md5Hex(password);
                }
                String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                user.setAddress(address);
                user.setLogin(login);
                user.setPassword(hashedPassword);
                user.setMobilePhone(phone);

                isUserAdded = userDAO.addUser(user, role);
            }
            requestContent.insertAttribute(USER_IS_ADDED, isUserAdded);
        } catch (DAOException e) {
            requestContent.insertParameter(USER_IS_ADDED, isUserAdded);
            throw new ReceiverException(e);
        }


    }

    public void getAllUsers(RequestContent requestContent) throws ReceiverException {
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
        int userId;
        boolean isUserDeleted = false;
        try {
            userId = Integer.parseInt((String) content.getRequestParameters().get(USER_ID));
            isUserDeleted = dao.removeUserById(userId);
            content.insertParameter(IS_USER_DELETED, isUserDeleted);
        } catch (DAOException e) {
            content.insertParameter(IS_USER_DELETED, isUserDeleted);
            throw new ReceiverException(String.format("Can not remove user. Reason : %s", e.getMessage()), e);
        }
    }

    public void findUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int libraryCardId;
        String surname;
        List<User> user = new ArrayList<>();
        String typeOfSearch = (String) requestContent.getRequestParameters().get(TYPE_OF_SEARCH);
        try {
            if (typeOfSearch.equalsIgnoreCase(BY_LIBRARY_CARD)) {
                libraryCardId = Integer.parseInt((String) requestContent.getRequestParameters().get(FIND_QUERY_VALUE));
                user = dao.findUserByLibraryCard(libraryCardId);
            } else if (typeOfSearch.equalsIgnoreCase(BY_SURNAME)) {
                surname = (String) requestContent.getRequestParameters().get(FIND_QUERY_VALUE);
                user = dao.findUserBySurname(surname);
            }
            requestContent.insertParameter(FOUND_USER, user);
        } catch (DAOException e) {
            throw new ReceiverException(String.format("Can not find user. Reason : %s", e.getMessage()), e);
        }
    }

    public void blockUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int userId;
        boolean isUserBlocked;
        try {
            userId = Integer.parseInt((String) requestContent.getRequestParameters().get(BLOCK_QUERY_VALUE));
            isUserBlocked = dao.blockUser(userId);
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
            userId = Integer.parseInt((String) requestContent.getRequestParameters().get(UNBLOCK_QUERY_VALUE));
            isUserUnblocked = dao.unblockUser(userId);
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
        User user;
        int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARY_CARD));
        try {
            user = dao.getExplicitUserInfo(libraryCard);
            requestContent.insertParameter(FOUND_USER, user);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        int libraryCard;
        List<User> user = new ArrayList<>();
        try {

            libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
            user = dao.findUserByLibraryCard(libraryCard);

            requestContent.insertParameter(FOUND_USER, user);
        } catch (DAOException e) {
            requestContent.insertParameter(FOUND_USER, user);
            throw new ReceiverException(String.format("Can not find user. Reason : %s", e.getMessage()), e);
        }
    }

    public void editUser(RequestContent requestContent) throws ReceiverException {
        UserDAOImpl dao = new UserDAOImpl();
        boolean isUserEdited = false;
        int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
        String name = (String) requestContent.getRequestParameters().get(USER_NAME);
        String surname = (String) requestContent.getRequestParameters().get(USER_SURNAME);
        String patronymic = (String) requestContent.getRequestParameters().get(USER_PATRONYMIC);
        String address = (String) requestContent.getRequestParameters().get(USER_ADDRESS);
        int role = Integer.parseInt((String) requestContent.getRequestParameters().get(USER_ROLE));
        String login = (String) requestContent.getRequestParameters().get(LOGIN);
        String phone = (String) requestContent.getRequestParameters().get(USER_MOBILE_PHONE);
        try {
            isUserEdited = dao.editUser(libraryCard, name, surname, patronymic, address, role, login, phone);
            requestContent.insertParameter("isUserEdited", isUserEdited);
        } catch (DAOException e) {
            requestContent.insertParameter("isUserEdited", isUserEdited);
            throw new ReceiverException(String.format("Can not edit user. Reason : %s", e.getMessage()), e);
        }
    }

//    client part

    @Override
    public void changePassword(RequestContent requestContent) throws ReceiverException {

        UserDAO dao = new UserDAOImpl();
        boolean isPasswordChanged = false;
        int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
        String oldPassword = (String) requestContent.getRequestParameters().get("old_password");
        String newPassword1 = (String) requestContent.getRequestParameters().get("new_password1");
        String newPassword2 = (String) requestContent.getRequestParameters().get("new_password2");
        Pattern pattern = Pattern.compile("[\\w!()*&^%$@]{1,12}");
        Matcher matcherForOldPassword = pattern.matcher(oldPassword);
        Matcher matcherForNewPassword1 = pattern.matcher(newPassword1);
        try {
            String oldPasswordFromDB = dao.getPassword(libraryCard);
            String hashedOldPassword = DigestUtils.md5Hex(oldPassword);
            String hashedNewPassword = DigestUtils.md5Hex(newPassword1);
            if (newPassword1.equals(newPassword2) && oldPasswordFromDB.equals(hashedOldPassword) && matcherForOldPassword.matches() && matcherForNewPassword1.matches()) {
                isPasswordChanged = dao.changePassword(libraryCard, hashedNewPassword);
            }
            requestContent.insertAttribute("isPasswordChanged", isPasswordChanged);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void changeLogin(RequestContent requestContent) throws ReceiverException {
        UserDAO dao = new UserDAOImpl();
        boolean isLoginChanged = false;
        int userId = Integer.parseInt((String) requestContent.getRequestParameters().get("user_id"));
        String newLogin = (String) requestContent.getRequestParameters().get("new_login");

        Pattern pattern = Pattern.compile("[^\\W]{1,12}");
        Matcher matcherForLogin = pattern.matcher(newLogin);
        try {

            if (matcherForLogin.matches()) {
                isLoginChanged = dao.changeLogin(userId, newLogin);
            }
            requestContent.insertAttribute("isLoginChanged", isLoginChanged);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}

