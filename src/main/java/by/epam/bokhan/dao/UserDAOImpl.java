package by.epam.bokhan.dao;

import by.epam.bokhan.entity.Book;
import by.epam.bokhan.entity.Order;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.pool.ConnectionPool;
import by.epam.bokhan.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAOImpl extends AbstractDAO implements UserDAO {
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT library_card, user.name, surname, patronymic, address, role.name, login, password, mobile_phone, blocked  from user left join role on user.role_id = role.id where login = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO USER (user.name ,surname, patronymic, address,role_id,login,password,mobile_phone) VALUES " +
            "(?,?,?,?,?,?,?,?)";
    private static final String SQL_REMOVE_USER_BY_LIBRARY_CARD = "DELETE FROM USER where library_card = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT library_card, user.name, surname, patronymic, address, role.name, login, mobile_phone, blocked from user left join role on user.role_id = role.id";
    private static final String SQL_FIND_USER_BY_LIBRARY_CARD = "SELECT library_card, user.name,surname,patronymic,address,role.name,login,mobile_phone, blocked from user left join role on user.role_id = role.id where library_card = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT library_card, user.name,surname,patronymic,address,role.name,login,mobile_phone, blocked from user left join role on user.role_id = role.id where login = ?";
    private static final String SQL_CHECK_IF_USER_EXIST = "SELECT login FROM user where login = ?";
    private static final String SQL_BLOCK_USER_BY_CARD = "UPDATE user SET blocked = 1 WHERE library_card = ?";
    private static final String SQL_UNBLOCK_USER_BY_CARD = "UPDATE user SET blocked = 0 WHERE library_card = ?";
    private static final String SQL_BLOCK_STATUS_BY_LIBRARY_CARD = "SELECT blocked FROM user WHERE library_card = ?";
    private static final String SQL_GET_BLOCKED_USERS = "Select library_card, user.name, surname, patronymic, role.name, login, blocked from USER left join role on user.role_id = role.id where blocked = 1";
    private static final String SQL_GET_NOT_BLOCKED_USERS = "Select library_card, user.name, surname, patronymic, role.name, login, blocked from USER left join role on user.role_id = role.id where blocked = 0";
    private static final String SQL_GET_USERS_ORDER_INFO = "Select orders.id, orders.book_id, book.title, orders.order_date, orders.expiration_date, orders.return_date \n" +
            "from orders\n" +
            "left join book on orders.book_id = book.id\n" +
            "where orders.user_id = ?";

    private static final String SQL_EDIT_USER_INFO = "UPDATE user SET library_card = ?, name =?, surname=?, patronymic=?,address =?, role_id=?, login =?, mobile_phone=? where library_card = ?";

    private final String LIBRARY_CARD = "library_card";
    private final String USER_NAME = "user.name";
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
            ResultSet rs;
            rs = st.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt(LIBRARY_CARD));
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                user.setAddress(rs.getString(ADDRESS));
                user.setLogin(rs.getString(LOGIN));
                user.setPassword(rs.getString(PASSWORD));
                Role userRole = Role.valueOf(rs.getString("role.name").toUpperCase());
                user.setRole(userRole);
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

    public ArrayList<User> getAllUsers() throws DAOException{
        ArrayList<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(LIBRARY_CARD));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setRole((Role.valueOf(resultSet.getString("role.name").toUpperCase())));
                user.setLogin(resultSet.getString(LOGIN));
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE));
                user.setBlocked(resultSet.getInt(BLOCK_FIELD));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get users. Reason : %s", e.getMessage()), e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    public boolean removeUserByLibraryCard(int id) throws DAOException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_REMOVE_USER_BY_LIBRARY_CARD);
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
            Role userRole = Role.valueOf(rs.getString("role.name").toUpperCase());
            user.setRole(userRole);
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
            Role userRole = Role.valueOf(rs.getString("role.name").toUpperCase());
            user.setRole(userRole);
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
                Role userRole = Role.valueOf(rs.getString("role.name").toUpperCase());
                user.setRole(userRole);
                user.setLogin(rs.getString(LOGIN));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
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
                Role userRole = Role.valueOf(rs.getString("role.name").toUpperCase());
                user.setRole(userRole);
                user.setLogin(rs.getString(LOGIN));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
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

    public User getExplicitUserInfo(int libraryCard) throws DAOException{
        User user = new User();
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        PreparedStatement ordersInfo = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            st.setInt(1, libraryCard);
            ResultSet resultSet = st.executeQuery();
            if(resultSet.next()){
            user.setId(resultSet.getInt(LIBRARY_CARD));
            user.setName(resultSet.getString(USER_NAME));
            user.setSurname(resultSet.getString(USER_SURNAME));
            user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
            user.setAddress(resultSet.getString(ADDRESS));
            user.setLogin(resultSet.getString(LOGIN));
            Role userRole = Role.valueOf(resultSet.getString("role.name").toUpperCase());
            user.setRole(userRole);
            user.setMobilePhone(resultSet.getString(MOBILE_PHONE));
            user.setBlocked(resultSet.getInt(BLOCK_FIELD));
            }else {
                throw new DAOException(String.format("Can not get user. No such user exists"));
            }
            ordersInfo = connection.prepareStatement(SQL_GET_USERS_ORDER_INFO);
            ordersInfo.setInt(1, libraryCard);
            ResultSet ordersInfoResult = ordersInfo.executeQuery();
            while (ordersInfoResult.next()) {
                Order order = new Order();
                Book book = new Book();
                order.setId(ordersInfoResult.getInt("orders.id"));
                book.setId(ordersInfoResult.getInt("orders.book_id"));
                book.setTitle(ordersInfoResult.getString("book.title"));
                order.setBook(book);
                Timestamp timestamp = ordersInfoResult.getTimestamp("orders.order_date");
                LocalDate orderDate = timestamp.toLocalDateTime().toLocalDate();
                order.setOrderDate(orderDate);

                LocalDate expirationDate = ordersInfoResult.getTimestamp("orders.expiration_date").toLocalDateTime().toLocalDate();
                order.setExpirationDate(expirationDate);
                Timestamp returnDateTimeStamp = ordersInfoResult.getTimestamp("orders.return_date");
                LocalDate returnDate;
                returnDate = returnDateTimeStamp !=null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() :null;
                order.setReturnDate(returnDate);
                orders.add(order);
            }
            user.setOrders(orders);
            return user;

        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get user. Reason : %s", e.getMessage()), e);
        }finally {
            closeStatement(st);
            closeStatement(ordersInfo);
            closeConnection(connection);
        }
    }

    public boolean editUser(int libraryCard, String name, String surname, String patronymic, String address, int role, String login, String mobile_phone) throws DAOException{
        boolean isUserEdited = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_EDIT_USER_INFO);
            st.setInt(1, libraryCard);
            st.setString(2, name);
            st.setString(3, surname);
            st.setString(4, patronymic);
            st.setString(5, address);
            st.setInt(6, role);
            st.setString(7, login);
            st.setString(8,mobile_phone);
            st.setInt(9, libraryCard);
            int res = st.executeUpdate();
            if (res > 0) {
                isUserEdited = true;
            }
            return isUserEdited;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not edit user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

}
