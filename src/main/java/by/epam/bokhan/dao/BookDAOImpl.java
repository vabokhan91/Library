package by.epam.bokhan.dao;

import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.pool.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static by.epam.bokhan.dao.DAOConstant.*;


public class BookDAOImpl extends AbstractDAO implements BookDAO {
    private static final String SQL_GET_ALL_BOOKS = "SELECT book.id, book.title, book.pages,book.isbn, book.year, book.description, book.location, book.image, publisher.id, publisher.name,\n" +
            "genres.genre_name,genres.genre_id, authors.author_name, authors.author_surname, authors.author_patronymic\n" +
            "from book\n" +
            "left join publisher on book.publisher_id = publisher.id\n" +
            "left join (select book_id,genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            "left join (select book_id, author_id, author.name as author_name, author.surname as author_surname,author.patronymic as author_patronymic \n" +
            "\tfrom book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = authors.book_id";
    private static final String SQL_FIND_BOOK_BY_ID = "SELECT book.id, book.title,book.pages,book.isbn, book.location,book.description,book.image, genres.genre_id, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year,publisher.id, publisher.name\n" +
            " from book \n" +
            " left join publisher on book.publisher_id = publisher.id\n" +
            " left join (select book_author.book_id as b,author.name, author.surname, author.patronymic from book_author left join author on book_author.author_id = author.id) as authors\n" +
            " on book.id = b\n" +
            "left join (select book_id,genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            " where book.id = ?";

   /* private static final String SQL_GET_BOOK_FOR_EDITING = "SELECT book.id, book.title,book.pages,book.isbn, book.description, book.location,publisher.id, publisher.name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year,\n" +
            "genres.genre_id, genre_name\n" +
            "from book\n" +
            "left join (select book_author.book_id as b,author.name , author.surname , author.patronymic  from book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = b \n" +
            "left join (select book_genre.book_id as b_id, genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres\n" +
            "on book.id = b_id\n" +
            "left join publisher\n" +
            "on book.publisher_id = publisher.id\n" +
            "where book.id = ?";*/

    private static final String SQL_FIND_BOOK_BY_TITLE = "SELECT book.id, book.title,book.pages,book.isbn, book.location,book.description,book.image, genres.genre_id, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year,publisher.id, publisher.name" +
            " from book \n" +
            " left join publisher on book.publisher_id = publisher.id\n" +
            " left join (select book_author.book_id as b,author.name, author.surname, author.patronymic from book_author left join author on book_author.author_id = author.id) as authors\n" +
            " on book.id = b\n" +
            "left join (select book_id,genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            "where book.title LIKE ?";


   /* private static final String SQL_GET_EXPLICIT_BOOK_INFO = "SELECT book.id, book.title,book.pages,book.isbn, book.location,book.description,book.image, genres.genre_id, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year,publisher.id, publisher.name\n" +
            " from book \n" +
            " left join publisher on book.publisher_id = publisher.id\n" +
            " left join (select book_author.book_id as b,author.name, author.surname, author.patronymic from book_author left join author on book_author.author_id = author.id) as authors\n" +
            " on book.id = b\n" +
            "left join (select book_id,genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            " where book.id = ?";*/

    private static final String SQL_GET_BOOKS_LAST_ORDER = "SELECT orders.book_id,orders.library_card_id,orders.order_date, orders.expiration_date, orders.return_date,\n" +
            "user.id, user.name, user.surname, user.patronymic,library_card.id, library_card.mobile_phone\n" +
            "from orders\n" +
            "left join library_card on library_card.id = orders.library_card_id\n" +
            "left join user\n" +
            "on library_card.user_id = user.id\n" +
            "where orders.order_date = (select max(order_date) from orders where orders.book_id = ?) and orders.book_id = ?";

    private static final String SQL_GET_ALL_GENRES = "Select * from genre";
    private static final String SQL_EDIT_BOOK = "Update book set title = ?, pages = ?, isbn = ?, year = ?,publisher_id = ?, description = ? where id = ?";
    private static final String SQL_CHANGE_BOOK_IMAGE = "Update book set image = ? where id = ?";
    private static final String SQL_DELETE_BOOKS_GENRE = "DELETE FROM book_genre WHERE book_genre.book_id = ?";
    private static final String SQL_DELETE_BOOKS_AUTHOR = "DELETE FROM book_author WHERE book_author.book_id = ?";
    private static final String SQL_DELETE_GENRE = "DELETE FROM genre WHERE genre.id = ?";
    private static final String SQL_DELETE_AUTHOR = "DELETE FROM author WHERE author.id = ?";
    private static final String SQL_DELETE_PUBLISHER = "DELETE FROM publisher WHERE publisher.id = ?";
    private static final String SQL_ADD_AUTHOR = "INSERT INTO author (author.name, author.surname, author.patronymic, author.date_of_birth) VALUES (?,?,?,?)";
    private static final String SQL_ADD_PUBLISHER = "INSERT INTO publisher (publisher.name) VALUES (?)";
    private static final String SQL_GET_ALL_AUTHORS = "Select author.id, author.name as author_name, author.surname as author_surname, author.patronymic as author_patronymic, author.date_of_birth from author";
    private static final String SQL_GET_ALL_PUBLISHERS = "Select * from publisher";
    private static final String SQL_ADD_BOOK = "INSERT INTO book (book.title, book.pages,book.isbn, book.year,book.publisher_id,book.description,book.location, book.image) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_ADD_BOOK_GENRE = "INSERT INTO book_genre (book_id,genre_id) VALUES (?,?)";
    private static final String SQL_ADD_GENRE = "INSERT INTO genre (genre.name) VALUES (?)";
    private static final String SQL_ADD_BOOK_AUTHOR = "INSERT INTO book_author (book_id,author_id) VALUES (?,?)";
    private static final String SQL_IS_BOOK_IN_STORAGE = "SELECT * from book where book.id = ? and book.location = 'storage'";
    private static final String SQL_DELETE_BOOK = "DELETE FROM book where book.id = ?";
    private static final String SQL_ADD_ORDER_ON_READING_ROOM = "INSERT INTO ORDERS (orders.library_card_id, orders.book_id, orders.order_date, orders.expiration_date, orders.return_date, orders.librarian_id) VALUES (?,?,now(),addtime(now(), '1 0:0:0.0'), null,?)";
    private static final String SQL_ADD_ORDER_ON_SUBSCRIPTION = "INSERT INTO ORDERS (orders.library_card_id, orders.book_id, orders.order_date, orders.expiration_date, orders.return_date, orders.librarian_id) VALUES (?,?,now(),addtime(now(), '30 0:0:0.0'), null,?)";
    private static final String SQL_BOOK_LOCATION_SUBSCRIPTION = "Update book set location = 'subscription' where book.id = ?";
    private static final String SQL_BOOK_LOCATION_READING_ROOM = "Update book set location = 'reading_room' where book.id = ?";
    private static final String SQL_BOOK_LOCATION_STORAGE = "Update book set location = 'storage' where book.id = ?";
    private static final String SQL_BOOK_LOCATION_ONLINE_ORDER = "Update book set location = 'online_order' where book.id = ?";

    private static final String SQL_RETURN_BOOK = "UPDATE orders set orders.return_date = now() where orders.id = ?";
    private static final String SQL_ADD_ONLINE_ORDER = "INSERT INTO online_orders (online_orders.library_card_id, online_orders.book_id, online_orders.order_date, online_orders.expiration_date, online_orders.order_execution_date, online_orders.order_status) \n" +
            "VALUES (?,?,now(),addtime(now(), '3 0:0:0.0'), null,'booked')";
    private static final String SQL_CANCEL_ONLINE_ORDER = "Update online_orders set online_orders.order_execution_date = now(), order_status = 'canceled' where online_orders.id = ?";
    private static final String SQL_ONLINE_ORDER_STATUS = "SELECT order_status from online_orders where online_orders.id = ?";
    private static final String SQL_EXECUTE_ONLINE_ORDER = "Update online_orders SET online_orders.order_execution_date = now(), online_orders.order_status = 'executed' where online_orders.id = ?";
    private static final String SQL_GET_BOOK_BY_GENRE = "SELECT book.id, book.title,book.pages,book.isbn, book.location, book.description,book.image, genres.genre_id, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year,publisher.id, publisher.name\n" +
            "from book \n" +
            "left join publisher on book.publisher_id = publisher.id\n" +
            "left join (select book_author.book_id as b,author.name, author.surname, author.patronymic from book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = b\n" +
            "left join (select book_id,genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            "where genre_name LIKE ?";
    private static final String SQL_GET_RANDOM_BOOK_ID = "SELECT id FROM book\n" +
            "ORDER BY RAND()\n" +
            "LIMIT ?";

    @Override
    public List<Book> getAllBooks() throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Connection connection = null;
        PreparedStatement getAllBooksStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getAllBooksStatement = connection.prepareStatement(SQL_GET_ALL_BOOKS);
            ResultSet resultSet = getAllBooksStatement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt(BOOK_ID);
                int lastBookId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastBookId == bookId) {
                    Genre genre = new Genre();
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre.setId(genreId);
                    genre.setName(genreName);
                    if (!books.getLast().getGenre().contains(genre)) {
                        books.getLast().addGenre(genre);
                    }
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    if (!books.getLast().getAuthors().contains(author)) {
                        books.getLast().addAuthor(author);
                    }
                } else {
                    Book book = new Book();
                    book.setId(bookId);
                    book.setTitle(resultSet.getString(BOOK_TITLE));
                    book.setPages(resultSet.getInt(BOOK_PAGES));
                    book.setIsbn(resultSet.getString(BOOK_ISBN));
                    book.setYear(resultSet.getInt(BOOK_YEAR));
                    book.setDescription(resultSet.getString(BOOK_DESCRIPTION));
                    book.setLocation(Location.valueOf(resultSet.getString(BOOK_LOCATION).toUpperCase()));
                    Publisher publisher = new Publisher();
                    Integer publisherId = resultSet.getInt(PUBLISHER_ID);
                    publisher.setId(publisherId);
                    String publisherName = resultSet.getString(PUBLISHER_NAME);
                    publisher.setName(publisherName);
                    book.setPublisher(publisher);
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    Genre genre = new Genre();
                    genre.setId(genreId);
                    genre.setName(genreName);
                    book.addGenre(genre);
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    book.addAuthor(author);
                    Blob imageBlob = resultSet.getBlob(BOOK_IMAGE);
                    if (imageBlob != null) {
                        String image = new String(imageBlob.getBytes(1, (int) imageBlob.length()));
                        book.setImage(image);
                    }
                    books.add(book);
                }
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get books. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getAllBooksStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Book> findBookById(int bookId) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Book book;
        Connection connection = null;
        PreparedStatement findBookByIdStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findBookByIdStatement = connection.prepareStatement(SQL_FIND_BOOK_BY_ID);
            findBookByIdStatement.setInt(1, bookId);
            ResultSet resultSet = findBookByIdStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre;
                int bookFromDBId = resultSet.getInt(BOOK_ID);
                int lastBookId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastBookId == bookFromDBId) {
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre = new Genre();
                    genre.setId(genreId);
                    genre.setName(genreName);
                    if (!books.getLast().getGenre().contains(genre)) {
                        books.getLast().addGenre(genre);
                    }
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book lastBookFromList = books.getLast();
                    if (!lastBookFromList.getAuthors().contains(author)) {
                        lastBookFromList.addAuthor(author);
                    }
                } else {
                    book = new Book();
                    book.setId(bookFromDBId);
                    book.setTitle(resultSet.getString(BOOK_TITLE));
                    book.setPages(resultSet.getInt(BOOK_PAGES));
                    book.setIsbn(resultSet.getString(BOOK_ISBN));
                    book.setYear(resultSet.getInt(BOOK_YEAR));
                    book.setLocation(Location.valueOf(resultSet.getString(BOOK_LOCATION).toUpperCase()));
                    Publisher publisher = new Publisher();
                    Integer publisherId = resultSet.getInt(PUBLISHER_ID);
                    String publisherName = resultSet.getString(PUBLISHER_NAME);
                    publisher.setId(publisherId);
                    publisher.setName(publisherName);
                    book.setPublisher(publisher);
                    genre = new Genre();
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre.setId(genreId);
                    genre.setName(genreName);
                    book.addGenre(genre);
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    book.addAuthor(author);
                    Blob imageBlob = resultSet.getBlob(BOOK_IMAGE);
                    if (imageBlob != null) {
                        String image = new String(imageBlob.getBytes(1, (int) imageBlob.length()));
                        book.setImage(image);
                    }
                    books.add(book);
                }
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not find book. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(findBookByIdStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Book> findBookByTitle(String title) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Book book;
        Connection connection = null;
        PreparedStatement findBookByTitleStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            findBookByTitleStatement = connection.prepareStatement(SQL_FIND_BOOK_BY_TITLE);
            findBookByTitleStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = findBookByTitleStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre;
                int bookFromDBId = resultSet.getInt(BOOK_ID);
                int lastBookId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastBookId == bookFromDBId) {
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre = new Genre();
                    genre.setId(genreId);
                    genre.setName(genreName);
                    if (!books.getLast().getGenre().contains(genre)) {
                        books.getLast().addGenre(genre);
                    }
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book lastBookFromList = books.getLast();
                    if (!lastBookFromList.getAuthors().contains(author)) {
                        lastBookFromList.addAuthor(author);
                    }
                } else {
                    book = new Book();
                    book.setId(bookFromDBId);
                    book.setTitle(resultSet.getString(BOOK_TITLE));
                    book.setPages(resultSet.getInt(BOOK_PAGES));
                    book.setIsbn(resultSet.getString(BOOK_ISBN));
                    book.setYear(resultSet.getInt(BOOK_YEAR));
                    book.setLocation(Location.valueOf(resultSet.getString(BOOK_LOCATION).toUpperCase()));
                    Publisher publisher = new Publisher();
                    Integer publisherId = resultSet.getInt(PUBLISHER_ID);
                    String publisherName = resultSet.getString(PUBLISHER_NAME);
                    publisher.setId(publisherId);
                    publisher.setName(publisherName);
                    book.setPublisher(publisher);
                    genre = new Genre();
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre.setId(genreId);
                    genre.setName(genreName);
                    book.addGenre(genre);
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    book.addAuthor(author);
                    Blob imageBlob = resultSet.getBlob(BOOK_IMAGE);
                    if (imageBlob != null) {
                        String image = new String(imageBlob.getBytes(1, (int) imageBlob.length()));
                        book.setImage(image);
                    }
                    books.add(book);
                }
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not find book. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(findBookByTitleStatement);
            closeConnection(connection);
        }
    }

    @Override
    public Book getExplicitBookInfo(int bookId) throws DAOException {
        Book book = new Book();
        Connection connection = null;
        PreparedStatement explicitBookInfoStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            explicitBookInfoStatement = connection.prepareStatement(SQL_FIND_BOOK_BY_ID);
            explicitBookInfoStatement.setInt(1, bookId);
            ResultSet resultSet = explicitBookInfoStatement.executeQuery();
            while (resultSet.next()) {
                int bookFromDBId = resultSet.getInt(BOOK_ID);
                int foundBookId = book.getId();
                if (foundBookId == 0) {
                    book.setId(bookFromDBId);
                    book.setTitle(resultSet.getString(BOOK_TITLE));
                    book.setPages(resultSet.getInt(BOOK_PAGES));
                    book.setYear(resultSet.getInt(BOOK_YEAR));
                    book.setLocation(Location.valueOf(resultSet.getString(BOOK_LOCATION).toUpperCase()));
                    Genre genre = new Genre();
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre.setId(genreId);
                    genre.setName(genreName);
                    book.addGenre(genre);
                    book.setIsbn(resultSet.getString(BOOK_ISBN));
                    book.setDescription(resultSet.getString(BOOK_DESCRIPTION));
                    Publisher publisher = new Publisher();
                    Integer publisherId = resultSet.getInt(PUBLISHER_ID);
                    String publisherName = resultSet.getString(PUBLISHER_NAME);
                    publisher.setId(publisherId);
                    publisher.setName(publisherName);
                    book.setPublisher(publisher);
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    book.addAuthor(author);
                    Blob imageBlob = resultSet.getBlob(BOOK_IMAGE);
                    if (imageBlob != null) {
                        String image = new String(imageBlob.getBytes(1, (int) imageBlob.length()));
                        book.setImage(image);
                    }
                } else {
                    Genre genre = new Genre();
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre.setId(genreId);
                    genre.setName(genreName);
                    if (!book.getGenre().contains(genre)) {
                        book.addGenre(genre);
                    }
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    if (!book.getAuthors().contains(author)) {
                        book.addAuthor(author);
                    }
                }
            }
            return book;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get explicit book info. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(explicitBookInfoStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Order> getBooksLastOrder(int bookId) throws DAOException {
        List<Order> orders = new LinkedList<>();
        Connection connection = null;
        PreparedStatement booksLastOrderStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            booksLastOrderStatement = connection.prepareStatement(SQL_GET_BOOKS_LAST_ORDER);
            booksLastOrderStatement.setInt(1, bookId);
            booksLastOrderStatement.setInt(2, bookId);
            ResultSet resultSet = booksLastOrderStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                Timestamp orderDateTimeStamp = resultSet.getTimestamp(ORDER_DATE);
                LocalDate orderDate = orderDateTimeStamp != null ? orderDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setOrderDate(orderDate);
                Timestamp expirationDateTimeStamp = resultSet.getTimestamp(ORDER_EXPIRATION_DATE);
                LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setExpirationDate(expirationDate);
                Timestamp returnDateTimeStamp = resultSet.getTimestamp(ORDER_RETURN_DATE);
                LocalDate returnDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setReturnDate(returnDate);
                User user = new User();
                user.setName(resultSet.getString(USER_NAME));
                user.setSurname(resultSet.getString(USER_SURNAME));
                user.setPatronymic(resultSet.getString(USER_PATRONYMIC));
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE));
                user.setId(resultSet.getInt(USER_ID));
                user.setLibraryCardNumber(resultSet.getInt(LIBRARY_CARD));
                order.setUser(user);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get order. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(booksLastOrderStatement);
            closeConnection(connection);
        }
    }
