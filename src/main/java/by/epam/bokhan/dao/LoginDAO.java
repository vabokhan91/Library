package by.epam.bokhan.dao;

import by.epam.bokhan.dao.connectionpool.ConnectionPool;
import by.epam.bokhan.entity.User;

import java.sql.*;

/**
 * Created by vbokh on 13.07.2017.
 */
public class LoginDAO extends AbstractDAO{
    private static final String SQL_SELECT_USER_BY_LOGIN = "Select id, name, surname, patronymic, address, role_id, login, password, mobile_phone, blocked  from user where login = ?";

    public LoginDAO() {
    }

    public LoginDAO(Connection connection) {
        super(connection);
    }

    public User getUserByLogin(String login) throws SQLException {
        User user = new User();
        Connection connection = null;
        PreparedStatement st;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            st.setString(1, login);
            ResultSet rs = null;
            rs = st.executeQuery();
            rs.next();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setPatronymic(rs.getString("patronymic"));
            user.setAddress(rs.getString("address"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setMobilePhone(rs.getString("mobile_phone"));
            user.setBlocked(rs.getInt("blocked"));
        } catch (SQLException e) {
            throw e;
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return user;
    }
}
