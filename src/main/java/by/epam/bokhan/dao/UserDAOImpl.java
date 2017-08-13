package by.epam.bokhan.dao;

import by.epam.bokhan.entity.Book;
import by.epam.bokhan.entity.Order;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.pool.ConnectionPool;
import by.epam.bokhan.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends AbstractDAO implements UserDAO {
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT user.id, user.name, surname, patronymic, address, role.name, login, password,address, mobile_phone, blocked  \n" +
            "from user left join role on user.role_id = role.id \n" +
            "left join library_card on user.id = library_card.user_id\n" +
            "where login = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO USER (user.name ,surname, patronymic,role_id,login,password) VALUES " +
            "(?,?,?,?,?,?)";
    private static final String SQL_INSERT_LIBRARY_CARD = "INSERT INTO library_card (user_id, address, mobile_phone) VALUES " +
            "(?,?,?)";
    private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM USER where user.id = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT user.id,library_card.id, user.name, surname, patronymic, address, role.name, login, mobile_phone, blocked \n" +
            "from user left join role on user.role_id = role.id\n" +
            "left join library_card on user.id = library_card.user_id";
    private static final String SQL_FIND_USER_BY_LIBRARY_CARD = "SELECT user.id,library_card.id, user.name, user.surname, user.patronymic, address, role.name, login, mobile_phone, blocked \n" +
            "from library_card left join user on user.id = library_card.user_id\n" +
            "left join role on user.role_id = role.id \n" +
            "where library_card.id = ?";
    private static final String SQL_FIND_USER_BY_SURNAME = "SELECT user.id,library_card.id, user.name, user.surname, user.patronymic, address, role.name, login, mobile_phone, blocked\n" +
            "from library_card\n" +
            "left join user on library_card.user_id = user.id\n" +
            "left join role on user.role_id = role.id \n" +
            "where user.surname LIKE ?";
    private static final String SQL_CHECK_IF_USER_EXIST = "SELECT login FROM user where login = ?";
    private static final String SQL_BLOCK_USER_BY_ID = "UPDATE user SET blocked = 1 WHERE user.id = ?";
    private static final String SQL_UNBLOCK_USER_BY_CARD = "UPDATE user SET blocked = 0 WHERE library_card = ?";
    private static final String SQL_BLOCK_STATUS_BY_LIBRARY_CARD = "SELECT blocked FROM user WHERE library_card = ?";
    private static final String SQL_GET_BLOCKED_USERS = "Select user.id, library_card.id, user.name, surname, patronymic, role.name, login, blocked\n" +
            "from USER left join role on user.role_id = role.id\n" +
            "left join library_card on user.id = library_card.user_id \n" +
            "where blocked = 1";
    private static final String SQL_GET_NOT_BLOCKED_USERS = "Select user.id, library_card.id, user.name, surname, patronymic, role.name, login, blocked \n" +
            "from USER \n" +
            "left join role on user.role_id = role.id \n" +
            "left join library_card on library_card.user_id = user.id\n" +
            "where blocked = 0";
    private static final String SQL_GET_USERS_ORDER_INFO = "Select orders.id, orders.book_id, book.title, orders.order_date, orders.expiration_date, orders.return_date \n" +
            "from orders\n" +
            "left join book on orders.book_id = book.id\n" +
            "where orders.user_id = ?";

    private static final String SQL_EDIT_USER_INFO = "UPDATE user SET library_card = ?, name =?, surname=?, patronymic=?,address =?, role_id=?, login =?, mobile_phone=? where library_card = ?";
    private static final String SQL_GET_PASSWORD = "SELECT password FROM user where library_card = ?";
    private static final String SQL_SET_NEW_PASSWORD = "UPDATE user SET password = ? where user.library_card = ?";
    private static final String SQL_SET_NEW_LOGIN = "UPDATE user SET login = ? where user.library_card = ?";



    private final String USER_NAME = "user.name";
    private final String USER_SURNAME = "surname";
    private final String USER_PATRONYMIC = "patronymic";
    private final String ADDRESS = "address";
    private final String LOGIN = "login";
    private final String ROLE_ID = "role_id";
    private final String MOBILE_PHONE = "mobile_phone";
    private final String BLOCK_FIELD = "blocked";
    private final String PASSWORD = "password";
    protected final String USER_ID = "user.id";
    private final String LIBRARY_CARD = "library_card.id";
    private final String ROLE = "role.name";

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
                user.setId(rs.getInt(USER_ID));
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

            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addUser(User user, int roleId) throws DAOException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statementFindUser = null;
        PreparedStatement insertUserStatement = null;
        PreparedStatement insertLibraryCardStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statementFindUser = connection.prepareStatement(SQL_CHECK_IF_USER_EXIST);
            statementFindUser.setString(1, user.getLogin());
            ResultSet foundUser = statementFindUser.executeQuery();
            if (!foundUser.next()) {
                insertUserStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                insertUserStatement.setString(1, user.getName());
                insertUserStatement.setString(2, user.getSurname());
                insertUserStatement.setString(3, user.getPatronymic());
                insertUserStatement.setInt(4, roleId);
                insertUserStatement.setString(5, user.getLogin());
                insertUserStatement.setString(6, user.getPassword());

                int insertUserRes = insertUserStatement.executeUpdate();
                int insertLibraryCardResult = 0;
                ResultSet resultSet = insertUserStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    insertLibraryCardStatement = connection.prepareStatement(SQL_INSERT_LIBRARY_CARD);
                    insertLibraryCardStatement.setInt(1, userId);
                    insertLibraryCardStatement.setString(2, user.getAddress());
                    insertLibraryCardStatement.setString(3, user.getMobilePhone());
                    insertLibraryCardResult = insertLibraryCardStatement.executeUpdate();
                }


                if (insertUserRes > 0 && insertLibraryCardResult > 0) {
                    result = true;
                    connection.commit();
                }else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not add user. Reason : %s", e1.getMessage()), e1);
            }
            throw new DAOException(String.format("Can not add user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(insertLibraryCardStatement);
            closeStatement(statementFindUser);
            closeStatement(insertUserStatement);
            closeConnection(connection);
        }
    }

    public ArrayList<User> getAllUsers() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setRole((Role.valueOf(resultSet.getString("role.name").toUpperCase())));
                user.setLogin(resultSet.getString(LOGIN));
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE));
                user.setBlocked(resultSet.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get users. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(statement);
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

    public List<User> findUserByLibraryCard(int libraryCard) throws DAOException {
        List<User> user = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            st.setInt(1, libraryCard);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User foundUser = new User();
                foundUser.setId(rs.getInt(USER_ID));
                foundUser.setName(rs.getString(USER_NAME));
                foundUser.setSurname(rs.getString(USER_SURNAME));
                foundUser.setPatronymic(rs.getString(USER_PATRONYMIC));
                foundUser.setAddress(rs.getString(ADDRESS));
                foundUser.setLogin(rs.getString(LOGIN));
                Role userRole = Role.valueOf(rs.getString("role.name").toUpperCase());
                foundUser.setRole(userRole);
                foundUser.setMobilePhone(rs.getString(MOBILE_PHONE));
                foundUser.setBlocked(rs.getInt(BLOCK_FIELD));
                foundUser.setLibraryCardNumber(rs.getInt(LIBRARY_CARD));
                user.add(foundUser);
            }

            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }
    @Override
    public List<User> findUserBySurname(String surname) throws DAOException {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_SURNAME);
            st.setString(1, "%" +surname + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(USER_ID));
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                user.setAddress(rs.getString(ADDRESS));
                user.setLogin(rs.getString(LOGIN));
                Role userRole = Role.valueOf(rs.getString(ROLE).toUpperCase());
                user.setRole(userRole);
                user.setMobilePhone(rs.getString(MOBILE_PHONE));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(rs.getInt(LIBRARY_CARD));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean blockUserById(int userId) throws DAOException {
        boolean isBlocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            st = connection.prepareStatement(SQL_BLOCK_USER_BY_ID);
            st.setInt(1, userId);
            int res = st.executeUpdate();
            if (res > 0) {
                isBlocked = true;
            }
            return isBlocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not block user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean unblockUser(int libraryCard) throws DAOException {
        boolean isUnblocked = false;
        Connection connection = null;
        PreparedStatement st = null;
        PreparedStatement userUnblockedStatus = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
           /* userUnblockedStatus = connection.prepareStatement(SQL_BLOCK_STATUS_BY_LIBRARY_CARD);
            userUnblockedStatus.setInt(1, libraryCard);
            ResultSet resultSet = userUnblockedStatus.executeQuery();
            resultSet.next();
            int status = resultSet.getInt(BLOCK_FIELD);
            if (status == 0) {
                throw new SQLException("Already unblocked");
            }*/
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
//            closeStatement(userUnblockedStatus);
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
                user.setId(rs.getInt(USER_ID));
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                Role userRole = Role.valueOf(rs.getString("role.name").toUpperCase());
                user.setRole(userRole);
                user.setLogin(rs.getString(LOGIN));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(rs.getInt(LIBRARY_CARD));
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

    @Override
    public List<User> getNotBlockedUsers() throws DAOException {
        List<User> notBlockedUsers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_NOT_BLOCKED_USERS);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(USER_ID));
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                Role userRole = Role.valueOf(rs.getString(ROLE).toUpperCase());
                user.setRole(userRole);
                user.setLogin(rs.getString(LOGIN));
                user.setBlocked(rs.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(rs.getInt(LIBRARY_CARD));
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

    public User getExplicitUserInfo(int libraryCard) throws DAOException {
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
            resultSet.next();
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
                returnDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setReturnDate(returnDate);
                orders.add(order);
            }
            user.setOrders(orders);
            return user;

        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeStatement(ordersInfo);
            closeConnection(connection);
        }
    }

    public boolean editUser(int libraryCard, String name, String surname, String patronymic, String address, int role, String login, String mobile_phone) throws DAOException {
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
            st.setString(8, mobile_phone);
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

    @Override
    public String getPassword(int libraryCard) throws DAOException {
        String password = "";
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_PASSWORD);
            st.setInt(1, libraryCard);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");
            }
            return password;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get password. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean changePassword(int libraryCard, String newPassword) throws DAOException {
        boolean isPasswordChanged = false;
        Connection connection = null;
        PreparedStatement setNewPassword = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            setNewPassword = connection.prepareStatement(SQL_SET_NEW_PASSWORD);
            setNewPassword.setString(1, newPassword);
            setNewPassword.setInt(2, libraryCard);
            int res = setNewPassword.executeUpdate();
            if (res > 0) {
                isPasswordChanged = true;
            }
            return isPasswordChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change password. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(setNewPassword);
            closeConnection(connection);
        }
    }

    @Override
    public boolean changeLogin(int userId, String login) throws DAOException {
        boolean isLoginChanged = false;
        Connection connection = null;
        PreparedStatement setNewLoginStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            setNewLoginStatement = connection.prepareStatement(SQL_SET_NEW_LOGIN);
            setNewLoginStatement.setString(1, login);
            setNewLoginStatement.setInt(2, userId);
            int res = setNewLoginStatement.executeUpdate();
            if (res > 0) {
                isLoginChanged = true;
            }
            return isLoginChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change login. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(setNewLoginStatement);
            closeConnection(connection);
        }
    }
}
