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

import static by.epam.bokhan.dao.DAOConstant.*;


public class UserDAOImpl extends AbstractDAO implements UserDAO {
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT user.id,library_card.id, user.name, surname, patronymic, address, role.name, login, password,address, mobile_phone, blocked, user.photo  \n" +
            "from user left join role on user.role_id = role.id \n" +
            "left join library_card on user.id = library_card.user_id\n" +
            "where login = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO USER (user.name ,surname, patronymic,role_id, login,password, photo) VALUES " +
            "(?,?,?,?,?,?,?)";
    private static final String SQL_REGISTER_USER = "UPDATE USER SET user.login = ?, user.password = ? where user.id = ?";
    private static final String SQL_INSERT_LIBRARY_CARD = "INSERT INTO library_card (user_id, address, mobile_phone) VALUES " +
            "(?,?,?)";
    private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM USER where user.id = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT user.id,library_card.id, user.name, surname, patronymic, address, role.name, login, mobile_phone, blocked \n" +
            "from user left join role on user.role_id = role.id\n" +
            "left join library_card on user.id = library_card.user_id";
    private static final String SQL_FIND_USER_BY_LIBRARY_CARD = "SELECT user.id,library_card.id, user.name, user.surname, user.patronymic, address, role.name, login, mobile_phone, blocked,user.photo \n" +
            "from library_card left join user on user.id = library_card.user_id\n" +
            "left join role on user.role_id = role.id \n" +
            "where library_card.id = ?";
    private static final String SQL_FIND_USER_BY_SURNAME = "SELECT user.id,library_card.id, user.name, user.surname, user.patronymic, address, role.name, login, mobile_phone, blocked, user.photo\n" +
            "from library_card\n" +
            "left join user on library_card.user_id = user.id\n" +
            "left join role on user.role_id = role.id \n" +
            "where user.surname LIKE ?";
    private static final String SQL_CHECK_IF_LOGIN_EXIST = "SELECT login FROM user where login = ?";
    private static final String SQL_BLOCK_USER_BY_ID = "UPDATE user SET blocked = 1 WHERE user.id = ?";
    private static final String SQL_UNBLOCK_USER = "UPDATE user SET blocked = 0 WHERE user.id = ?";
    private static final String SQL_BLOCK_STATUS = "SELECT blocked FROM user WHERE user.id = ?";
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
            "where orders.library_card_id = ?";

    private static final String SQL_EDIT_USER_INFO = "UPDATE user SET name =?, surname=?, patronymic=?, role_id=?, login =? where user.id = ?";
    private static final String SQL_EDIT_LIBRARY_CARD_INFO = "UPDATE library_card SET address = ?, mobile_phone=? where library_card.id = ?";
    private static final String SQL_GET_PASSWORD = "SELECT password FROM user where id = ?";
    private static final String SQL_SET_NEW_PASSWORD = "UPDATE user SET password = ? where user.id = ?";
    private static final String SQL_SET_NEW_LOGIN = "UPDATE user SET login = ? where user.id = ?";
    private static final String SQL_CHANGE_USER_PHOTO = "UPDATE user SET photo = ? where user.id = ?";

