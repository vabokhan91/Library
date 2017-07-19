package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.LoginDAO;
import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class UserReceiverImpl implements UserReceiver {

    @Override
    public void signIn(RequestContent content) throws SQLException {
        LoginDAO dao = new LoginDAO();
        String login =(String) content.getRequestParameters().get("login");
        String password =(String) content.getRequestParameters().get("password");
        try {
            User client = dao.getUserByLogin(login);
            boolean isRightData = client.getLogin().equals(login) && client.getPassword().equals(password);
            content.insertAttributes("isValid", String.valueOf(isRightData));
            content.insertAttributes("username", client.getName());
            content.insertAttributes("usersurname", client.getSurname());
            content.insertAttributes("userpatronymic", client.getPatronymic());
            content.insertAttributes("useraddress", client.getAddress());
            content.insertAttributes("login", client.getLogin());
            content.insertAttributes("userrole", String.valueOf(client.getRoleId()));
            content.insertAttributes("usermobilephone", client.getMobilePhone());
            content.insertAttributes("userStatus", String.valueOf(client.isBlocked()));
            content.insertAttributes("user", client);
        } catch (SQLException e) {
            content.insertAttributes("isValid", "false");
            e.printStackTrace();
        }
    }

    @Override
    public void signOut(RequestContent content) {
        System.out.println("SignOut dao");
        content.insertAttributes("invalidate", "true");
    }

    public void addUser(RequestContent requestContent) {
        UserDAO userDAO = new UserDAO();
        String name = (String) requestContent.getRequestParameters().get("username");
        String surname = (String) requestContent.getRequestParameters().get("usersurname");
        String patronymic = (String) requestContent.getRequestParameters().get("userpatronymic");
        String address = (String) requestContent.getRequestParameters().get("useraddress");
        int role = Integer.parseInt((String) requestContent.getRequestParameters().get("user_role"));
        String login = (String) requestContent.getRequestParameters().get("login");
        String password = (String) requestContent.getRequestParameters().get("userpassword");
        String phone = (String) requestContent.getRequestParameters().get("usermobilephone");
        try {
            boolean isUserAdded = userDAO.addUser(name, surname, patronymic, address, role, login, password, phone);
            if (isUserAdded) {
                requestContent.insertAttributes("userIsAdded", String.valueOf(isUserAdded));
            }else {
                requestContent.insertAttributes("userIsAdded", "false");
            }
        } catch (SQLException e) {
            requestContent.insertAttributes("userIsAdded", "false");
            e.printStackTrace();
        }


    }

    public void removeUser(RequestContent content) {
        UserDAO dao = new UserDAO();
        int id = Integer.parseInt((String) content.getRequestParameters().get("user_id"));
        try {
            boolean isUserDeleted = dao.removeUser(id);
            if (isUserDeleted) {
                content.insertAttributes("isUserDeleted", String.valueOf(isUserDeleted));
            }else {
                content.insertAttributes("isUserDeleted", "false");
            }
        } catch (SQLException e) {
            content.insertAttributes("isUserDeleted", "false");
            e.printStackTrace();
        }
    }

    public void findUser(RequestContent requestContent) {
        UserDAO dao = new UserDAO();
        int id = Integer.parseInt((String) requestContent.getRequestParameters().get("user_id"));
        try {
            User user = dao.findUserById(id);
            if (user != null) {
                requestContent.insertAttributes("foundUser", user);
            }else {
                requestContent.insertAttributes("foundUser", null);
            }
        } catch (SQLException e) {
            requestContent.insertAttributes("foundUser", null);
            e.printStackTrace();
        }
    }
}
