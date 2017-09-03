package by.epam.bokhan.dao.impl;

import by.epam.bokhan.dao.UserDAO;
import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.pool.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.epam.bokhan.dao.impl.DAOConstant.*;


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

    private static final String SQL_EDIT_USER_INFO = "UPDATE user SET name =?, surname=?, patronymic=?, role_id=? where user.id = ?";
    private static final String SQL_EDIT_LIBRARY_CARD_INFO = "UPDATE library_card SET address = ?, mobile_phone=? where library_card.id = ?";
    private static final String SQL_GET_PASSWORD = "SELECT password FROM user where id = ?";
    private static final String SQL_SET_NEW_PASSWORD = "UPDATE user SET password = ? where user.id = ?";
    private static final String SQL_SET_NEW_LOGIN = "UPDATE user SET login = ? where user.id = ?";
    private static final String SQL_CHANGE_USER_PHOTO = "UPDATE user SET photo = ? where user.id = ?";
    private static final String SQL_GET_USER_ORDERS = "Select orders.id, book.id, book.title, book.isbn, user.id,library_card.id, user.name, user.surname, user.patronymic, library_card.mobile_phone, orders.order_date, orders.expiration_date, orders.return_date \n" +
            "from orders \n" +
            "right join library_card on orders.library_card_id = library_card.id\n" +
            "left join user on user.id = library_card.user_id\n" +
            "left join book on book.id = orders.book_id\n" +
            "where library_card.id = ?";
    private static final String SQL_GET_USER_ONLINE_ORDERS = "Select online_orders.id, book.id, book.title, book.isbn, user.id, user.name, user.surname, user.patronymic, library_card.id,library_card.mobile_phone, online_orders.order_date, online_orders.expiration_date, online_orders.order_execution_date,online_orders.order_status\n" +
            "from online_orders\n" +
            "right join library_card on online_orders.library_card_id = library_card.id\n" +
            "right join user\n" +
            "on library_card.user_id = user.id\n" +
            "left join book on book.id = online_orders.book_id\n" +
            "where library_card.id =?";

    /**
     * Gets user by login
     * @param login login of the user
     * @return found user
     * @throws DAOException
     * */
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

    /**
     * Registeres new user
     * @param user new user to register
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean registerUser(User user) throws DAOException {
        boolean isUserRegistered = false;
        Connection connection = null;
        PreparedStatement getUserInfoStatement = null;
        PreparedStatement findLoginStatement = null;
        PreparedStatement registerUserStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getUserInfoStatement = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            ResultSet resultSet = getUserInfoStatement.executeQuery();
            if (resultSet.next() && resultSet.getString(LOGIN) == null) {
                String userName = resultSet.getString(USER_NAME);
                String userSurname = resultSet.getString(USER_SURNAME);
                if (user.getName().equalsIgnoreCase(userName) && user.getSurname().equalsIgnoreCase(userSurname)) {
                    int userId = resultSet.getInt(USER_ID);
                    findLoginStatement = connection.prepareStatement(SQL_CHECK_IF_LOGIN_EXIST);
                    findLoginStatement.setString(1, user.getLogin());
                    ResultSet foundLogin = findLoginStatement.executeQuery();
                    if (!foundLogin.next()) {
                        registerUserStatement = connection.prepareStatement(SQL_REGISTER_USER);
                        registerUserStatement.setString(1, user.getLogin());
                        registerUserStatement.setString(2, user.getPassword());
                        registerUserStatement.setInt(3, userId);
                        int registerUserResult = registerUserStatement.executeUpdate();
                        isUserRegistered = registerUserResult > 0;
                    }
                }

            }
            return isUserRegistered;
        } catch (SQLException e) {
            throw new DAOException(String.format("User was not registered: Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getUserInfoStatement);
            closeStatement(findLoginStatement);
            closeStatement(registerUserStatement);
            closeConnection(connection);
        }
    }

    /**
     * Adds new user
     * @param user new user to register
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean addUser(User user) throws DAOException {
        boolean isUserAdded = false;
        Connection connection = null;
        PreparedStatement findLoginStatement = null;
        PreparedStatement insertUserStatement = null;
        PreparedStatement insertLibraryCardStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            insertUserStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getName());
            insertUserStatement.setString(2, user.getSurname());
            insertUserStatement.setString(3, user.getPatronymic());
            insertUserStatement.setInt(4, user.getRole().ordinal());
            if (user.getLogin() == null && user.getPassword() == null) {
                insertUserStatement.setString(5, null);
                insertUserStatement.setString(6, null);
            } else {
                findLoginStatement = connection.prepareStatement(SQL_CHECK_IF_LOGIN_EXIST);
                findLoginStatement.setString(1, user.getLogin());
                ResultSet foundLogin = findLoginStatement.executeQuery();
                if (!foundLogin.next()) {
                    insertUserStatement.setString(5, user.getLogin());
                    insertUserStatement.setString(6, user.getPassword());
                } else {
                    return isUserAdded;
                }
            }
            if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                insertUserStatement.setString(7, user.getPhoto());
            } else {
                insertUserStatement.setString(7, null);
            }
            int insertUserRes = insertUserStatement.executeUpdate();
            if (insertUserRes > 0) {
                int insertLibraryCardResult;
                ResultSet generatedKeys = insertUserStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    insertLibraryCardStatement = connection.prepareStatement(SQL_INSERT_LIBRARY_CARD);
                    insertLibraryCardStatement.setInt(1, userId);
                    insertLibraryCardStatement.setString(2, user.getAddress());
                    insertLibraryCardStatement.setString(3, user.getMobilePhone());
                    insertLibraryCardResult = insertLibraryCardStatement.executeUpdate();
                    if (insertLibraryCardResult > 0) {
                        isUserAdded = true;
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
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
                throw new DAOException(String.format("Can not add user. Reason : %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason : %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(insertLibraryCardStatement);
            closeStatement(findLoginStatement);
            closeStatement(insertUserStatement);
            closeConnection(connection);
        }
    }

    /**
     * Gets all users from database
     * @return List of users
     * @throws DAOException
     * */
    @Override
    public List<User> getAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement getAllUsersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getAllUsersStatement = connection.prepareStatement(SQL_GET_ALL_USERS);
            ResultSet resultSet = getAllUsersStatement.executeQuery();
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

    /**
     * Removes user by id
     * @param userId user id
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean removeUserById(int userId) throws DAOException {
        boolean isUserRemoved;
        Connection connection = null;
        PreparedStatement removeUserStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            removeUserStatement = connection.prepareStatement(SQL_REMOVE_USER_BY_ID);
            removeUserStatement.setInt(1, userId);
            int userRemoveResult = removeUserStatement.executeUpdate();
            isUserRemoved = userRemoveResult > 0;
            return isUserRemoved;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not remove user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(removeUserStatement);
            closeConnection(connection);
        }
    }

    /**
     * Gets user by library card
     * @param libraryCard library card of user
     * @return List of found users
     * @throws DAOException
     * */
    @Override
    public List<User> getUserByLibraryCard(int libraryCard) throws DAOException {
        List<User> user = new ArrayList<>();
        Connection connection = null;
        PreparedStatement findUserBLibraryCardStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findUserBLibraryCardStatement = connection.prepareStatement(SQL_FIND_USER_BY_LIBRARY_CARD);
            findUserBLibraryCardStatement.setInt(1, libraryCard);
            ResultSet resultSet = findUserBLibraryCardStatement.executeQuery();
            while (resultSet.next()) {
                User foundUser = new User();
                foundUser.setId(resultSet.getInt(USER_ID));
                foundUser.setName(resultSet.getString(USER_NAME));
                foundUser.setSurname(resultSet.getString(USER_SURNAME));
                foundUser.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                foundUser.setAddress(resultSet.getString(ADDRESS));
                foundUser.setLogin(resultSet.getString(LOGIN));
                Role userRole = Role.valueOf(resultSet.getString(ROLE).toUpperCase());
                foundUser.setRole(userRole);
                foundUser.setMobilePhone(resultSet.getString(MOBILE_PHONE));
                foundUser.setBlocked(resultSet.getInt(BLOCK_FIELD));
                foundUser.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                foundUser.setPhoto(resultSet.getString(USER_PHOTO));
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

    /**
     * Gets user by surname
     * @param surname user surname
     * @return List of found users
     * @throws DAOException
     * */
    @Override
    public List<User> getUserBySurname(String surname) throws DAOException {
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

    /**
     * Blocks user
     * @param userId user id
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean blockUser(int userId) throws DAOException {
        boolean isUserBlocked = false;
        Connection connection = null;
        PreparedStatement userBlockStatement = null;
        PreparedStatement userBlockStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            userBlockStatusStatement = connection.prepareStatement(SQL_BLOCK_STATUS);
            userBlockStatusStatement.setInt(1, userId);
            ResultSet resultSet = userBlockStatusStatement.executeQuery();
            if (resultSet.next()) {
                boolean isNotBlocked = resultSet.getInt(BLOCK_FIELD) == 0;
                if (isNotBlocked) {
                    userBlockStatement = connection.prepareStatement(SQL_BLOCK_USER_BY_ID);
                    userBlockStatement.setInt(1, userId);
                    int blockResult = userBlockStatement.executeUpdate();
                    isUserBlocked = blockResult > 0;
                }
            }
            return isUserBlocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not block user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(userBlockStatusStatement);
            closeStatement(userBlockStatement);
            closeConnection(connection);
        }
    }

    /**
     * Unblocks user
     * @param userId user id
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean unblockUser(int userId) throws DAOException {
        boolean isUserUnblocked = false;
        Connection connection = null;
        PreparedStatement unblockUserStatement = null;
        PreparedStatement userBlockStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            userBlockStatusStatement = connection.prepareStatement(SQL_BLOCK_STATUS);
            userBlockStatusStatement.setInt(1, userId);
            ResultSet resultSet = userBlockStatusStatement.executeQuery();
            if (resultSet.next()) {
                boolean isBlocked = resultSet.getInt(BLOCK_FIELD) > 0;
                if (isBlocked) {
                    unblockUserStatement = connection.prepareStatement(SQL_UNBLOCK_USER);
                    unblockUserStatement.setInt(1, userId);
                    int unblockResult = unblockUserStatement.executeUpdate();
                    isUserUnblocked = unblockResult > 0;
                }
            }
            return isUserUnblocked;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not unblock user. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(userBlockStatusStatement);
            closeStatement(unblockUserStatement);
            closeConnection(connection);
        }
    }

    /**
     * Gets blocked users
     * @return List of users
     * @throws DAOException
     * */
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

    /**
     * Gets not blocked users
     * @return List of users
     * @throws DAOException
     * */
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

    /**
     * Gets explicit user info( user information and information about his orders)
     * @param libraryCard user library card
     * @return found user
     * @throws DAOException
     * */
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
                    Timestamp orderDateTimestamp = ordersInfoResult.getTimestamp(ORDER_DATE);
                    LocalDate orderDate = orderDateTimestamp != null ? orderDateTimestamp.toLocalDateTime().toLocalDate() : null;
                    order.setOrderDate(orderDate);
                    Timestamp expirationDateTimeStamp = ordersInfoResult.getTimestamp(ORDER_EXPIRATION_DATE);
                    LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                    order.setExpirationDate(expirationDate);
                    Timestamp returnDateTimeStamp = ordersInfoResult.getTimestamp(ORDER_RETURN_DATE);
                    LocalDate returnDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                    order.setReturnDate(returnDate);
                    orders.add(order);
                }
            }
            user.setOrders(orders);
            return user;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get user and its orders. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getUserStatement);
            closeStatement(getUserOrdersStatement);
            closeConnection(connection);
        }
    }

    /**
     * Edits user. If user information edited,
     * then updated information about library card, user photo, login and password
     * @param user user to edit
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean editUser(User user) throws DAOException {
        boolean isUserEdited = false;
        Connection connection = null;
        PreparedStatement editUserInfoStatement = null;
        PreparedStatement editLibraryCardInfoStatement = null;
        PreparedStatement changeUserLoginStatement = null;
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
            editUserInfoStatement.setInt(5, user.getId());
            int userUpdateResult = editUserInfoStatement.executeUpdate();
            boolean userUpdated = userUpdateResult > 0;
            if (userUpdated) {
                editLibraryCardInfoStatement = connection.prepareStatement(SQL_EDIT_LIBRARY_CARD_INFO);
                editLibraryCardInfoStatement.setString(1, user.getAddress());
                editLibraryCardInfoStatement.setString(2, user.getMobilePhone());
                editLibraryCardInfoStatement.setInt(3, user.getLibraryCardNumber());
                int libraryCardUpdateResult = editLibraryCardInfoStatement.executeUpdate();
                if (libraryCardUpdateResult > 0) {
                    boolean isLoginChanged;
                    boolean isPasswordChanged;
                    boolean isPhotoChanged;
                    if (user.getLogin() != null && !user.getLogin().isEmpty()) {
                        changeUserLoginStatement = connection.prepareStatement(SQL_SET_NEW_LOGIN);
                        changeUserLoginStatement.setString(1, user.getLogin());
                        changeUserLoginStatement.setInt(2, user.getId());
                        int changeLoginResult = changeUserLoginStatement.executeUpdate();
                        isLoginChanged = changeLoginResult > 0;
                    } else {
                        isLoginChanged = true;
                    }
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        changeUserPassword = connection.prepareStatement(SQL_SET_NEW_PASSWORD);
                        changeUserPassword.setString(1, user.getPassword());
                        changeUserPassword.setInt(2, user.getId());
                        int changePasswordResult = changeUserPassword.executeUpdate();
                        isPasswordChanged = changePasswordResult > 0;
                    } else {
                        isPasswordChanged = true;
                    }
                    if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                        uploadPhotoStatement = connection.prepareStatement(SQL_CHANGE_USER_PHOTO);
                        uploadPhotoStatement.setString(1, user.getPhoto());
                        uploadPhotoStatement.setInt(2, user.getId());
                        int changePhotoResult = uploadPhotoStatement.executeUpdate();
                        isPhotoChanged = changePhotoResult > 0;
                    } else {
                        isPhotoChanged = true;
                    }
                    if (isLoginChanged && isPasswordChanged && isPhotoChanged) {
                        isUserEdited = true;
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
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
            closeStatement(changeUserLoginStatement);
            closeStatement(editUserInfoStatement);
            closeStatement(editLibraryCardInfoStatement);
            closeConnection(connection);
        }
    }

    /**
     * Changes user password
     * @param libraryCard user library card
     * @param oldPassword old password of the user
     * @param newPassword new password of the user
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean changePassword(int libraryCard,String oldPassword, String newPassword) throws DAOException {
        boolean isPasswordChanged = false;
        Connection connection = null;
        PreparedStatement getOldPasswordStatement = null;
        PreparedStatement setNewPasswordStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getOldPasswordStatement = connection.prepareStatement(SQL_GET_PASSWORD);
            getOldPasswordStatement.setInt(1, libraryCard);
            ResultSet resultSet = getOldPasswordStatement.executeQuery();
            if (resultSet.next()) {
               String oldPasswordFromDB = resultSet.getString(PASSWORD);
                if (oldPassword.equals(oldPasswordFromDB)) {
                    connection = ConnectionPool.getInstance().getConnection();
                    setNewPasswordStatement = connection.prepareStatement(SQL_SET_NEW_PASSWORD);
                    setNewPasswordStatement.setString(1, newPassword);
                    setNewPasswordStatement.setInt(2, libraryCard);
                    int setNewPasswordResult = setNewPasswordStatement.executeUpdate();
                    isPasswordChanged = setNewPasswordResult > 0;
                }
            }
            return isPasswordChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change password. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getOldPasswordStatement);
            closeStatement(setNewPasswordStatement);
            closeConnection(connection);
        }
    }

    /**
     * Changes user login. First checks, if login do not exist, then changes it
     * @param userId user id
     * @param login new login of the user
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean changeLogin(int userId, String login) throws DAOException {
        boolean isLoginChanged = false;
        Connection connection = null;
        PreparedStatement findLoginStatement = null;
        PreparedStatement setNewLoginStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findLoginStatement = connection.prepareStatement(SQL_CHECK_IF_LOGIN_EXIST);
            findLoginStatement.setString(1, login);
            ResultSet foundLogin = findLoginStatement.executeQuery();
            if (!foundLogin.next()) {
                setNewLoginStatement = connection.prepareStatement(SQL_SET_NEW_LOGIN);
                setNewLoginStatement.setString(1, login);
                setNewLoginStatement.setInt(2, userId);
                int setNewLoginResult = setNewLoginStatement.executeUpdate();
                isLoginChanged = setNewLoginResult > 0;
            }
            return isLoginChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change login. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(findLoginStatement);
            closeStatement(setNewLoginStatement);
            closeConnection(connection);
        }
    }

    /**
     * Changes user photo
     * @param user user, whose photo to be changed
     * @return boolean, depending on the operation result
     * @throws DAOException
     * */
    @Override
    public boolean changePhoto(User user) throws DAOException {
        boolean isPhotoChanged;
        Connection connection = null;
        PreparedStatement setNewPhotoStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            setNewPhotoStatement = connection.prepareStatement(SQL_CHANGE_USER_PHOTO);
            setNewPhotoStatement.setString(1, user.getPhoto());
            setNewPhotoStatement.setInt(2, user.getId());
            int setNewPhotoResult = setNewPhotoStatement.executeUpdate();
            isPhotoChanged = setNewPhotoResult > 0;
            return isPhotoChanged;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not change photo. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(setNewPhotoStatement);
            closeConnection(connection);
        }
    }

    /**
     * Gets user and his orders
     * @param libraryCard user library card
     * @return user with his orders
     * @throws DAOException
     * */
    @Override
    public User getUserWithOrders(int libraryCard) throws DAOException {
        User user = new User();
        List<Order> userOrders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement getUserOrdersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getUserOrdersStatement = connection.prepareStatement(SQL_GET_USER_ORDERS);
            getUserOrdersStatement.setInt(1, libraryCard);
            ResultSet resultSet = getUserOrdersStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(USER_ID);
                if (user.getId() != userId) {
                    int userLibraryCard = resultSet.getInt(LIBRARY_CARD);
                    String userName = resultSet.getString(USER_NAME);
                    String userSurname = resultSet.getString(USER_SURNAME);
                    String userPatronymic = resultSet.getString(USER_PATRONYMIC);
                    String userMobilePhone = resultSet.getString(MOBILE_PHONE);
                    user.setId(userId);
                    user.setLibraryCardNumber(userLibraryCard);
                    user.setName(userName);
                    user.setSurname(userSurname);
                    user.setPatronymic(userPatronymic);
                    user.setMobilePhone(userMobilePhone);
                } else {
                    int orderId = resultSet.getInt(ORDER_ID);
                    if (orderId != 0) {
                        Order order = new Order();
                        Book book = new Book();
                        order.setId(orderId);
                        int bookId = resultSet.getInt(BOOK_ID);
                        String bookTitle = resultSet.getString(BOOK_TITLE);
                        String bookIsbn = resultSet.getString(BOOK_ISBN);
                        book.setId(bookId);
                        book.setTitle(bookTitle);
                        book.setIsbn(bookIsbn);
                        order.setBook(book);
                        Timestamp lastOrderTimeStamp = resultSet.getTimestamp(ORDER_DATE);
                        LocalDate lastOrderDate = lastOrderTimeStamp != null ? lastOrderTimeStamp.toLocalDateTime().toLocalDate() : null;
                        order.setOrderDate(lastOrderDate);
                        Timestamp expirationDateTimeStamp = resultSet.getTimestamp(ORDER_EXPIRATION_DATE);
                        LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                        order.setExpirationDate(expirationDate);
                        Timestamp returnDateTimeStamp = resultSet.getTimestamp(ORDER_RETURN_DATE);
                        LocalDate returnDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                        order.setReturnDate(returnDate);
                        userOrders.add(order);
                    }
                }
            }
            user.setOrders(userOrders);
            return user;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get user orders. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(getUserOrdersStatement);
            closeConnection(connection);
        }
    }

    /**
     * Gets user and his online orders
     * @param libraryCard user library card
     * @return user with his online orders
     * @throws DAOException
     * */
    @Override
    public User getUserOnlineOrders(int libraryCard) throws DAOException {
        User user = new User();
        List<Order> userOrders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement getUserOnlineOrdersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getUserOnlineOrdersStatement = connection.prepareStatement(SQL_GET_USER_ONLINE_ORDERS);
            getUserOnlineOrdersStatement.setInt(1, libraryCard);
            ResultSet resultSet = getUserOnlineOrdersStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(USER_ID);
                if (user.getId() != userId) {
                    int userLibraryCard = resultSet.getInt(LIBRARY_CARD);
                    String userName = resultSet.getString(USER_NAME);
                    String userSurname = resultSet.getString(USER_SURNAME);
                    String userPatronymic = resultSet.getString(USER_PATRONYMIC);
                    String mobilePhone = resultSet.getString(MOBILE_PHONE);
                    user.setId(userId);
                    user.setLibraryCardNumber(userLibraryCard);
                    user.setName(userName);
                    user.setSurname(userSurname);
                    user.setPatronymic(userPatronymic);
                    user.setMobilePhone(mobilePhone);
                } else {
                    int ordersId = resultSet.getInt(ONLINE_ORDER_ID);
                    if (ordersId != 0) {
                        OnlineOrder order = new OnlineOrder();
                        Book book = new Book();
                        int bookId = resultSet.getInt(BOOK_ID);
                        String bookTitle = resultSet.getString(BOOK_TITLE);
                        String bookIsbn = resultSet.getString(BOOK_ISBN);
                        book.setId(bookId);
                        book.setTitle(bookTitle);
                        book.setIsbn(bookIsbn);
                        String orderStatus = resultSet.getString(ONLINE_ORDER_STATUS);
                        order.setOrderStatus(OrderStatus.valueOf(orderStatus.toUpperCase()));
                        order.setBook(book);
                        order.setId(ordersId);
                        Timestamp orderDateTimeStamp = resultSet.getTimestamp(ONLINE_ORDER_DATE_OF_ORDER);
                        LocalDate OrderDate = orderDateTimeStamp != null ? orderDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                        order.setOrderDate(OrderDate);
                        Timestamp expirationDateTimeStamp = resultSet.getTimestamp(ONLINE_ORDER_EXPIRATION_DATE);
                        LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                        order.setExpirationDate(expirationDate);
                        Timestamp executionDateTimeStamp = resultSet.getTimestamp(ONLINE_ORDER_EXECUTION_DATE);
                        LocalDate executionDate = executionDateTimeStamp != null ? executionDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                        order.setReturnDate(executionDate);
                        userOrders.add(order);
                    }
                }
            }
            user.setOrders(userOrders);
            return user;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get users' online orders. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(getUserOnlineOrdersStatement);
            closeConnection(connection);
        }
    }
}