/*
    @Override
    public Book getBookForEditing(int bookId) throws DAOException {
        Book foundBook = new Book();
        Connection connection = null;
        PreparedStatement getBookForEditingStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getBookForEditingStatement = connection.prepareStatement(SQL_GET_BOOK_FOR_EDITING);
            getBookForEditingStatement.setInt(1, bookId);
            ResultSet resultSet = getBookForEditingStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre;
                int bookFromDBId = resultSet.getInt(BOOK_ID);
                int foundBookId = foundBook.getId();
                if (foundBookId == 0) {
                    foundBook.setId(bookFromDBId);
                    foundBook.setTitle(resultSet.getString(BOOK_TITLE));
                    foundBook.setPages(resultSet.getInt(BOOK_PAGES));
                    foundBook.setYear(resultSet.getInt(BOOK_YEAR));
                    genre = new Genre();
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre.setId(genreId);
                    genre.setName(genreName);
                    foundBook.addGenre(genre);
                    foundBook.setIsbn(resultSet.getString(BOOK_ISBN));
                    foundBook.setDescription(resultSet.getString(BOOK_DESCRIPTION));
                    Publisher publisher = new Publisher();
                    Integer publisherId = resultSet.getInt(PUBLISHER_ID);
                    String publisherName = resultSet.getString(PUBLISHER_NAME);
                    publisher.setId(publisherId);
                    publisher.setName(publisherName);
                    foundBook.setPublisher(publisher);
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    foundBook.addAuthor(author);
                } else {
                    genre = new Genre();
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    genre.setId(genreId);
                    genre.setName(genreName);
                    if (!foundBook.getGenre().contains(genre)) {
                        foundBook.addGenre(genre);
                    }
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    if (!foundBook.getAuthors().contains(author)) {
                        foundBook.addAuthor(author);
                    }
                }
            }
            return foundBook;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get book for editing. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getBookForEditingStatement);
            closeConnection(connection);
        }
    }*/

    @Override
    public List<Genre> getAllGenres() throws DAOException {
        List<Genre> genres = new LinkedList<>();
        Connection connection = null;
        Statement getAllGenresStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getAllGenresStatement = connection.createStatement();
            ResultSet resultSet = getAllGenresStatement.executeQuery(SQL_GET_ALL_GENRES);
            while (resultSet.next()) {
                Genre genre = new Genre();
                String genreName = resultSet.getString(GENRE_NAME);
                int genreId = resultSet.getInt(GENRE_ID);
                genre.setId(genreId);
                genre.setName(genreName);
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get genres. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(getAllGenresStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean editBook(Book book) throws DAOException {
        boolean isFullBookEdited = false;
        Connection connection = null;
        PreparedStatement editBookStatement = null;
        PreparedStatement deleteGenreStatement = null;
        PreparedStatement changeBookImageStatement = null;
        PreparedStatement addGenreStatement = null;
        PreparedStatement deleteAuthorStatement = null;
        PreparedStatement addAuthorStatement = null;
        boolean isBookEdited;
        boolean isBookImageEdited;
        boolean isGenreDeleted;
        boolean isGenreEdited = false;
        boolean isAuthorDeleted;
        boolean isAuthorEdited = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            editBookStatement = connection.prepareStatement(SQL_EDIT_BOOK);
            editBookStatement.setString(1, book.getTitle());
            editBookStatement.setInt(2, book.getPages());
            editBookStatement.setString(3, book.getIsbn());
            editBookStatement.setInt(4, book.getYear());
            editBookStatement.setInt(5, book.getPublisher().getId());
            editBookStatement.setString(6, book.getDescription());
            editBookStatement.setInt(7, book.getId());
            int editBookResult = editBookStatement.executeUpdate();
            isBookEdited = editBookResult > 0;
            if (isBookEdited) {
                if (book.getImage() != null && !book.getImage().isEmpty()) {
                    changeBookImageStatement = connection.prepareStatement(SQL_CHANGE_BOOK_IMAGE);
                    changeBookImageStatement.setString(1, book.getImage());
                    changeBookImageStatement.setInt(2, book.getId());
                    int changeBookImageResult = changeBookImageStatement.executeUpdate();
                    isBookImageEdited = changeBookImageResult > 0;
                } else {
                    isBookImageEdited = true;
                }
                deleteGenreStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRE);
                deleteGenreStatement.setInt(1, book.getId());
                int deleteGenreResult = deleteGenreStatement.executeUpdate();
                isGenreDeleted = deleteGenreResult > 0;
                if (isGenreDeleted) {
                    addGenreStatement = connection.prepareStatement(SQL_ADD_BOOK_GENRE);
                    int addGenreResult = 0;
                    List<Genre> genres = book.getGenre();
                    for (Genre genre : genres) {
                        addGenreStatement.setInt(1, book.getId());
                        addGenreStatement.setInt(2, genre.getId());
                        addGenreResult = addGenreStatement.executeUpdate();
                        if (addGenreResult == 0) {
                            break;
                        }
                    }
                    isGenreEdited = addGenreResult > 0;
                }
                deleteAuthorStatement = connection.prepareStatement(SQL_DELETE_BOOKS_AUTHOR);
                deleteAuthorStatement.setInt(1, book.getId());
                int deleteAuthorResult = deleteAuthorStatement.executeUpdate();
                isAuthorDeleted = deleteAuthorResult > 0;
                if (isAuthorDeleted) {
                    addAuthorStatement = connection.prepareStatement(SQL_ADD_BOOK_AUTHOR);
                    int addAuthorResult = 0;
                    List<Author> authors = book.getAuthors();
                    for (Author author : authors) {
                        addAuthorStatement.setInt(1, book.getId());
                        addAuthorStatement.setInt(2, author.getId());
                        addAuthorResult = addAuthorStatement.executeUpdate();
                        if (addAuthorResult == 0) {
                            break;
                        }
                    }
                    isAuthorEdited = addAuthorResult > 0;
                }
                if (isAuthorEdited && isGenreEdited && isBookImageEdited) {
                    isFullBookEdited = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isFullBookEdited;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not edit book. Reason : %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason : %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(editBookStatement);
            closeStatement(deleteGenreStatement);
            closeStatement(addGenreStatement);
            closeStatement(deleteAuthorStatement);
            closeStatement(addAuthorStatement);
            closeStatement(changeBookImageStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addAuthor(Author author) throws DAOException {
        boolean isAuthorAdded;
        Connection connection = null;
        PreparedStatement addAuthorStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            addAuthorStatement = connection.prepareStatement(SQL_ADD_AUTHOR);
            addAuthorStatement.setString(1, author.getName());
            addAuthorStatement.setString(2, author.getSurname());
            addAuthorStatement.setString(3, author.getPatronymic());
            addAuthorStatement.setObject(4, author.getDateOfBirth());
            int addAuthorResult = addAuthorStatement.executeUpdate();
            isAuthorAdded = addAuthorResult > 0;
            return isAuthorAdded;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not add author. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(addAuthorStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addPublisher(String publisherName) throws DAOException {
        boolean isPublisherAdded;
        Connection connection = null;
        PreparedStatement addPublisherStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            addPublisherStatement = connection.prepareStatement(SQL_ADD_PUBLISHER);
            addPublisherStatement.setString(1, publisherName);
            int addPublisherResult = addPublisherStatement.executeUpdate();
            isPublisherAdded = addPublisherResult > 0;
            return isPublisherAdded;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not add publisher. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(addPublisherStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean deletePublisher(List<Publisher> publishers) throws DAOException {
        boolean isPublisherDeleted = false;
        Connection connection = null;
        PreparedStatement deletePublisherStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            deletePublisherStatement = connection.prepareStatement(SQL_DELETE_PUBLISHER);
            int deletePublisherResult = 0;
            for (Publisher publisher : publishers) {
                deletePublisherStatement.setInt(1, publisher.getId());
                deletePublisherResult = deletePublisherStatement.executeUpdate();
                if (deletePublisherResult == 0) {
                    break;
                }
            }
            if (deletePublisherResult > 0) {
                isPublisherDeleted = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isPublisherDeleted;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not delete publisher. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(deletePublisherStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addGenre(Genre genre) throws DAOException {
        boolean isGenreAdded = false;
        Connection connection = null;
        PreparedStatement addGenreStatement = null;
        PreparedStatement getAllGenresStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getAllGenresStatement = connection.prepareStatement(SQL_GET_ALL_GENRES);
            boolean genreExist = false;
            ResultSet resultSet = getAllGenresStatement.executeQuery();
            while (resultSet.next()) {
                if (genre.getName().equalsIgnoreCase(resultSet.getString(GENRE_NAME))) {
                    genreExist = true;
                    break;
                }
            }
            if (!genreExist) {
                addGenreStatement = connection.prepareStatement(SQL_ADD_GENRE);
                addGenreStatement.setString(1, genre.getName());
                int addGenreResult = addGenreStatement.executeUpdate();
                isGenreAdded = addGenreResult > 0;
            }
            return isGenreAdded;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not add genre. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(getAllGenresStatement);
            closeStatement(addGenreStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean deleteGenre(List<Genre> genres) throws DAOException {
        boolean isGenreDeleted = false;
        Connection connection = null;
        PreparedStatement deleteGenreStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            deleteGenreStatement = connection.prepareStatement(SQL_DELETE_GENRE);
            int deleteGenreResult = 0;
            for (Genre genre : genres) {
                deleteGenreStatement.setInt(1, genre.getId());
                deleteGenreResult = deleteGenreStatement.executeUpdate();
                if (deleteGenreResult == 0) {
                    break;
                }
            }
            if (deleteGenreResult > 0) {
                isGenreDeleted = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isGenreDeleted;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not delete genre. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(deleteGenreStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean deleteAuthor(List<Author> authors) throws DAOException {
        boolean isAuthorDeleted = false;
        Connection connection = null;
        PreparedStatement deleteAuthorStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            deleteAuthorStatement = connection.prepareStatement(SQL_DELETE_AUTHOR);
            int deleteAuthorResult = 0;
            for (Author author : authors) {
                deleteAuthorStatement.setInt(1, author.getId());
                deleteAuthorResult = deleteAuthorStatement.executeUpdate();
                if (deleteAuthorResult == 0) {
                    break;
                }
            }
            if (deleteAuthorResult > 0) {
                isAuthorDeleted = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isAuthorDeleted;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not delete author. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(deleteAuthorStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Author> getAllAuthors() throws DAOException {
        List<Author> authors = new LinkedList<>();
        Connection connection = null;
        Statement getAllAuthorsStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getAllAuthorsStatement = connection.createStatement();
            ResultSet resultSet = getAllAuthorsStatement.executeQuery(SQL_GET_ALL_AUTHORS);
            while (resultSet.next()) {
                Author author = new Author();
                int authorId = resultSet.getInt(AUTHOR_ID);
                String name = resultSet.getString(AUTHOR_NAME);
                String surname = resultSet.getString(AUTHOR_SURNAME);
                String patronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                String date = resultSet.getString(AUTHOR_DATE_OF_BIRTH);
                LocalDate dateOfBirth = LocalDate.parse(date);
                author.setId(authorId);
                author.setName(name);
                author.setSurname(surname);
                author.setPatronymic(patronymic);
                author.setDateOfBirth(dateOfBirth);
                authors.add(author);
            }
            authors.sort(Comparator.comparing(Author::getSurname));
            return authors;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get authors. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(getAllAuthorsStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Publisher> getAllPublishers() throws DAOException {
        List<Publisher> publishers = new LinkedList<>();
        Connection connection = null;
        Statement getAllPublishersStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getAllPublishersStatement = connection.createStatement();
            ResultSet resultSet = getAllPublishersStatement.executeQuery(SQL_GET_ALL_PUBLISHERS);
            while (resultSet.next()) {
                Publisher publisher = new Publisher();
                int publisherId = Integer.parseInt(resultSet.getString(PUBLISHER_ID));
                String publisherName = resultSet.getString(PUBLISHER_NAME);
                publisher.setId(publisherId);
                publisher.setName(publisherName);
                publishers.add(publisher);
            }
            publishers.sort(Comparator.comparing(Publisher::getName));
            return publishers;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get publishers. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(getAllPublishersStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addBook(Book book) throws DAOException {
        boolean isBookFullyAdded = false;
        boolean isGenreAdded;
        boolean isAuthorAdded;
        boolean isBookAdded;
        Connection connection = null;
        PreparedStatement addBookStatement = null;
        PreparedStatement addGenreStatement = null;
        PreparedStatement addAuthorStatement = null;
        String bookTitle = book.getTitle();
        int bookPages = book.getPages();
        String bookIsbn = book.getIsbn();
        int bookYear = book.getYear();
        String bookDescription = book.getDescription();
        String location = book.getLocation().getName();
        int publisherId = book.getPublisher().getId();
        String image = book.getImage();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            addBookStatement = connection.prepareStatement(SQL_ADD_BOOK, Statement.RETURN_GENERATED_KEYS);
            addBookStatement.setString(1, bookTitle);
            addBookStatement.setInt(2, bookPages);
            addBookStatement.setString(3, bookIsbn);
            addBookStatement.setInt(4, bookYear);
            addBookStatement.setInt(5, publisherId);
            addBookStatement.setString(6, bookDescription);
            addBookStatement.setString(7, location);
            addBookStatement.setString(8, image);
            int addBookResult = addBookStatement.executeUpdate();
            isBookAdded = addBookResult > 0;
            ResultSet keys = addBookStatement.getGeneratedKeys();
            if (isBookAdded && keys.next()) {
                int lastBookId = keys.getInt(1);
                addGenreStatement = connection.prepareStatement(SQL_ADD_BOOK_GENRE);
                int addBookGenreResult = 0;
                List<Genre> genres = book.getGenre();
                for (Genre genre : genres) {
                    addGenreStatement.setInt(1, lastBookId);
                    addGenreStatement.setInt(2, genre.getId());
                    addBookGenreResult = addGenreStatement.executeUpdate();
                    if (addBookGenreResult == 0) {
                        break;
                    }
                }
                isGenreAdded = addBookGenreResult > 0;
                addAuthorStatement = connection.prepareStatement(SQL_ADD_BOOK_AUTHOR);
                int addBookAuthorResult = 0;
                List<Author> authors = book.getAuthors();
                for (Author author : authors) {
                    addAuthorStatement.setInt(1, lastBookId);
                    addAuthorStatement.setInt(2, author.getId());
                    addBookAuthorResult = addAuthorStatement.executeUpdate();
                    if (addBookAuthorResult == 0) {
                        break;
                    }
                }
                isAuthorAdded = addBookAuthorResult > 0;
                if (isAuthorAdded && isGenreAdded) {
                    isBookFullyAdded = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isBookFullyAdded;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not add book. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e.getMessage()), e1);
            }
        } finally {
            closeStatement(addBookStatement);
            closeStatement(addAuthorStatement);
            closeStatement(addGenreStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean deleteBook(int bookId) throws DAOException {
        boolean isBookDeleted = false;
        Connection connection = null;
        PreparedStatement deleteBookStatement = null;
        PreparedStatement isBookInStorageStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            isBookInStorageStatement = connection.prepareStatement(SQL_IS_BOOK_IN_STORAGE);
            isBookInStorageStatement.setInt(1, bookId);
            ResultSet resultSet = isBookInStorageStatement.executeQuery();
            if (resultSet.next()) {
                deleteBookStatement = connection.prepareStatement(SQL_DELETE_BOOK);
                deleteBookStatement.setInt(1, bookId);
                int deleteBookResult = deleteBookStatement.executeUpdate();
                isBookDeleted = deleteBookResult > 0;
            }
            return isBookDeleted;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not delete book. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(isBookInStorageStatement);
            closeStatement(deleteBookStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addOrder(Order order, String typeOfOrder) throws DAOException {
        boolean isOrderFullyAdded = false;
        boolean isOrderAdded;
        boolean isBookLocationChanged;
        Connection connection = null;
        PreparedStatement addOrderStatement = null;
        PreparedStatement changeBookStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            int libraryCard = order.getUser().getLibraryCardNumber();
            int bookId = order.getBook().getId();
            int librarianId = order.getLibrarian().getId();
            if (Location.valueOf(typeOfOrder).equals(Location.SUBSCRIPTION)) {
                addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_SUBSCRIPTION);
                changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_SUBSCRIPTION);

            } else if (Location.valueOf(typeOfOrder).equals(Location.READING_ROOM)) {
                addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_READING_ROOM);
                changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_READING_ROOM);
            }
            if (addOrderStatement != null) {
                addOrderStatement.setInt(1, libraryCard);
                addOrderStatement.setInt(2, bookId);
                addOrderStatement.setInt(3, librarianId);
                int addOrderResult = addOrderStatement.executeUpdate();
                isOrderAdded = addOrderResult > 0;
                if (isOrderAdded) {
                    changeBookStatusStatement.setInt(1, bookId);
                    int changeBookLocationResult = changeBookStatusStatement.executeUpdate();
                    isBookLocationChanged = changeBookLocationResult > 0;
                    if (isBookLocationChanged) {
                        isOrderFullyAdded = true;
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            }
            return isOrderFullyAdded;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not add order. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e.getMessage()), e1);
            }
        } finally {
            closeStatement(addOrderStatement);
            closeStatement(changeBookStatusStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean returnBook(int orderId, int bookId) throws DAOException {
        boolean isBookFullyReturned = false;
        boolean isBookReturned;
        boolean isBookLocationChanged;
        Connection connection = null;
        PreparedStatement returnBookStatement = null;
        PreparedStatement changeBookStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            returnBookStatement = connection.prepareStatement(SQL_RETURN_BOOK);
            returnBookStatement.setInt(1, orderId);
            int returnBookResult = returnBookStatement.executeUpdate();
            isBookReturned = returnBookResult > 0;
            if (isBookReturned) {
                changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_STORAGE);
                changeBookStatusStatement.setInt(1, bookId);
                int changeBookStatusResult = changeBookStatusStatement.executeUpdate();
                isBookLocationChanged = changeBookStatusResult > 0;
                if (isBookLocationChanged) {
                    isBookFullyReturned = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isBookFullyReturned;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not return book. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(changeBookStatusStatement);
            closeStatement(returnBookStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addOnlineOrder(int bookId, int libraryCard) throws DAOException {
        boolean isOnlineOrderFullyAdded = false;
        boolean isOnlineOrderAdded;
        boolean isBookLocationChanged;
        Connection connection = null;
        PreparedStatement addOnlineOrderStatement = null;
        PreparedStatement changeBookStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            addOnlineOrderStatement = connection.prepareStatement(SQL_ADD_ONLINE_ORDER);
            addOnlineOrderStatement.setInt(1, libraryCard);
            addOnlineOrderStatement.setInt(2, bookId);
            int addOnlineOrderResult = addOnlineOrderStatement.executeUpdate();
            isOnlineOrderAdded = addOnlineOrderResult > 0;
            if (isOnlineOrderAdded) {
                changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_ONLINE_ORDER);
                changeBookStatusStatement.setInt(1, bookId);
                int changeBookStatusResult = changeBookStatusStatement.executeUpdate();
                isBookLocationChanged = changeBookStatusResult > 0;
                if (isBookLocationChanged) {
                    isOnlineOrderFullyAdded = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isOnlineOrderFullyAdded;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not add online order. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(changeBookStatusStatement);
            closeStatement(addOnlineOrderStatement);
            closeConnection(connection);
        }
    }


    @Override
    public boolean cancelOnlineOrder(int orderId, int bookId) throws DAOException {
        boolean isOnlineOrderFullyCancelled = false;
        boolean isOnlineOrderCancelled;
        boolean isBookLocationChanged;
        Connection connection = null;
        PreparedStatement cancelOnlineOrderStatement = null;
        PreparedStatement changeBookLocationStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            cancelOnlineOrderStatement = connection.prepareStatement(SQL_CANCEL_ONLINE_ORDER);
            cancelOnlineOrderStatement.setInt(1, orderId);
            int cancelOnlineOrderResult = cancelOnlineOrderStatement.executeUpdate();
            isOnlineOrderCancelled = cancelOnlineOrderResult > 0;
            if (isOnlineOrderCancelled) {
                changeBookLocationStatement = connection.prepareStatement(SQL_BOOK_LOCATION_STORAGE);
                changeBookLocationStatement.setInt(1, bookId);
                int changeBookLocationResult = changeBookLocationStatement.executeUpdate();
                isBookLocationChanged = changeBookLocationResult > 0;
                if (isBookLocationChanged) {
                    isOnlineOrderFullyCancelled = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isOnlineOrderFullyCancelled;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not cancel online order. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(changeBookLocationStatement);
            closeStatement(cancelOnlineOrderStatement);
            closeConnection(connection);
        }
    }

    @Override
    public OnlineOrder onlineOrderStatus(int orderId) throws DAOException {
        OnlineOrder order = new OnlineOrder();
        Connection connection = null;
        PreparedStatement onlineOrderStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            onlineOrderStatusStatement = connection.prepareStatement(SQL_ONLINE_ORDER_STATUS);
            onlineOrderStatusStatement.setInt(1, orderId);
            ResultSet resultSet = onlineOrderStatusStatement.executeQuery();
            if (resultSet.next()) {
                String orderStatus = resultSet.getString(ONLINE_ORDER_STATUS);
                Location location = Location.valueOf(orderStatus.toUpperCase());
                order.setLocation(location);
            }
            return order;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get online order status. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(onlineOrderStatusStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean executeOnlineOrder(OnlineOrder onlineOrder, String typeOfOrder) throws DAOException {
        boolean isOnlineOrderFullyExecuted = false;
        boolean isOnlineOrderExecuted;
        boolean isOnlineOrderAdded;
        Connection connection = null;
        PreparedStatement executeOnlineOrderStatement = null;
        PreparedStatement addOrderStatement = null;
        PreparedStatement changeBookStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            executeOnlineOrderStatement = connection.prepareStatement(SQL_EXECUTE_ONLINE_ORDER);
            executeOnlineOrderStatement.setInt(1, onlineOrder.getId());
            int executeOnlineOrderResult = executeOnlineOrderStatement.executeUpdate();
            isOnlineOrderExecuted = executeOnlineOrderResult > 0;
            if (isOnlineOrderExecuted) {
                if (Location.valueOf(typeOfOrder).equals(Location.SUBSCRIPTION)) {
                    addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_SUBSCRIPTION);
                    changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_SUBSCRIPTION);

                } else if (Location.valueOf(typeOfOrder).equals(Location.READING_ROOM)) {
                    addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_READING_ROOM);
                    changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_READING_ROOM);

                }
                if (addOrderStatement != null && changeBookStatusStatement != null) {
                    int libraryCard = onlineOrder.getUser().getLibraryCardNumber();
                    int bookId = onlineOrder.getBook().getId();
                    int librarianId = onlineOrder.getLibrarian().getId();
                    addOrderStatement.setInt(1, libraryCard);
                    addOrderStatement.setInt(2, bookId);
                    addOrderStatement.setInt(3, librarianId);
                    changeBookStatusStatement.setInt(1, bookId);
                    int addOnlineOrderResult = addOrderStatement.executeUpdate();
                    isOnlineOrderAdded = addOnlineOrderResult > 0;
                    if (isOnlineOrderAdded) {
                        int bookChangeLocationResult = changeBookStatusStatement.executeUpdate();
                        if (bookChangeLocationResult > 0) {
                            isOnlineOrderFullyExecuted = true;
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

            } else {
                connection.rollback();
            }
            return isOnlineOrderFullyExecuted;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(String.format("Can not execute online order. Reason: %s", e.getMessage()), e);
            } catch (SQLException e1) {
                throw new DAOException(String.format("Can not make rollback. Reason: %s", e1.getMessage()), e1);
            }
        } finally {
            closeStatement(executeOnlineOrderStatement);
            closeStatement(changeBookStatusStatement);
            closeStatement(addOrderStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Connection connection = null;
        PreparedStatement getBooksByGenreStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getBooksByGenreStatement = connection.prepareStatement(SQL_GET_BOOK_BY_GENRE);
            getBooksByGenreStatement.setString(1, genre.getName());
            ResultSet resultSet = getBooksByGenreStatement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt(BOOK_ID);
                int lastBookFromListId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastBookFromListId == bookId) {
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    Genre bookGenre = new Genre();
                    bookGenre.setId(genreId);
                    bookGenre.setName(genreName);
                    if (!books.getLast().getGenre().contains(bookGenre)) {
                        books.getLast().addGenre(bookGenre);
                    }
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    if (!books.getLast().getAuthors().contains(author)) {
                        books.getLast().addAuthor(author);
                    }
                } else {
                    Book book = new Book();
                    book.setId(bookId);
                    book.setTitle(resultSet.getString(BOOK_TITLE));
                    book.setPages(resultSet.getInt(BOOK_PAGES));
                    book.setIsbn(resultSet.getString(BOOK_ISBN));
                    book.setYear(resultSet.getInt(BOOK_YEAR));
                    book.setDescription(resultSet.getString(BOOK_DESCRIPTION));
                    book.setLocation(Location.valueOf(resultSet.getString(BOOK_LOCATION).toUpperCase()));
                    Publisher publisher = new Publisher();
                    Integer publisherId = resultSet.getInt(PUBLISHER_ID);
                    publisher.setId(publisherId);
                    String publisherName = resultSet.getString(PUBLISHER_NAME);
                    publisher.setName(publisherName);
                    book.setPublisher(publisher);
                    String genreName = resultSet.getString(GENRES_NAME);
                    int genreId = resultSet.getInt(GENRES_ID);
                    Genre bookGenre = new Genre();
                    bookGenre.setId(genreId);
                    bookGenre.setName(genreName);
                    book.addGenre(bookGenre);
                    Author author = new Author();
                    String authorName = resultSet.getString(AUTHOR_NAME);
                    String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                    String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    book.addAuthor(author);
                    Blob imageBlob = resultSet.getBlob(BOOK_IMAGE);
                    if (imageBlob != null) {
                        String image = new String(imageBlob.getBytes(1, (int) imageBlob.length()));
                        book.setImage(image);
                    }
                    books.add(book);
                }
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get books by genre. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(getBooksByGenreStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Book> getRandomBooks(int numberOfBooks) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Connection connection = null;
        PreparedStatement getRandomBookIdStatement = null;
        PreparedStatement getExplicitBookInfoStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            getRandomBookIdStatement = connection.prepareStatement(SQL_GET_RANDOM_BOOK_ID);
            getRandomBookIdStatement.setInt(1, numberOfBooks);
            ResultSet idValues = getRandomBookIdStatement.executeQuery();
            List<Integer> bookIds = new ArrayList<>();
            while (idValues.next()) {
                Integer id = idValues.getInt(BOOK_ID);
                bookIds.add(id);
            }
            for (int id : bookIds) {
                getExplicitBookInfoStatement = connection.prepareStatement(SQL_FIND_BOOK_BY_ID);
                getExplicitBookInfoStatement.setInt(1, id);
                ResultSet resultSet = getExplicitBookInfoStatement.executeQuery();
                while (resultSet.next()) {
                    int bookId = resultSet.getInt(BOOK_ID);
                    int lastBookFromListId = !books.isEmpty() ? books.getLast().getId() : 0;
                    if (lastBookFromListId == bookId) {
                        String genreName = resultSet.getString(GENRES_NAME);
                        int genreId = resultSet.getInt(GENRES_ID);
                        Genre bookGenre = new Genre();
                        bookGenre.setId(genreId);
                        bookGenre.setName(genreName);
                        if (!books.getLast().getGenre().contains(bookGenre)) {
                            books.getLast().addGenre(bookGenre);
                        }
                        Author author = new Author();
                        String authorName = resultSet.getString(AUTHOR_NAME);
                        String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                        String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                        author.setName(authorName);
                        author.setSurname(authorSurname);
                        author.setPatronymic(authorPatronymic);
                        if (!books.getLast().getAuthors().contains(author)) {
                            books.getLast().addAuthor(author);
                        }
                    }else {
                        Book book = new Book();
                        book.setId(bookId);
                        book.setTitle(resultSet.getString(BOOK_TITLE));
                        book.setPages(resultSet.getInt(BOOK_PAGES));
                        book.setIsbn(resultSet.getString(BOOK_ISBN));
                        book.setYear(resultSet.getInt(BOOK_YEAR));
                        book.setDescription(resultSet.getString(BOOK_DESCRIPTION));
                        book.setLocation(Location.valueOf(resultSet.getString(BOOK_LOCATION).toUpperCase()));
                        Publisher publisher = new Publisher();
                        Integer publisherId = resultSet.getInt(PUBLISHER_ID);
                        publisher.setId(publisherId);
                        String publisherName = resultSet.getString(PUBLISHER_NAME);
                        publisher.setName(publisherName);
                        book.setPublisher(publisher);
                        String genreName = resultSet.getString(GENRES_NAME);
                        int genreId = resultSet.getInt(GENRES_ID);
                        Genre bookGenre = new Genre();
                        bookGenre.setId(genreId);
                        bookGenre.setName(genreName);
                        book.addGenre(bookGenre);
                        Author author = new Author();
                        String authorName = resultSet.getString(AUTHOR_NAME);
                        String authorSurname = resultSet.getString(AUTHOR_SURNAME);
                        String authorPatronymic = resultSet.getString(AUTHOR_PATRONYMIC);
                        author.setName(authorName);
                        author.setSurname(authorSurname);
                        author.setPatronymic(authorPatronymic);
                        book.addAuthor(author);
                        Blob imageBlob = resultSet.getBlob(BOOK_IMAGE);
                        if (imageBlob != null) {
                            String image = new String(imageBlob.getBytes(1, (int) imageBlob.length()));
                            book.setImage(image);
                        }
                        books.add(book);
                    }
                }
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get books. Reason: %s", e.getMessage()), e);
        } finally {
            closeStatement(getRandomBookIdStatement);
            closeStatement(getExplicitBookInfoStatement);
            closeConnection(connection);
        }
    }
}
