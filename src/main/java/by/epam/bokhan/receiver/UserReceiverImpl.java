package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.LoginDAO;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void login(RequestContent content) throws DAOException{
        LoginDAO dao = new LoginDAO();
        String login =(String) content.getRequestParameters().get("login");
        String password =(String) content.getRequestParameters().get("password");
        String hash = DigestUtils.md5Hex(password);
        try {
            User client = dao.getUserByLogin(login);
            boolean isRightData = client.getLogin().equals(login) && client.getPassword().equals(hash);
            content.insertParameter("isValid", String.valueOf(isRightData));
            /*content.insertParameter("username", client.getName());
            content.insertParameter("usersurname", client.getSurname());
            content.insertParameter("userpatronymic", client.getPatronymic());
            content.insertParameter("useraddress", client.getAddress());
            content.insertParameter("login", client.getLogin());
            content.insertParameter("userrole", String.valueOf(client.getRoleId()));
            content.insertParameter("usermobilephone", client.getMobilePhone());
            content.insertParameter("userStatus", String.valueOf(client.isBlocked()));*/
            content.insertParameter("user", client);
        } catch (SQLException e) {
            content.insertParameter("isValid", "false");
            LOGGER.log(Level.ERROR, String.format("Can not log in. Reason : %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    @Override
    public void signOut(RequestContent content) {
        content.insertParameter("invalidate", "true");
    }

    public void addUser(RequestContent requestContent) throws DAOException{
        UserDAO userDAO = new UserDAO();
        boolean isUserAdded = false;
        String name = (String) requestContent.getRequestParameters().get("username");
        String surname = (String) requestContent.getRequestParameters().get("usersurname");
        String patronymic = (String) requestContent.getRequestParameters().get("userpatronymic");
        String address = (String) requestContent.getRequestParameters().get("useraddress");
        int role = Integer.parseInt((String) requestContent.getRequestParameters().get("user_role"));
        String login = (String) requestContent.getRequestParameters().get("login");
        String password = (String) requestContent.getRequestParameters().get("userpassword");
        String hashedPassword = password != null ? DigestUtils.md5Hex(password) : null;
        String phone = (String) requestContent.getRequestParameters().get("usermobilephone");
        try {
            isUserAdded = userDAO.addUser(name, surname, patronymic, address, role, login, hashedPassword, phone);
            if (isUserAdded) {
                requestContent.insertParameter("userIsAdded", isUserAdded);
            }else {
                requestContent.insertParameter("userIsAdded", isUserAdded);
            }
        } catch (SQLException e) {
            requestContent.insertParameter("userIsAdded", isUserAdded);
            LOGGER.log(Level.ERROR, String.format("Can not add user. Reason : %s", e.getMessage()));
            throw new DAOException(e);
        }


    }

    public void removeUser(RequestContent content) throws DAOException {
        UserDAO dao = new UserDAO();
        int id;
        String login;
        boolean isUserDeleted = false;
        String typeOfSearch = (String) content.getRequestParameters().get("type_of_search");
        try {
            if (typeOfSearch.equalsIgnoreCase("by_library_card")) {
                id = Integer.parseInt((String) content.getRequestParameters().get("remove_query_value"));
                isUserDeleted = dao.removeUserById(id);
            } else if (typeOfSearch.equalsIgnoreCase("by_login")) {
                login = (String) content.getRequestParameters().get("remove_query_value");
                isUserDeleted = dao.removeUserByLogin(login);
            }
            if (isUserDeleted) {
                content.insertParameter("isUserDeleted", String.valueOf(isUserDeleted));
            }else {
                content.insertParameter("isUserDeleted", "false");
            }
        } catch (SQLException e) {
            content.insertParameter("isUserDeleted", "false");
            LOGGER.log(Level.ERROR, String.format("Can not remove user. Reason : %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    public void findUser(RequestContent requestContent) throws DAOException{
        UserDAO dao = new UserDAO();
        int libraryCard;
        String login;
        User user = null;
        String typeOfSearch = (String) requestContent.getRequestParameters().get("type_of_search");
        try {
            if (typeOfSearch.equalsIgnoreCase("by_library_card")) {
                libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("find_query_value"));
                user = dao.findUserByLibraryCard(libraryCard);
            }else if(typeOfSearch.equalsIgnoreCase("by_login")){
                login = (String) requestContent.getRequestParameters().get("find_query_value");
                user = dao.findUserByLogin(login);
            }
            if (user != null) {
                requestContent.insertParameter("foundUser", user);
            }else {
                requestContent.insertParameter("foundUser", null);
            }
        }catch (SQLException e) {
            requestContent.insertParameter("foundUser", null);
            LOGGER.log(Level.ERROR, String.format("Can not find user. Reason : %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    public void blockUser(RequestContent requestContent) throws DAOException{
        UserDAO dao = new UserDAO();
        int libraryCard;
        String login;
        boolean isUserBlocked = false;
        String typeOfSearch = (String) requestContent.getRequestParameters().get("type_of_search");
        try {
            if (typeOfSearch.equalsIgnoreCase("by_library_card")) {
                libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("block_query_value"));
                isUserBlocked = dao.blockUserByLibraryCard(libraryCard);
            }else if(typeOfSearch.equalsIgnoreCase("by_login")){
                login = (String) requestContent.getRequestParameters().get("block_query_value");
                isUserBlocked = dao.blockUserByLogin(login);
            }
            requestContent.insertParameter("isUserBlocked", isUserBlocked);
        }catch (SQLException e) {
            requestContent.insertParameter("foundUser", null);
            LOGGER.log(Level.ERROR, String.format("Can not find user. Reason : %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

}
