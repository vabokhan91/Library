package by.epam.bokhan.dao;

import by.epam.bokhan.pool.ConnectionPool;
import by.epam.bokhan.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vbokh on 16.07.2017.
 */
public class UserDAO extends AbstractDAO {
    private static final String SQL_INSERT_USER = "INSERT INTO USER (name ,surname, patronymic, address,role_id,login,password,mobile_phone) VALUES " +
            "(?,?,?,?,?,?,?,?)";
    private static final String SQL_REMOVE_USER = "DELETE FROM USER where id = ?";
    private static final String SQL_FIND_USER = "SELECT id, name,surname,patronymic,address,role_id,login,mobile_phone, blocked from user where id = ?";

    public boolean addUser(String name, String surname, String patronymic, String address, int roleId, String login, String password, String mobilephone) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_INSERT_USER);
            st.setString(1, name);
            st.setString(2, surname);
            st.setString(3, patronymic);
            st.setString(4, address);
            st.setInt(5, roleId);
            st.setString(6, login);
            st.setString(7, password);
            st.setString(8, mobilephone);
            int res = st.executeUpdate();
            if (res > 0) {
                result = true;
            }
            return result;

        } catch (SQLException e) {
            throw e;
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean removeUser(int id) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_REMOVE_USER);
            st.setInt(1, id);
            int res = st.executeUpdate();
            if (res > 0) {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }

    }

    public User findUserById(int id) throws SQLException{
        User user = new User();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setPatronymic(rs.getString("patronymic"));
            user.setAddress(rs.getString("address"));
            user.setLogin(rs.getString("login"));
            user.setRoleId(rs.getInt("role_id"));
            user.setMobilePhone(rs.getString("mobile_phone"));
            user.setBlocked(rs.getInt("blocked"));
            return user;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }
}
