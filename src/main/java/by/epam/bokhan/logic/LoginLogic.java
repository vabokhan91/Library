package by.epam.bokhan.logic;

import by.epam.bokhan.dao.LoginDAO;
import by.epam.bokhan.entity.User;

import java.sql.SQLException;

/**
 * Created by vbokh on 13.07.2017.
 */
public class LoginLogic {
    public LoginLogic() {
    }

    public static boolean checkLogin(String login, String password) throws SQLException {
        LoginDAO dao = new LoginDAO();
        User client = dao.getUserByLogin(login);
        return client.getLogin().equals(login) && client.getPassword().equals(password);
    }
}
