package by.epam.bokhan.dao;

import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.pool.ConnectionPool;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


/**
 * Created by vbokh on 01.08.2017.
 */
public class BookDAOImpl extends AbstractDAO implements BookDAO {
    private static final String SQL_GET_ALL_BOOKS = "SELECT book.id, book.title, book.pages,book.isbn, book.year, book.description, book.location,publisher.id, publisher.name as publisher,\n" +
            "genres.genre_name,genres.genre_id, authors.author_name, authors.author_surname, authors.author_patronymic\n" +
            "from book\n" +
            "left join publisher on book.publisher_id = publisher.id\n" +
            "left join (select book_id,genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            "left join (select book_id, author_id, author.name as author_name, author.surname as author_surname,author.patronymic as author_patronymic \n" +
            "\tfrom book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = authors.book_id";
    private static final String SQL_FIND_BOOK_BY_ID = "SELECT book.id, book.title,book.pages, book.location, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year\n" +
            "            from book\n" +
            "            left join (select book_author.book_id as b,author.name , author.surname , author.patronymic  from book_author left join author on book_author.author_id = author.id) as authors\n" +
            "            on book.id = b \n" +
            "            where book.id = ?";

    private static final String SQL_GET_BOOK_FOR_EDITING = "SELECT book.id, book.title,book.pages,book.isbn,  book.location,publisher.id, publisher.name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year,\n" +
            "genres.id, genres.name\n" +
            "from book\n" +
            "left join (select book_author.book_id as b,author.name , author.surname , author.patronymic  from book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = b \n" +
            "left join (select book_genre.book_id as b_id, genre.id, genre.name from book_genre left join genre on book_genre.genre_id = genre.id) as genres\n" +
            "on book.id = b_id\n" +
            "left join publisher\n" +
            "on book.publisher_id = publisher.id\n" +
            "where book.id = ?";

    private static final String SQL_FIND_BOOK_BY_TITLE = "SELECT book.id, book.title,book.pages, book.location, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year\n" +
            "            from book\n" +
            "            left join (select book_author.book_id as b,author.name , author.surname , author.patronymic  from book_author left join author on book_author.author_id = author.id) as authors\n" +
            "            on book.id = b \n" +
            "            where book.title LIKE ?";


    private static final String SQL_GET_EXPLICIT_BOOK_INFO = "SELECT book.id, book.title,book.pages,book.isbn, book.location,genres.genre_id, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.year,publisher.id, publisher.name as publisher\n" +
            " from book \n" +
            " left join publisher on book.publisher_id = publisher.id\n" +
            " left join (select book_author.book_id as b,author.name, author.surname, author.patronymic from book_author left join author on book_author.author_id = author.id) as authors\n" +
            " on book.id = b\n" +
            "left join (select book_id,genre.id as genre_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            " where book.id = ?";

    private static final String SQL_GET_BOOKS_LAST_ORDER = "SELECT orders.book_id,orders.library_card_id,orders.order_date, orders.expiration_date, orders.return_date,\n" +
            "user.id, user.name, user.surname, user.patronymic,library_card.id, library_card.mobile_phone\n" +
            "from orders\n" +
            "left join library_card on library_card.id = orders.library_card_id\n" +
            "left join user\n" +
            "on library_card.user_id = user.id\n" +
            "where orders.order_date = (select max(order_date) from orders where orders.book_id = ?) and orders.book_id = ?";

    private static final String SQL_GET_ALL_GENRES = "Select * from genre";
    private static final String SQL_EDIT_BOOK = "Update book set title = ?, pages = ?, isbn = ?, year = ?,publisher_id = ? where id = ?";
    private static final String SQL_DELETE_BOOKS_GENRE = "DELETE FROM book_genre WHERE book_genre.book_id = ?";
    private static final String SQL_DELETE_BOOKS_AUTHOR = "DELETE FROM book_author WHERE book_author.book_id = ?";
    private static final String SQL_DELETE_GENRE = "DELETE FROM genre WHERE genre.id = ?";
    private static final String SQL_DELETE_AUTHOR = "DELETE FROM author WHERE author.id = ?";
    private static final String SQL_DELETE_PUBLISHER = "DELETE FROM publisher WHERE publisher.id = ?";
    private static final String SQL_ADD_AUTHOR = "INSERT INTO author (author.name, author.surname, author.patronymic, author.date_of_birth) VALUES (?,?,?,?)";
    private static final String SQL_ADD_PUBLISHER = "INSERT INTO publisher (publisher.name) VALUES (?)";
    private static final String SQL_GET_ALL_AUTHORS = "Select * from author";
    private static final String SQL_GET_ALL_PUBLISHERS = "Select * from publisher";
    private static final String SQL_ADD_BOOK = "INSERT INTO book (book.title, book.pages,book.isbn, book.year,book.publisher_id,book.description,book.location) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_ADD_BOOK_GENRE = "INSERT INTO book_genre (book_id,genre_id) VALUES (?,?)";
    private static final String SQL_ADD_GENRE = "INSERT INTO genre (genre.name) VALUES (?)";
    private static final String SQL_ADD_BOOK_AUTHOR = "INSERT INTO book_author (book_id,author_id) VALUES (?,?)";
    private static final String SQL_IS_BOOK_IN_STORAGE = "SELECT * from book where book.id = ? and book.location = 'storage'";
    private static final String SQL_DELETE_BOOK = "DELETE FROM book where book.id = ?";
    private static final String SQL_ADD_ORDER_ON_READING_ROOM = "INSERT INTO ORDERS (orders.user_id, orders.book_id, orders.order_date, orders.expiration_date, orders.return_date, orders.librarian_id) VALUES (?,?,now(),addtime(now(), '1 0:0:0.0'), null,?)";
    private static final String SQL_ADD_ORDER_ON_SUBSCRIPTION = "INSERT INTO ORDERS (orders.user_id, orders.book_id, orders.order_date, orders.expiration_date, orders.return_date, orders.librarian_id) VALUES (?,?,now(),addtime(now(), '30 0:0:0.0'), null,?)";
    private static final String SQL_BOOK_LOCATION_SUBSCRIPTION = "Update book set location = 'subscription' where book.id = ?";
    private static final String SQL_BOOK_LOCATION_READING_ROOM = "Update book set location = 'reading_room' where book.id = ?";
    private static final String SQL_BOOK_LOCATION_STORAGE = "Update book set location = 'storage' where book.id = ?";
    private static final String SQL_BOOK_LOCATION_ONLINE_ORDER = "Update book set location = 'online_order' where book.id = ?";
    private static final String SQL_GET_USER_ORDERS = "Select orders.id, book.id, book.title, book.isbn, user.library_card, user.name, user.surname, user.patronymic, user.mobile_phone, orders.order_date, orders.expiration_date, orders.return_date from orders \n" +
            "right join user\n" +
            "on orders.user_id = user.library_card\n" +
            "left join book on book.id = orders.book_id\n" +
            "where user.library_card = ?";
    private static final String SQL_RETURN_BOOK = "UPDATE orders set orders.return_date = now() where orders.id = ?";
    private static final String SQL_ADD_ONLINE_ORDER = "INSERT INTO online_orders (online_orders.user_id, online_orders.book_id, online_orders.order_date, online_orders.expiration_date, online_orders.order_execution_date, online_orders.order_status) \n" +
            "VALUES (?,?,now(),addtime(now(), '3 0:0:0.0'), null,'booked')";

    private static final String SQL_GET_USER_ONLINE_ORDERS = "Select online_orders.id, book.id, book.title, book.isbn, authors.name,authors.surname, authors.patronymic, user.library_card, online_orders.order_date, online_orders.expiration_date, online_orders.order_execution_date,online_orders.order_status\n" +
            "from online_orders \n" +
            "right join user\n" +
            "on online_orders.user_id = user.library_card\n" +
            "left join book on book.id = online_orders.book_id\n" +
            "left join (select book_author.book_id as b_id, author.name, author.surname, author.patronymic from author join book_author on book_author.author_id = author.id) as authors\n" +
            "on authors.b_id = online_orders.book_id\n" +
            "where user.library_card = ?";
    private static final String SQL_CANCEL_ONLINE_ORDER = "Update online_orders set online_orders.order_execution_date = now(), order_status = 'canceled' where online_orders.id = ?";
    private static final String SQL_ONLINE_ORDER_STATUS = "SELECT order_status from online_orders where online_orders.id = ?";
    private static final String SQL_EXECUTE_ONLINE_ORDER ="Update online_orders SET online_orders.order_execution_date = now(), online_orders.order_status = 'executed' where online_orders.id = ?";
    private final String ORDER_DATE = "orders.order_date";
    private final String EXPIRATION_DATE = "orders.expiration_date";
    private final String RETURN_DATE = "orders.return_date";
    private final String USER_NAME = "user.name";
    private final String USER_SURNAME = "user.surname";
    private final String USER_PATRONYMIC = "user.patronymic";
    private final String MOBILE_PHONE = "library_card.mobile_phone";
    private final String USER_ID = "user.id";
    private final String LIBRARY_CARD = "library_card.id";


    @Override
    public List<Book> getAllBooks() throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_ALL_BOOKS);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Book book = new Book();