    @Override
    public User getUserByLogin(String login) throws DAOException {
        User user = new User();
        Connection connection = null;
        PreparedStatement getUserByLoginStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getUserByLoginStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            getUserByLoginStatement.setString(1, login);
            ResultSet resultSet = getUserByLoginStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(USER_ID));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setLogin(resultSet.getString(LOGIN));
                user.setPassword(resultSet.getString(PASSWORD));
                Role userRole = Role.valueOf(resultSet.getString(ROLE).toUpperCase());
                user.setRole(userRole);
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE));
                user.setBlocked(resultSet.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                user.setPhoto(resultSet.getString(DAOConstant.USER_PHOTO));

            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not log in. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getUserByLoginStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean registerUser(User user) throws DAOException {
        boolean isUserRegistered = false;
        Connection connection = null;
        PreparedStatement registerUserStatement = null;
        PreparedStatement findUserStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findUserStatement = connection.prepareStatement(SQL_CHECK_IF_LOGIN_EXIST);
            findUserStatement.setString(1, user.getLogin());
            ResultSet foundUser = findUserStatement.executeQuery();
            if (!foundUser.next()) {
                registerUserStatement = connection.prepareStatement(SQL_REGISTER_USER);
                registerUserStatement.setString(1, user.getLogin());
                registerUserStatement.setString(2, user.getPassword());
                registerUserStatement.setInt(3, user.getId());
                int registerUserResult = registerUserStatement.executeUpdate();
                if (registerUserResult > POSITIVE_RESULT_VALUE) {
                    isUserRegistered = true;
                }
            }
            return isUserRegistered;
        } catch (SQLException e) {
            throw new DAOException(String.format("User was not registered: Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(findUserStatement);
            closeStatement(registerUserStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        boolean isUserAdded = false;
        Connection connection = null;
        PreparedStatement findUserStatement = null;
        PreparedStatement insertUserStatement = null;
        PreparedStatement insertLibraryCardStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            findUserStatement = connection.prepareStatement(SQL_CHECK_IF_LOGIN_EXIST);
            findUserStatement.setString(1, user.getLogin());
            ResultSet foundUser = findUserStatement.executeQuery();
            if (!foundUser.next()) {
                insertUserStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                insertUserStatement.setString(1, user.getName());
                insertUserStatement.setString(2, user.getSurname());
                insertUserStatement.setString(3, user.getPatronymic());
                insertUserStatement.setInt(4, user.getRole().ordinal());
                if (user.getLogin() != null && user.getPassword() != null) {
                    insertUserStatement.setString(5, user.getLogin());
                    insertUserStatement.setString(6, user.getPassword());

                }else {
                    insertUserStatement.setString(5, null);
                    insertUserStatement.setString(6, null);
                }
                if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                    insertUserStatement.setString(7, user.getPhoto());
                }else {
                    insertUserStatement.setString(7,null);
                }
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
                    isUserAdded = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isUserAdded;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason : %s", e1.getMessage()), e1);
            }
            throw new DAOException(String.format("Can not add user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(insertLibraryCardStatement);
            closeStatement(findUserStatement);
            closeStatement(insertUserStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = null;
        Statement getAllUsersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getAllUsersStatement = connection.createStatement();
            ResultSet resultSet = getAllUsersStatement.executeQuery(SQL_GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setRole((Role.valueOf(resultSet.getString(ROLE).toUpperCase())));
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
            closeStatement(getAllUsersStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean removeUserById(int id) throws DAOException {
        boolean isUserRemoved = false;
        Connection connection = null;
        PreparedStatement removeUserStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            removeUserStatement = connection.prepareStatement(SQL_REMOVE_USER_BY_ID);
            removeUserStatement.setInt(1, id);
            int res = removeUserStatement.executeUpdate();
            if (res > 0) {
                isUserRemoved = true;
            }
            return isUserRemoved;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not remove user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(removeUserStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<User> findUserByLibraryCard(int libraryCard) throws DAOException {
        List<User> user = new ArrayList<>();
        Connection connection = null;
        PreparedStatement findUserBLibraryCardStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findUserBLibraryCardStatement = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            findUserBLibraryCardStatement.setInt(1, libraryCard);
            ResultSet rs = findUserBLibraryCardStatement.executeQuery();
            while (rs.next()) {
                User foundUser = new User();
                foundUser.setId(rs.getInt(USER_ID));
                foundUser.setName(rs.getString(USER_NAME));
                foundUser.setSurname(rs.getString(USER_SURNAME));
                foundUser.setPatronymic(rs.getString(USER_PATRONYMIC));
                foundUser.setAddress(rs.getString(ADDRESS));
                foundUser.setLogin(rs.getString(LOGIN));
                Role userRole = Role.valueOf(rs.getString(ROLE).toUpperCase());
                foundUser.setRole(userRole);
                foundUser.setMobilePhone(rs.getString(MOBILE_PHONE));
                foundUser.setBlocked(rs.getInt(BLOCK_FIELD));
                foundUser.setLibraryCardNumber(rs.getInt(LIBRARY_CARD));
                foundUser.setPhoto(rs.getString(USER_PHOTO));
                user.add(foundUser);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not find user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(findUserBLibraryCardStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<User> findUserBySurname(String surname) throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement findUserBySurnameStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findUserBySurnameStatement = connection.prepareStatement(SQL_FIND_USER_BY_SURNAME);
            findUserBySurnameStatement.setString(1, "%" + surname + "%");
            ResultSet resultSet = findUserBySurnameStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setLogin(resultSet.getString(LOGIN));
                Role userRole = Role.valueOf(resultSet.getString(ROLE).toUpperCase());
                user.setRole(userRole);
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE));
                user.setBlocked(resultSet.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                user.setPhoto(resultSet.getString(USER_PHOTO));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not find user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(findUserBySurnameStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean blockUser(int userId) throws DAOException {
        boolean isBlocked = false;
        Connection connection = null;
        PreparedStatement userBlockStatement = null;
        PreparedStatement userBlockStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            userBlockStatusStatement = connection.prepareStatement(SQL_BLOCK_STATUS);
            userBlockStatusStatement.setInt(1, userId);
            ResultSet resultSet = userBlockStatusStatement.executeQuery();
            if (resultSet.next()) {
                int status = resultSet.getInt(BLOCK_FIELD);
                if (status != BLOCKED_VALUE) {
                    userBlockStatement = connection.prepareStatement(SQL_BLOCK_USER_BY_ID);
                    userBlockStatement.setInt(1, userId);
                    int blockResult = userBlockStatement.executeUpdate();
                    if (blockResult > 0) {
                        isBlocked = true;
                    }
                }
            }
            return isBlocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not block user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(userBlockStatusStatement);
            closeStatement(userBlockStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean unblockUser(int userId) throws DAOException {
        boolean isUnblocked = false;
        Connection connection = null;
        PreparedStatement unblockUserStatement = null;
        PreparedStatement userBlockStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            userBlockStatusStatement = connection.prepareStatement(SQL_BLOCK_STATUS);
            userBlockStatusStatement.setInt(1, userId);
            ResultSet resultSet = userBlockStatusStatement.executeQuery();
            if (resultSet.next()) {
                int status = resultSet.getInt(BLOCK_FIELD);
                if (status != UNBLOCKED_VALUE) {
                    unblockUserStatement = connection.prepareStatement(SQL_UNBLOCK_USER);
                    unblockUserStatement.setInt(1, userId);
                    int unblockResult = unblockUserStatement.executeUpdate();
                    if (unblockResult > 0) {
                        isUnblocked = true;
                    }
                }
            }
            return isUnblocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not unblock user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(userBlockStatusStatement);
            closeStatement(unblockUserStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<User> getBlockedUsers() throws DAOException {
        List<User> blockedUsers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement getBlockedUsersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getBlockedUsersStatement = connection.prepareStatement(SQL_GET_BLOCKED_USERS);
            ResultSet resultSet = getBlockedUsersStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                Role userRole = Role.valueOf(resultSet.getString(ROLE).toUpperCase());
                user.setRole(userRole);
                user.setLogin(resultSet.getString(LOGIN));
                user.setBlocked(resultSet.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                blockedUsers.add(user);
            }
            return blockedUsers;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get users. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getBlockedUsersStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<User> getNotBlockedUsers() throws DAOException {
        List<User> notBlockedUsers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement getNotBlockedUsersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getNotBlockedUsersStatement = connection.prepareStatement(SQL_GET_NOT_BLOCKED_USERS);
            ResultSet resultSet = getNotBlockedUsersStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                Role userRole = Role.valueOf(resultSet.getString(ROLE).toUpperCase());
                user.setRole(userRole);
                user.setLogin(resultSet.getString(LOGIN));
                user.setBlocked(resultSet.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                notBlockedUsers.add(user);
            }
            return notBlockedUsers;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get users. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getNotBlockedUsersStatement);
            closeConnection(connection);
        }
    }

    @Override
    public User getExplicitUserInfo(int libraryCard) throws DAOException {
        User user = new User();
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement getUserStatement = null;
        PreparedStatement getUserOrdersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getUserStatement = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            getUserStatement.setInt(1, libraryCard);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(USER_ID));
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setLogin(resultSet.getString(LOGIN));
                Role userRole = Role.valueOf(resultSet.getString(ROLE).toUpperCase());
                user.setRole(userRole);
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE));
                user.setBlocked(resultSet.getInt(BLOCK_FIELD));
                user.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                user.setPhoto(resultSet.getString(USER_PHOTO));
                getUserOrdersStatement = connection.prepareStatement(SQL_GET_USERS_ORDER_INFO);
                getUserOrdersStatement.setInt(1, libraryCard);
                ResultSet ordersInfoResult = getUserOrdersStatement.executeQuery();
                while (ordersInfoResult.next()) {
                    Order order = new Order();
                    Book book = new Book();
                    order.setId(ordersInfoResult.getInt(ORDER_ID));
                    book.setId(ordersInfoResult.getInt(ORDERS_BOOK_ID));
                    book.setTitle(ordersInfoResult.getString(BOOK_TITLE));
                    order.setBook(book);
                    Timestamp timestamp = ordersInfoResult.getTimestamp(ORDER_DATE);
                    LocalDate orderDate = timestamp.toLocalDateTime().toLocalDate();
                    order.setOrderDate(orderDate);
                    LocalDate expirationDate = ordersInfoResult.getTimestamp(ORDER_EXPIRATION_DATE).toLocalDateTime().toLocalDate();
                    order.setExpirationDate(expirationDate);
                    Timestamp returnDateTimeStamp = ordersInfoResult.getTimestamp(ORDER_RETURN_DATE);
                    LocalDate returnDate;
                    returnDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                    order.setReturnDate(returnDate);
                    orders.add(order);
                }
                user.setOrders(orders);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getUserStatement);
            closeStatement(getUserOrdersStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean editUser(User user) throws DAOException {
        boolean isUserEdited = false;
        Connection connection = null;
        PreparedStatement editUserInfoStatement = null;
        PreparedStatement editLibraryCardInfoStatement = null;
        PreparedStatement changeUserPassword = null;
        PreparedStatement uploadPhotoStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            editUserInfoStatement = connection.prepareStatement(SQL_EDIT_USER_INFO);
            editUserInfoStatement.setString(1, user.getName());
            editUserInfoStatement.setString(2, user.getSurname());
            editUserInfoStatement.setString(3, user.getPatronymic());
            editUserInfoStatement.setInt(4, user.getRole().ordinal());
            editUserInfoStatement.setString(5, user.getLogin());
            editUserInfoStatement.setInt(6, user.getId());
            int userUpdateResult = editUserInfoStatement.executeUpdate();
            int changePasswordResult;
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                changeUserPassword = connection.prepareStatement(SQL_SET_NEW_PASSWORD);
                changeUserPassword.setString(1, user.getPassword());
                changeUserPassword.setInt(2, user.getId());
                changePasswordResult = changeUserPassword.executeUpdate();
            }else {
                changePasswordResult = POSITIVE_RESULT_VALUE;
            }
            int changePhotoResult;
            if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                uploadPhotoStatement = connection.prepareStatement(SQL_CHANGE_USER_PHOTO);
                uploadPhotoStatement.setString(1, user.getPhoto());
                uploadPhotoStatement.setInt(2,user.getId());
                changePhotoResult = uploadPhotoStatement.executeUpdate();
            }else {
                changePhotoResult = POSITIVE_RESULT_VALUE;
            }
            if (userUpdateResult > 0 && changePasswordResult > 0 && changePhotoResult > 0) {
                editLibraryCardInfoStatement = connection.prepareStatement(SQL_EDIT_LIBRARY_CARD_INFO);
                editLibraryCardInfoStatement.setString(1, user.getAddress());
                editLibraryCardInfoStatement.setString(2, user.getMobilePhone());
                editLibraryCardInfoStatement.setInt(3, user.getLibraryCardNumber());
                int libraryCardUpdateResult = editLibraryCardInfoStatement.executeUpdate();
                if (libraryCardUpdateResult > 0) {
                    isUserEdited = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isUserEdited;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not edit user. Reason : %s", e.getMessage()), e1);
            }
            throw new DAOException(String.format("Can not edit user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(changeUserPassword);
            closeStatement(uploadPhotoStatement);
            closeStatement(editUserInfoStatement);
            closeStatement(editLibraryCardInfoStatement);
            closeConnection(connection);
        }
    }

    @Override
    public String getUserPassword(int libraryCard) throws DAOException {
        String password = null;
        Connection connection = null;
        PreparedStatement getUsersPasswordStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getUsersPasswordStatement = connection.prepareStatement(SQL_GET_PASSWORD);
            getUsersPasswordStatement.setInt(1, libraryCard);
            ResultSet resultSet = getUsersPasswordStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(PASSWORD);
            }
            return password;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get password. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getUsersPasswordStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean changePassword(int libraryCard, String newPassword) throws DAOException {
        boolean isPasswordChanged = false;
        Connection connection = null;
        PreparedStatement setNewPasswordStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            setNewPasswordStatement = connection.prepareStatement(SQL_SET_NEW_PASSWORD);
            setNewPasswordStatement.setString(1, newPassword);
            setNewPasswordStatement.setInt(2, libraryCard);
            int setNewPasswordResult = setNewPasswordStatement.executeUpdate();
            if (setNewPasswordResult > 0) {
                isPasswordChanged = true;
            }
            return isPasswordChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change password. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(setNewPasswordStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean changeLogin(int userId, String login) throws DAOException {
        boolean isLoginChanged = false;
        Connection connection = null;
        PreparedStatement findUserStatement = null;
        PreparedStatement setNewLoginStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findUserStatement = connection.prepareStatement(SQL_CHECK_IF_LOGIN_EXIST);
            findUserStatement.setString(1, login);
            ResultSet foundUser = findUserStatement.executeQuery();
            if (!foundUser.next()) {
                setNewLoginStatement = connection.prepareStatement(SQL_SET_NEW_LOGIN);
                setNewLoginStatement.setString(1, login);
                setNewLoginStatement.setInt(2, userId);
                int setNewLoginResult = setNewLoginStatement.executeUpdate();
                if (setNewLoginResult > 0) {
                    isLoginChanged = true;
                }
            }
            return isLoginChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change login. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(findUserStatement);
            closeStatement(setNewLoginStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean changePhoto(User user) throws DAOException {
        boolean isPhotoChanged = false;
        Connection connection = null;
        PreparedStatement setNewPhotoStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            setNewPhotoStatement = connection.prepareStatement(SQL_CHANGE_USER_PHOTO);
            setNewPhotoStatement.setString(1, user.getPhoto());
            setNewPhotoStatement.setInt(2, user.getId());
            int setNewPhotoResult = setNewPhotoStatement.executeUpdate();
            if (setNewPhotoResult > 0) {
                isPhotoChanged = true;
            }
            return isPhotoChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change photo. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(setNewPhotoStatement);
            closeConnection(connection);
        }
    }
}
