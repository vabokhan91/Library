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
    private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM USER where library_card = ?";
    private static final String SQL_REMOVE_USER_BY_LOGIN = "DELETE FROM USER where login = ?";
    private static final String SQL_FIND_USER_BY_LIBRARY_CARD = "SELECT library_card, name,surname,patronymic,address,role_id,login,mobile_phone, blocked from user where library_card = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT library_card, name,surname,patronymic,address,role_id,login,mobile_phone, blocked from user where login = ?";
    private static final String SQL_CHECK_IF_USER_EXIST = "SELECT login FROM user where login = ?";
    private static final String SQL_BLOCK_USER_BY_CARD = "UPDATE user SET blocked = 1 WHERE library_card = ?";
    private static final String SQL_BLOCK_USER_BY_LOGIN = "UPDATE user SET blocked = 1 WHERE login = ?";

    public boolean addUser(String name, String surname, String patronymic, String address, int roleId, String login, String password, String mobilephone) throws SQLException {

        boolean result = false;
        Connection connection = null;
        PreparedStatement statementFindUser = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statementFindUser = connection.prepareStatement(SQL_CHECK_IF_USER_EXIST);
            statementFindUser.setString(1, login);
            ResultSet foundUser = statementFindUser.executeQuery();
            if (!foundUser.next()) {
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
            } else {
                result = false;
            }
            return result;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeStatement(statementFindUser);
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean removeUserById(int id) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_REMOVE_USER_BY_ID);
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

    public boolean removeUserByLogin(String login) throws SQLException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_REMOVE_USER_BY_LOGIN);
            st.setString(1, login);
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

    public User findUserByLibraryCard(int libraryCard) throws SQLException{
        User user = new User();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            st.setInt(1, libraryCard);
            ResultSet rs = st.executeQuery();
            rs.next();
            user.setId(rs.getInt("library_card"));
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

    public User findUserByLogin(String login) throws SQLException{
        User user = new User();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            rs.next();
            user.setId(rs.getInt("library_card"));
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

    public boolean blockUserByLibraryCard(int librarianCard) throws SQLException{
        boolean isBlocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_BLOCK_USER_BY_CARD);
            st.setInt(1, librarianCard);
            int res = st.executeUpdate();
            if (res > 0) {
                isBlocked = true;
            }
            return isBlocked;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean blockUserByLogin(String login) throws SQLException{
        boolean isBlocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_BLOCK_USER_BY_LOGIN);
            st.setString(1, login);
            int res = st.executeUpdate();
            if (res > 0) {
                isBlocked = true;
            }
            return isBlocked;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }
}