                int bookId = rs.getInt("book.id");
                int lastId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastId == bookId) {
                    String g = rs.getString("genre_name");
                    int gId = rs.getInt("genres.genre_id");
                    if (g != null && !g.isEmpty()) {
                        Genre gen = new Genre();
                        gen.setId(gId);
                        gen.setName(g);
                        if (!books.getLast().getGenre().contains(gen)) {
                            books.getLast().addGenre(gen);
                        }

                    }
                    String authorName = rs.getString("author_name");
                    String authorSurname = rs.getString("author_surname");
                    String authorPatronymic = rs.getString("author_patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book b = books.getLast();
                    if (!b.getAuthors().contains(author)) {
                        b.addAuthor(author);
                    }
                    continue;
                }
                book.setId(bookId);
                book.setTitle(rs.getString("book.title"));
                book.setPages(rs.getInt("book.pages"));
                book.setIsbn(rs.getString("book.isbn"));
                book.setYear(rs.getInt("book.year"));
                book.setDescription(new String(rs.getBytes("book.description"), StandardCharsets.UTF_8));
                book.setLocation(Location.valueOf(rs.getString("book.location").toUpperCase()));
                Publisher publisher = new Publisher();
                Integer publisherId = rs.getInt("publisher.id");
                if (publisherId != null) {
                    publisher.setId(publisherId);
                }
                String publisherName = rs.getString("publisher");
                if (publisherName != null && !publisherName.isEmpty()) {
                    publisher.setName(publisherName);
                }
                book.setPublisher(publisher);
                String g = rs.getString("genre_name");
                int gId = rs.getInt("genres.genre_id");
                if (g != null && !g.isEmpty()) {
                    Genre gen = new Genre();
                    gen.setId(gId);
                    gen.setName(g);
                    book.addGenre(gen);
                }
                String authorName = rs.getString("author_name");
                String authorSurname = rs.getString("author_surname");
                String authorPatronymic = rs.getString("author_patronymic");
                Author author = new Author();
                author.setName(authorName);
                author.setSurname(authorSurname);
                author.setPatronymic(authorPatronymic);
                book.addAuthor(author);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get books. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public List<Book> findBookById(int bookId) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Connection connection = null;
        PreparedStatement st = null;
        Book book;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_BOOK_BY_ID);
            st.setInt(1, bookId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                book = new Book();
                int bId = rs.getInt("book.id");
                int lastId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastId == bId) {
                    String authorName = rs.getString("author_name");
                    String authorSurname = rs.getString("author_surname");
                    String authorPatronymic = rs.getString("author_patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book b = books.getLast();
                    if (!b.getAuthors().contains(author)) {
                        b.addAuthor(author);
                    }
                    continue;
                }
                book.setId(bId);
                book.setTitle(rs.getString("book.title"));
                book.setPages(rs.getInt("book.pages"));
                book.setYear(rs.getInt("book.year"));
                book.setLocation(Location.valueOf(rs.getString("book.location").toUpperCase()));
                String authorName = rs.getString("author_name");
                String authorSurname = rs.getString("author_surname");
                String authorPatronymic = rs.getString("author_patronymic");
                Author author = new Author();
                author.setName(authorName);
                author.setSurname(authorSurname);
                author.setPatronymic(authorPatronymic);
                book.addAuthor(author);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public List<Book> findBookByTitle(String title) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Book book;
        Connection connection = null;
        PreparedStatement st = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_BOOK_BY_TITLE);
            st.setString(1, "%" + title + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                book = new Book();
                int bId = rs.getInt("book.id");
                int lastId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastId == bId) {
                    String authorName = rs.getString("author_name");
                    String authorSurname = rs.getString("author_surname");
                    String authorPatronymic = rs.getString("author_patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book b = books.getLast();
                    if (!b.getAuthors().contains(author)) {
                        b.addAuthor(author);
                    }
                    continue;
                }
                book.setId(bId);
                book.setTitle(rs.getString("book.title"));
                book.setPages(rs.getInt("book.pages"));
                book.setYear(rs.getInt("book.year"));
                book.setLocation(Location.valueOf(rs.getString("book.location").toUpperCase()));
                String authorName = rs.getString("author_name");
                String authorSurname = rs.getString("author_surname");
                String authorPatronymic = rs.getString("author_patronymic");
                Author author = new Author();
                author.setName(authorName);
                author.setSurname(authorSurname);
                author.setPatronymic(authorPatronymic);
                book.addAuthor(author);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }


    public List<Book> getExplicitBookInfo(int bookId) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_EXPLICIT_BOOK_INFO);
            st.setInt(1, bookId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                Genre genre;
                int bId = rs.getInt("book.id");
                int lastId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastId == bId) {
                    String g = rs.getString("genre_name");
                    int gId = rs.getInt("genres.genre_id");
                    if (g != null && !g.isEmpty()) {
                        genre = new Genre();
                        genre.setId(gId);
                        genre.setName(g);
                        if (!books.getLast().getGenre().contains(genre)) {
                            books.getLast().addGenre(genre);
                        }

                    }
                    String authorName = rs.getString("author_name");
                    String authorSurname = rs.getString("author_surname");
                    String authorPatronymic = rs.getString("author_patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book b = books.getLast();
                    if (!b.getAuthors().contains(author)) {
                        b.addAuthor(author);
                    }
                    continue;
                }
                book.setId(bookId);
                book.setTitle(rs.getString("book.title"));
                book.setPages(rs.getInt("book.pages"));
                book.setIsbn(rs.getString("book.isbn"));
                book.setYear(rs.getInt("book.year"));
                book.setLocation(Location.valueOf(rs.getString("book.location").toUpperCase()));
                Publisher publisher = new Publisher();
                Integer publisherId = rs.getInt("publisher.id");
                if (publisherId != null) {
                    publisher.setId(publisherId);
                }
                String publisherName = rs.getString("publisher");
                if (publisherName != null && !publisherName.isEmpty()) {
                    publisher.setName(publisherName);
                }
                publisher.setId(rs.getInt("publisher.id"));
                publisher.setName(rs.getString("publisher"));
                book.setPublisher(publisher);
                String g = rs.getString("genre_name");
                int gId = rs.getInt("genres.genre_id");
                if (g != null && !g.isEmpty()) {
                    genre = new Genre();
                    genre.setId(gId);
                    genre.setName(g);
                    book.addGenre(genre);
                }
                String authorName = rs.getString("author_name");
                String authorSurname = rs.getString("author_surname");
                String authorPatronymic = rs.getString("author_patronymic");
                Author author = new Author();
                author.setName(authorName);
                author.setSurname(authorSurname);
                author.setPatronymic(authorPatronymic);
                book.addAuthor(author);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get books. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public List<Order> getBooksLastOrder(int bookId) throws DAOException {
        List<Order> orders = new LinkedList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_BOOKS_LAST_ORDER);
            st.setInt(1, bookId);
            st.setInt(2,bookId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                Timestamp lastOrderTimeStamp = rs.getTimestamp(ORDER_DATE);
                LocalDate lastOrderDate = lastOrderTimeStamp != null ? lastOrderTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setOrderDate(lastOrderDate);
                Timestamp expirationDateTimeStamp = rs.getTimestamp(EXPIRATION_DATE);
                LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setExpirationDate(expirationDate);
                Timestamp returnDateTimeStamp = rs.getTimestamp(RETURN_DATE);
                LocalDate returnDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setReturnDate(returnDate);
                User user = new User();
                user.setName(rs.getString(USER_NAME));
                user.setSurname(rs.getString(USER_SURNAME));
                user.setPatronymic(rs.getString(USER_PATRONYMIC));
                user.setMobilePhone(rs.getString(MOBILE_PHONE));
                user.setId(rs.getInt(USER_ID));
                user.setLibraryCardNumber(rs.getInt(LIBRARY_CARD));
                order.setUser(user);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can not get order. Reason : %s", e.getMessage()), e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public List<Book> getBookForEditing(int bookId) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Book book;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_BOOK_FOR_EDITING);
            st.setInt(1, bookId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                book = new Book();
                Genre genre;
                int bId = rs.getInt("book.id");
                int lastId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastId == bId) {
                    String g = rs.getString("genres.name");
                    int gId = rs.getInt("genres.id");
                    if (g != null && !g.isEmpty()) {
                        genre = new Genre();
                        genre.setId(gId);
                        genre.setName(g);
                        if (!books.getLast().getGenre().contains(genre)) {
                            books.getLast().addGenre(genre);
                        }

                    }
                    String authorName = rs.getString("author_name");
                    String authorSurname = rs.getString("author_surname");
                    String authorPatronymic = rs.getString("author_patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book b = books.getLast();
                    if (!b.getAuthors().contains(author)) {
                        b.addAuthor(author);
                    }
                    continue;
                }
                book.setId(bId);
                book.setTitle(rs.getString("book.title"));
                book.setPages(rs.getInt("book.pages"));
                book.setYear(rs.getInt("book.year"));
                String g = rs.getString("genres.name");
                int gId = rs.getInt("genres.id");
                if (g != null && !g.isEmpty()) {
                    genre = new Genre();
                    genre.setId(gId);
                    genre.setName(g);
                    book.addGenre(genre);
                }
                book.setIsbn(rs.getString("book.isbn"));
                Publisher publisher = new Publisher();
                Integer publisherId = rs.getInt("publisher.id");
                if (publisherId != null) {
                    publisher.setId(publisherId);
                }
                String publisherName = rs.getString("publisher.name");
                if (publisherName != null && !publisherName.isEmpty()) {
                    publisher.setName(publisherName);
                }
                book.setPublisher(publisher);
                String authorName = rs.getString("author_name");
                String authorSurname = rs.getString("author_surname");
                String authorPatronymic = rs.getString("author_patronymic");
                Author author = new Author();
                author.setName(authorName);
                author.setSurname(authorSurname);
                author.setPatronymic(authorPatronymic);
                book.addAuthor(author);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public LinkedList<Genre> getAllGenres() throws DAOException {
        LinkedList<Genre> genres = new LinkedList<>();
        Connection connection = null;
        Statement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_GET_ALL_GENRES);
            while (rs.next()) {
                String name = rs.getString("name");
                int gId = rs.getInt("id");
                Genre genre = new Genre();
                genre.setId(gId);
                genre.setName(name);
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean editBook(Book book, int[] genreId, int[] authorId) throws DAOException {
        boolean isBookEdited = false;
        Connection connection = null;
        PreparedStatement st = null;
        PreparedStatement deleteGenreStatement = null;
        PreparedStatement addGenreStatement = null;
        PreparedStatement deleteAuthorStatement = null;
        PreparedStatement addAuthorStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            st = connection.prepareStatement(SQL_EDIT_BOOK);
            st.setString(1, book.getTitle());
            st.setInt(2, book.getPages());
            st.setString(3, book.getIsbn());
            st.setInt(4, book.getYear());
            st.setInt(5, book.getPublisher().getId());
            st.setInt(6, book.getId());
            int editBookRes = st.executeUpdate();

            deleteGenreStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRE);
            deleteGenreStatement.setInt(1, book.getId());
            deleteGenreStatement.executeUpdate();
            addGenreStatement = connection.prepareStatement(SQL_ADD_BOOK_GENRE);
            int addGenreRes = 0;
            for (int genId : genreId) {
                addGenreStatement.setInt(1, book.getId());
                addGenreStatement.setInt(2, genId);
                addGenreRes += addGenreStatement.executeUpdate();
            }

            deleteAuthorStatement = connection.prepareStatement(SQL_DELETE_BOOKS_AUTHOR);
            deleteAuthorStatement.setInt(1, book.getId());
            deleteAuthorStatement.executeUpdate();

            addAuthorStatement = connection.prepareStatement(SQL_ADD_BOOK_AUTHOR);
            int addAuthorRes = 0;
            for (int authId : authorId) {
                addAuthorStatement.setInt(1, book.getId());
                addAuthorStatement.setInt(2, authId);
                addAuthorRes += addAuthorStatement.executeUpdate();
            }

            if (editBookRes != 0 && addGenreRes != 0 && addAuthorRes != 0) {
                isBookEdited = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isBookEdited;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeStatement(deleteGenreStatement);
            closeStatement(addGenreStatement);
            closeStatement(deleteAuthorStatement);
            closeStatement(addAuthorStatement);
            closeConnection(connection);
        }
    }

    public boolean addAuthor(String name, String surname, String patronymic, String dateOfBirth) throws DAOException {
        boolean isAuthorAdded = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_ADD_AUTHOR);
            st.setString(1, name);
            st.setString(2, surname);
            st.setString(3, patronymic);
            LocalDate date = LocalDate.parse(dateOfBirth);
            st.setObject(4, date);
            int res = st.executeUpdate();
            if (res > 0) {
                isAuthorAdded = true;
            }
            return isAuthorAdded;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addPublisher(String publisherName) throws DAOException {
        boolean isPublisherAdded = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_ADD_PUBLISHER);
            st.setString(1, publisherName);
            int res = st.executeUpdate();
            if (res > 0) {
                isPublisherAdded = true;
            }
            return isPublisherAdded;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean deletePublisher(int[] publishersIds) throws DAOException {
        boolean isPublisherDeleted = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_DELETE_PUBLISHER);
            int res = 0;
            for (int id : publishersIds) {
                st.setInt(1, id);
                res += st.executeUpdate();
            }
            if (res > 0) {
                isPublisherDeleted = true;
            }
            return isPublisherDeleted;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addGenre(String genreName) throws DAOException {
        boolean isGenreAdded = false;
        Connection connection = null;
        PreparedStatement st = null;
        Statement allGenresStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            allGenresStatement = connection.createStatement();
            ResultSet rs = allGenresStatement.executeQuery(SQL_GET_ALL_GENRES);
            boolean genreExist = false;
            while (rs.next()) {
                if (rs.getString("genre.name").equalsIgnoreCase(genreName)) {
                    genreExist = true;
                }
            }
            if (!genreExist) {
                st = connection.prepareStatement(SQL_ADD_GENRE);
                st.setString(1, genreName);
                int res = st.executeUpdate();
                if (res > 0) {
                    isGenreAdded = true;
                }
            }
            return isGenreAdded;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(allGenresStatement);
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean deleteGenre(int[] genreId) throws DAOException {
        boolean isGenreDeleted = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_DELETE_GENRE);
            int res = 0;
            for (int id : genreId) {
                st.setInt(1, id);
                res += st.executeUpdate();
            }
            if (res > 0) {
                isGenreDeleted = true;
            }
            return isGenreDeleted;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean deleteAuthor(int[] authorIds) throws DAOException {
        boolean isAuthorDeleted = false;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_DELETE_AUTHOR);
            int res = 0;
            for (int id : authorIds) {
                st.setInt(1, id);
                res += st.executeUpdate();
            }
            if (res > 0) {
                isAuthorDeleted = true;
            }
            return isAuthorDeleted;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public LinkedList<Author> getAllAuthors() throws DAOException {
        LinkedList<Author> authors = new LinkedList<>();
        Connection connection = null;
        Statement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_GET_ALL_AUTHORS);
            while (rs.next()) {
                Author author = new Author();
                int authorId = Integer.parseInt(rs.getString("author.id"));
                String name = rs.getString("author.name");
                String surname = rs.getString("author.surname");
                String patronymic = rs.getString("author.patronymic");
                String date = rs.getString("author.date_of_birth");
                LocalDate dateOfBirth = LocalDate.parse(date);
                author.setId(authorId);
                author.setName(name);
                author.setSurname(surname);
                author.setPatronymic(patronymic);
                author.setDateOfBirth(dateOfBirth);
                authors.add(author);
            }
            authors.sort(Comparator.comparing(a -> a.getSurname()));
            return authors;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public LinkedList<Publisher> getAllPublishers() throws DAOException {
        LinkedList<Publisher> publishers = new LinkedList<>();
        Connection connection = null;
        Statement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_GET_ALL_PUBLISHERS);
            while (rs.next()) {
                Publisher publisher = new Publisher();
                int publisherId = Integer.parseInt(rs.getString("publisher.id"));
                String publisherName = rs.getString("publisher.name");
                publisher.setId(publisherId);
                publisher.setName(publisherName);
                publishers.add(publisher);
            }
            Collections.sort(publishers, Comparator.comparing(a -> a.getName()));
            return publishers;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addBook(Book book, int publisherId, int genreId[], int[] authorId) throws DAOException {
        boolean isBookAdded = false;
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
            int resultBookInsert = addBookStatement.executeUpdate();
            ResultSet keys = addBookStatement.getGeneratedKeys();
            if (keys.next()) {
                int lastBookId = keys.getInt(1);
                addGenreStatement = connection.prepareStatement(SQL_ADD_BOOK_GENRE);
                int resultGenreInsert = 0;
                for (int id : genreId) {
                    addGenreStatement.setInt(1, lastBookId);
                    addGenreStatement.setInt(2, id);
                    resultGenreInsert += addGenreStatement.executeUpdate();
                }
                addAuthorStatement = connection.prepareStatement(SQL_ADD_BOOK_AUTHOR);
                int resultAuthorInsert = 0;
                for (int id : authorId) {
                    addAuthorStatement.setInt(1, lastBookId);
                    addAuthorStatement.setInt(2, id);
                    resultAuthorInsert += addAuthorStatement.executeUpdate();
                }

                if (resultBookInsert > 0 && resultGenreInsert > 0 && resultAuthorInsert > 0) {
                    isBookAdded = true;
                }
                connection.commit();
            } else {
                connection.rollback();
            }
            return isBookAdded;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(e);
            } catch (SQLException e1) {
                throw new DAOException(e1);
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
        PreparedStatement st = null;
        PreparedStatement isBookInStorage = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            isBookInStorage = connection.prepareStatement(SQL_IS_BOOK_IN_STORAGE);
            isBookInStorage.setInt(1, bookId);
            ResultSet rs = isBookInStorage.executeQuery();
            if (rs.next()) {
                st = connection.prepareStatement(SQL_DELETE_BOOK);
                st.setInt(1, bookId);
                int res = st.executeUpdate();
                if (res > 0) {
                    isBookDeleted = true;
                }
            }

            return isBookDeleted;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addOrder(int bookId, String typeOfOrder, int librarianId, int libraryCard) throws DAOException {
        boolean isOrderAdded = false;
        Connection connection = null;
        PreparedStatement addOrderStatement = null;
        PreparedStatement changeBookStatus = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (typeOfOrder.equalsIgnoreCase(Location.SUBSCRIPTION.getName())) {
                addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_SUBSCRIPTION);
                changeBookStatus = connection.prepareStatement(SQL_BOOK_LOCATION_SUBSCRIPTION);

            } else if (typeOfOrder.equalsIgnoreCase(Location.READING_ROOM.getName())) {
                addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_READING_ROOM);
                changeBookStatus = connection.prepareStatement(SQL_BOOK_LOCATION_READING_ROOM);

            }
            addOrderStatement.setInt(1, libraryCard);
            addOrderStatement.setInt(2, bookId);
            addOrderStatement.setInt(3, librarianId);
            changeBookStatus.setInt(1, bookId);
            int resultOrderInsert = addOrderStatement.executeUpdate();

            int bookChangeLocationResult = changeBookStatus.executeUpdate();
            if (resultOrderInsert > 0 && bookChangeLocationResult > 0) {
                isOrderAdded = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isOrderAdded;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(e);
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }

        } finally {
            closeStatement(addOrderStatement);
            closeStatement(changeBookStatus);
            closeConnection(connection);
        }
    }

    @Override
    public List<Order> getUserOrders(int libraryCard) throws DAOException {
        LinkedList<Order> userOrders = new LinkedList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_USER_ORDERS);
            st.setInt(1, libraryCard);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                int orderId = Integer.parseInt(rs.getString("orders.id"));
                order.setId(orderId);
                User user = new User();
                int userLibraryCard = Integer.parseInt(rs.getString("user.library_card"));
                String userName = rs.getString("user.name");
                String userSurname = rs.getString("user.surname");
                String userPatronymic = rs.getString("user.patronymic");
                String userMobilePhone = rs.getString("user.mobile_phone");
                user.setId(userLibraryCard);
                user.setName(userName);
                user.setSurname(userSurname);
                user.setPatronymic(userPatronymic);
                user.setMobilePhone(userMobilePhone);
                order.setUser(user);
                Book book = new Book();
                int bookId = Integer.parseInt(rs.getString("book.id"));
                String bookTitle = rs.getString("book.title");
                String bookIsbn = rs.getString("book.isbn");
                book.setId(bookId);
                book.setTitle(bookTitle);
                book.setIsbn(bookIsbn);
                order.setBook(book);
                Timestamp lastOrderTimeStamp = rs.getTimestamp("orders.order_date");
                LocalDate lastOrderDate = lastOrderTimeStamp != null ? lastOrderTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setOrderDate(lastOrderDate);
                Timestamp expirationDateTimeStamp = rs.getTimestamp("orders.expiration_date");
                LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setExpirationDate(expirationDate);
                Timestamp returnDateTimeStamp = rs.getTimestamp("orders.return_date");
                LocalDate returnDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setReturnDate(returnDate);
                userOrders.add(order);
            }
            return userOrders;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean returnBook(int orderId, int bookId) throws DAOException {
        boolean isBookReturned = false;
        Connection connection = null;
        PreparedStatement returnBookStatement = null;
        PreparedStatement changeBookStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            returnBookStatement = connection.prepareStatement(SQL_RETURN_BOOK);
            returnBookStatement.setInt(1, orderId);
            int returnBookRes = returnBookStatement.executeUpdate();

            changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_STORAGE);
            changeBookStatusStatement.setInt(1, bookId);

            int changeBookStatusRes = changeBookStatusStatement.executeUpdate();
            if (returnBookRes > 0 && changeBookStatusRes > 0) {
                isBookReturned = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isBookReturned;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(e);
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }

        } finally {
            closeStatement(changeBookStatusStatement);
            closeStatement(returnBookStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean addOnlineOrder(int bookId, int userId) throws DAOException {
        boolean isOnlineOrderAdded = false;
        Connection connection = null;
        PreparedStatement addOnlineOrderStatement = null;
        PreparedStatement changeBookStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            addOnlineOrderStatement = connection.prepareStatement(SQL_ADD_ONLINE_ORDER);
            addOnlineOrderStatement.setInt(1, userId);
            addOnlineOrderStatement.setInt(2, bookId);
            int onlineOrderResult = addOnlineOrderStatement.executeUpdate();

            changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_ONLINE_ORDER);
            changeBookStatusStatement.setInt(1, bookId);

            int changeBookStatusRes = changeBookStatusStatement.executeUpdate();
            if (onlineOrderResult > 0 && changeBookStatusRes > 0) {
                isOnlineOrderAdded = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isOnlineOrderAdded;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(e);
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }

        } finally {
            closeStatement(changeBookStatusStatement);
            closeStatement(addOnlineOrderStatement);
            closeConnection(connection);
        }
    }

    @Override
    public List<Order> getUserOnlineOrders(int userId) throws DAOException {
        LinkedList<Order> userOrders = new LinkedList<>();
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_GET_USER_ONLINE_ORDERS);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                Book book = new Book();
                int ordersId = Integer.parseInt(rs.getString("online_orders.id"));
                int bookId = Integer.parseInt(rs.getString("book.id"));
                int lastId = !userOrders.isEmpty() ? userOrders.getLast().getId() : 0;
                if (lastId == ordersId) {
                    String authorName = rs.getString("authors.name");
                    String authorSurname = rs.getString("authors.surname");
                    String authorPatronymic = rs.getString("authors.patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book b = userOrders.getLast().getBook();
                    if (!b.getAuthors().contains(author)) {
                        b.addAuthor(author);
                    }
                    continue;
                }

                String bookTitle = rs.getString("book.title");
                String bookIsbn = rs.getString("book.isbn");
                String authorName = rs.getString("authors.name");
                String authorSurname = rs.getString("authors.surname");
                String authorPatronymic = rs.getString("authors.patronymic");
                Author author = new Author();
                author.setName(authorName);
                author.setSurname(authorSurname);
                author.setPatronymic(authorPatronymic);
                book.addAuthor(author);
                book.setId(bookId);
                book.setTitle(bookTitle);
                book.setIsbn(bookIsbn);
                OnlineOrder order = new OnlineOrder();
                String orderStatus = rs.getString("online_orders.order_status");
                order.setStatus(orderStatus);
                order.setBook(book);
                order.setId(ordersId);
                User user = new User();
                int userLibraryCard = Integer.parseInt(rs.getString("user.library_card"));
                user.setId(userLibraryCard);
                order.setUser(user);
                Timestamp lastOrderTimeStamp = rs.getTimestamp("online_orders.order_date");
                LocalDate lastOrderDate = lastOrderTimeStamp != null ? lastOrderTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setOrderDate(lastOrderDate);
                Timestamp expirationDateTimeStamp = rs.getTimestamp("online_orders.expiration_date");
                LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setExpirationDate(expirationDate);
                Timestamp returnDateTimeStamp = rs.getTimestamp("online_orders.order_execution_date");
                LocalDate executionDate = returnDateTimeStamp != null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() : null;
                order.setReturnDate(executionDate);
                userOrders.add(order);
            }
            return userOrders;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public boolean cancelOnlineOrder(int orderId, int bookId) throws DAOException {
        boolean isOnlineOrderCancelled = false;
        Connection connection = null;
        PreparedStatement cancelOnlineOrderStatement = null;
        PreparedStatement changeBookStatusStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            cancelOnlineOrderStatement = connection.prepareStatement(SQL_CANCEL_ONLINE_ORDER);
            cancelOnlineOrderStatement.setInt(1, orderId);
            int cancelOnlineOrderResult = cancelOnlineOrderStatement.executeUpdate();

            changeBookStatusStatement = connection.prepareStatement(SQL_BOOK_LOCATION_STORAGE);
            changeBookStatusStatement.setInt(1, bookId);

            int changeBookStatusRes = changeBookStatusStatement.executeUpdate();
            if (cancelOnlineOrderResult > 0 && changeBookStatusRes > 0) {
                isOnlineOrderCancelled = true;
                connection.commit();
            } else {
                connection.rollback();
            }
            return isOnlineOrderCancelled;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(e);
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }

        } finally {
            closeStatement(changeBookStatusStatement);
            closeStatement(cancelOnlineOrderStatement);
            closeConnection(connection);
        }
    }

    @Override
    public OnlineOrder onlineOrderStatus(int orderId) throws DAOException {
        OnlineOrder order = new OnlineOrder();
        Connection connection = null;
        PreparedStatement orderStatusStatement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();

            orderStatusStatement = connection.prepareStatement(SQL_ONLINE_ORDER_STATUS);
            orderStatusStatement.setInt(1, orderId);

            ResultSet rs = orderStatusStatement.executeQuery();
            rs.next();
            String orderStatus = rs.getString("online_orders.order_status");
            order.setStatus(orderStatus);

            return order;
        } catch (SQLException e) {
            throw new DAOException(e);

        } finally {
            closeStatement(orderStatusStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean executeOnlineOrder(int onlineOrderId, String typeOfOrder, int bookId, int userId, int librarianId) throws DAOException {
        boolean isOnlineOrderExecuted = false;
        Connection connection = null;
        PreparedStatement executeOnlineOrderStatement = null;
        PreparedStatement addOrderStatement = null;
        PreparedStatement changeBookStatus = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            executeOnlineOrderStatement = connection.prepareStatement(SQL_EXECUTE_ONLINE_ORDER);
            executeOnlineOrderStatement.setInt(1, onlineOrderId);
            int executeOnlineOrderResult = executeOnlineOrderStatement.executeUpdate();


            if (executeOnlineOrderResult > 0) {




                if (typeOfOrder.equals(Location.SUBSCRIPTION.getName())) {
                    addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_SUBSCRIPTION);
                    changeBookStatus = connection.prepareStatement(SQL_BOOK_LOCATION_SUBSCRIPTION);

                } else if (typeOfOrder.equalsIgnoreCase(Location.READING_ROOM.getName())) {
                    addOrderStatement = connection.prepareStatement(SQL_ADD_ORDER_ON_READING_ROOM);
                    changeBookStatus = connection.prepareStatement(SQL_BOOK_LOCATION_READING_ROOM);

                }
                addOrderStatement.setInt(1, userId);
                addOrderStatement.setInt(2, bookId);
                addOrderStatement.setInt(3, librarianId);
                changeBookStatus.setInt(1, bookId);

                int orderResultInsert = addOrderStatement.executeUpdate();
                int bookChangeLocationResult = changeBookStatus.executeUpdate();
                if (orderResultInsert > 0 && bookChangeLocationResult > 0) {
                    isOnlineOrderExecuted = true;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
            return isOnlineOrderExecuted;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(e);
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }

        } finally {

            closeStatement(executeOnlineOrderStatement);
            closeStatement(changeBookStatus);
            closeStatement(addOrderStatement);
            closeConnection(connection);
        }
    }
}
