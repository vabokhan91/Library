package by.epam.bokhan.dao;

import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.pool.ConnectionPool;
import by.epam.bokhan.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl extends AbstractDAO implements UserDAO {
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT library_card, name, surname, patronymic, address, role_id, login, password, mobile_phone, blocked  from user where login = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO USER (name ,surname, patronymic, address,role_id,login,password,mobile_phone) VALUES " +
            "(?,?,?,?,?,?,?,?)";
    private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM USER where library_card = ?";
    private static final String SQL_REMOVE_USER_BY_LOGIN = "DELETE FROM USER where login = ?";
    private static final String SQL_FIND_USER_BY_LIBRARY_CARD = "SELECT library_card, name,surname,patronymic,address,role_id,login,mobile_phone, blocked from user where library_card = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT library_card, name,surname,patronymic,address,role_id,login,mobile_phone, blocked from user where login = ?";
    private static final String SQL_CHECK_IF_USER_EXIST = "SELECT login FROM user where login = ?";
    private static final String SQL_BLOCK_USER_BY_CARD = "UPDATE user SET blocked = 1 WHERE library_card = ?";
    private static final String SQL_BLOCK_USER_BY_LOGIN = "UPDATE user SET blocked = 1 WHERE login = ?";
    private static final String SQL_UNBLOCK_USER_BY_CARD = "UPDATE user SET blocked = 0 WHERE library_card = ?";
    private static final String SQL_BLOCK_STATUS_BY_LOGIN = "SELECT blocked FROM user WHERE login = ?";
    private static final String SQL_BLOCK_STATUS_BY_LIBRARY_CARD = "SELECT blocked FROM user WHERE library_card = ?";
    private static final String SQL_GET_BLOCKED_USERS = "Select library_card, name, surname, patronymic, address, role_id, login, mobile_phone, blocked from USER where blocked = 1";
    private static final String SQL_GET_NOT_BLOCKED_USERS = "Select library_card, name, surname, patronymic, address, role_id, login, mobile_phone, blocked from USER where blocked = 0";

    private final String LIBRARY_CARD = "library_card";
    private final String USER_NAME = "name";
    private final String USER_SURNAME = "surname";
    private final String USER_PATRONYMIC = "patronymic";
    private final String ADDRESS = "address";
    private final String LOGIN = "login";
    private final String ROLE_ID = "role_id";
    private final String MOBILE_PHONE = "mobile_phone";
    private final String BLOCK_FIELD = "blocked";
    private final String PASSWORD = "password";

    public User getUserByLogin(String login) throws DAOException {
        User user = new User();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            st.setString(1, login);
            ResultSet rs = null;
            rs = st.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt(LIBRARY_CARD));
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                user.setAddress(rs.getString(ADDRESS));
                user.setLogin(rs.getString(LOGIN));
                user.setPassword(rs.getString(PASSWORD));
                user.setRoleId(rs.getInt(ROLE_ID));
                user.setMobilePhone(rs.getString(MOBILE_PHONE));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
                return user;
            }else {
                throw new DAOException("Can not log in. No user with such login exists");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }

    }


    public boolean addUser(String name, String surname, String patronymic, String address, int roleId, String login, String password, String mobilephone) throws DAOException {
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
            throw new DAOException(e);
        } finally {
            closeStatement(statementFindUser);
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean removeUserById(int id) throws DAOException {
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
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean removeUserByLogin(String login) throws DAOException {
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
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public User findUserByLibraryCard(int libraryCard) throws DAOException {
        User user = new User();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            st.setInt(1, libraryCard);
            ResultSet rs = st.executeQuery();
            rs.next();
            user.setId(rs.getInt(LIBRARY_CARD));
            user.setName(rs.getString(USER_NAME));
            user.setSurname(rs.getString(USER_SURNAME));
            user.setPatronymic(rs.getString(USER_PATRONYMIC));
            user.setAddress(rs.getString(ADDRESS));
            user.setLogin(rs.getString(LOGIN));
            user.setRoleId(rs.getInt(ROLE_ID));
            user.setMobilePhone(rs.getString(MOBILE_PHONE));
            user.setBlocked(rs.getInt(BLOCK_FIELD));
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public User findUserByLogin(String login) throws DAOException {
        User user = new User();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            rs.next();
            user.setId(rs.getInt(LIBRARY_CARD));
            user.setName(rs.getString(USER_NAME));
            user.setSurname(rs.getString(USER_SURNAME));
            user.setPatronymic(rs.getString(USER_PATRONYMIC));
            user.setAddress(rs.getString(ADDRESS));
            user.setLogin(rs.getString(LOGIN));
            user.setRoleId(rs.getInt(ROLE_ID));
            user.setMobilePhone(rs.getString(MOBILE_PHONE));
            user.setBlocked(rs.getInt(BLOCK_FIELD));
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean blockUserByLibraryCard(int librarianCard) throws DAOException {
        boolean isBlocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        PreparedStatement userBlockedStatus = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            userBlockedStatus = connection.prepareStatement(SQL_BLOCK_STATUS_BY_LIBRARY_CARD);
            userBlockedStatus.setInt(1, librarianCard);
            ResultSet resultSet = userBlockedStatus.executeQuery();
            resultSet.next();
            int status = resultSet.getInt(BLOCK_FIELD);
            if (status == 1) {
                throw new SQLException("Already blocked");
            }
            st = connection.prepareStatement(SQL_BLOCK_USER_BY_CARD);
            st.setInt(1, librarianCard);
            int res = st.executeUpdate();
            if (res > 0) {
                isBlocked = true;
            }
            return isBlocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not block user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(userBlockedStatus);
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean blockUserByLogin(String login) throws DAOException {
        boolean isBlocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        PreparedStatement userBlockedStatus = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection = ConnectionPool.getInstance().getConnection();
            userBlockedStatus = connection.prepareStatement(SQL_BLOCK_STATUS_BY_LOGIN);
            userBlockedStatus.setString(1, login);
            ResultSet resultSet = userBlockedStatus.executeQuery();
            resultSet.next();
            int status = resultSet.getInt(BLOCK_FIELD);
            if (status == 1) {
                throw new SQLException("Already blocked");
            }
            st = connection.prepareStatement(SQL_BLOCK_USER_BY_LOGIN);
            st.setString(1, login);
            int res = st.executeUpdate();
            if (res > 0) {
                isBlocked = true;
            }
            return isBlocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not block user. Reason : %s", e.getMessage()), e);

        } finally {
            closeStatement(userBlockedStatus);
            closeStatement(st);
            closeConnection(connection);
        }
    }

    /*public boolean unblockUserByLogin(String login) throws DAOException {
        boolean isUnblocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        PreparedStatement userUnblockedStatus = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            userUnblockedStatus = connection.prepareStatement(SQL_BLOCK_STATUS_BY_LOGIN);
            userUnblockedStatus.setString(1, login);
            ResultSet resultSet = userUnblockedStatus.executeQuery();
            resultSet.next();
            int status = resultSet.getInt(BLOCK_FIELD);
            if (status == 0) {
                throw new SQLException("Already unblocked");
            }
            st = connection.prepareStatement(SQL_UNBLOCK_USER_BY_LOGIN);
            st.setString(1, login);
            int res = st.executeUpdate();
            if (res > 0) {
                isUnblocked = true;
            }
            return isUnblocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not unblock user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(userUnblockedStatus);
            closeStatement(st);
            closeConnection(connection);
        }
    }*/

    public boolean unblockUserByLibraryCard(int libraryCard) throws DAOException {
        boolean isUnblocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        PreparedStatement userUnblockedStatus = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            userUnblockedStatus = connection.prepareStatement(SQL_BLOCK_STATUS_BY_LIBRARY_CARD);
            userUnblockedStatus.setInt(1, libraryCard);
            ResultSet resultSet = userUnblockedStatus.executeQuery();
            resultSet.next();
            int status = resultSet.getInt(BLOCK_FIELD);
            if (status == 0) {
                throw new SQLException("Already unblocked");
            }
            st = connection.prepareStatement(SQL_UNBLOCK_USER_BY_CARD);
            st.setInt(1, libraryCard);
            int res = st.executeUpdate();
            if (res > 0) {
                isUnblocked = true;
            }
            return isUnblocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not unblock user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(userUnblockedStatus);
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public ArrayList<User> getBlockedUsers() throws DAOException {
        ArrayList<User> blockedUsers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_BLOCKED_USERS);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(LIBRARY_CARD));
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                user.setAddress(rs.getString(ADDRESS));
                user.setRoleId(rs.getInt(ROLE_ID));
                user.setLogin(rs.getString(LOGIN));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
                user.setMobilePhone(rs.getString(MOBILE_PHONE));
                blockedUsers.add(user);
            }
            return blockedUsers;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get users. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public ArrayList<User> getNotBlockedUsers() throws DAOException {
        ArrayList<User> notBlockedUsers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_NOT_BLOCKED_USERS);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(LIBRARY_CARD));
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                user.setAddress(rs.getString(ADDRESS));
                user.setRoleId(rs.getInt(ROLE_ID));
                user.setLogin(rs.getString(LOGIN));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
                user.setMobilePhone(rs.getString(MOBILE_PHONE));
                notBlockedUsers.add(user);
            }
            return notBlockedUsers;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get users. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

}
