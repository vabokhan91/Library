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
    private static final String SQL_GET_ALL_BOOKS = "SELECT book.id, book.title, book.pages,book.isbn, book.year, book.description, book.location, publisher.name as publisher,\n" +
            "genres.genre_name, authors.author_name, authors.author_surname, authors.author_patronymic\n" +
            "from book\n" +
            "left join publisher on book.publisher_id = publisher.id\n" +
            "left join (select book_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            "left join (select book_id, author_id, author.name as author_name, author.surname as author_surname,author.patronymic as author_patronymic \n" +
            "\tfrom book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = authors.book_id";
    private static final String SQL_FIND_BOOK_BY_ID = "SELECT book.id, book.title,book.pages,book.isbn, book.location, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.isbn, book.year, publisher.name as publisher\n" +
            " from book \n" +
            " left join publisher on book.publisher_id = publisher.id\n" +
            " left join (select book_author.book_id as b,author.name, author.surname, author.patronymic from book_author left join author on book_author.author_id = author.id) as authors\n" +
            " on book.id = b\n" +
            "left join (select book_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            "\n" +
            " where book.id = ?";

    private static final String SQL_FIND_BOOK_BY_TITLE = "SELECT book.id, book.title,book.pages,genres.genre_name,book.location, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.isbn, book.year, publisher.name as publisher\n" +
            "from book  \n" +
            "left join publisher on book.publisher_id = publisher.id \n" +
            "left join (select book_author.book_id as b,author.name , author.surname , author.patronymic  from book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = b \n" +
            "left join (select book_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres \n" +
            "on book.id = genres.book_id\n" +
            "\n" +
            "where book.title LIKE ?";

    private static final String SQL_GET_EXPLICIT_BOOK_INFO = "SELECT book.id, book.title,book.pages, book.location, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, \n" +
            "book.isbn, book.year, publisher.name as publisher,\n" +
            "userorder.last_order_date , userorder.expiration_date, userorder.return_date , userorder.library_card , userorder.user_name , userorder.user_surname , userorder.user_patronymic , userorder.mobile_phone\n" +
            "from book \n" +
            "left join publisher on book.publisher_id = publisher.id\n" +
            "left join (select book_author.book_id as b,author.name, author.surname, author.patronymic from book_author left join author on book_author.author_id = author.id) as authors\n" +
            "on book.id = b\n" +
            "left join (select book_id, genre.name as genre_name from book_genre left join genre on book_genre.genre_id = genre.id) as genres\n" +
            "on book.id = genres.book_id\n" +
            "left join (select orders.id, orders.book_id as bookid, orders.order_date as last_order_date, orders.expiration_date as expiration_date, orders.return_date as return_date,\n" +
            "\t\t\tuser.library_card as library_card, user.name as user_name, user.surname as user_surname, user.patronymic as user_patronymic, user.mobile_phone as mobile_phone \n" +
            "\t\t\t\tfrom orders left join user \n" +
            "\t\t\t\ton orders.user_id = user.library_card where orders.order_date = ( Select max(orders.order_date) as last_order from orders where user_id = library_card)) as userorder\n" +
            "\ton book.id = bookid\n" +
            "where book.id = ?";
    private static final String SQL_GET_ALL_GENRES = "Select * from genre";
    private static final String SQL_EDIT_BOOK = "Update book set title = ?, pages = ?, isbn = ?, year = ? where id = ?";
    private static final String SQL_ADD_AUTHOR = "INSERT INTO author (author.name, author.surname, author.patronymic, author.date_of_birth) VALUES (?,?,?,?)";
    private static final String SQL_GET_ALL_AUTHORS = "Select * from author";
    private static final String SQL_GET_ALL_PUBLISHERS = "Select * from publisher";
    private static final String SQL_ADD_BOOK = "INSERT INTO book (book.title, book.pages,book.isbn, book.year,book.publisher_id,book.description,book.location) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_ADD_BOOK_GENRE = "INSERT INTO book_genre (book_id,genre_id) VALUES (?,?)";
    private static final String SQL_ADD_BOOK_AUTHOR = "INSERT INTO book_author (book_id,author_id) VALUES (?,?)";
    private static final String SQL_DELETE_BOOK = "DELETE FROM book where book.id = ?";


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
                Genre genre;
                int bookId = rs.getInt("book.id");
                int lastId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastId == bookId) {
                    String g = rs.getString("genre_name");
                    if (g != null && !g.isEmpty()) {
                        genre = Genre.valueOf(g.toUpperCase());
                        if (!books.get(lastId - 1).getGenre().contains(genre)) {
                            books.get(lastId - 1).addGenre(genre);
                        }

                    }
                    String authorName = rs.getString("author_name");
                    String authorSurname = rs.getString("author_surname");
                    String authorPatronymic = rs.getString("author_patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    Book b = books.get(lastId - 1);
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
                book.setPublisher(rs.getString("publisher"));
                String g = rs.getString("genre_name");
                if (g != null && !g.isEmpty()) {
                    genre = Genre.valueOf(g.toUpperCase());
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
                book.setPublisher(rs.getString("publisher"));
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
            st.setString(1, "%" +title + "%");
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
                book.setPublisher(rs.getString("publisher"));
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
                    if (g != null && !g.isEmpty()) {
                        genre = Genre.valueOf(g.toUpperCase());
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
                book.setPublisher(rs.getString("publisher"));
                String g = rs.getString("genre_name");
                if (g != null && !g.isEmpty()) {
                    genre = Genre.valueOf(g.toUpperCase());
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
                Order order = new Order();
                Timestamp lastOrderTimeStamp = rs.getTimestamp("last_order_date");
                LocalDate lastOrderDate = lastOrderTimeStamp != null ? lastOrderTimeStamp.toLocalDateTime().toLocalDate():null;
                order.setOrderDate(lastOrderDate);
                Timestamp expirationDateTimeStamp = rs.getTimestamp("expiration_date");
                LocalDate expirationDate = expirationDateTimeStamp != null ? expirationDateTimeStamp.toLocalDateTime().toLocalDate():null;
                order.setExpirationDate(expirationDate);
                Timestamp returnDateTimeStamp = rs.getTimestamp("return_date");
                LocalDate returnDate = returnDateTimeStamp !=null ? returnDateTimeStamp.toLocalDateTime().toLocalDate() :null;
                order.setReturnDate(returnDate);
                User user = new User();
                user.setName(rs.getString("user_name"));
                user.setSurname(rs.getString("user_surname"));
                user.setPatronymic(rs.getString("user_patronymic"));
                user.setMobilePhone(rs.getString("mobile_phone"));
                user.setId(rs.getInt("library_card"));
                order.setUser(user);
                book.addOrder(order);
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

    public List<Book> getBookForEditing(int bookId) throws DAOException {
        LinkedList<Book> books = new LinkedList<>();
        Book book;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_BOOK_BY_ID);
            st.setInt(1, bookId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                book = new Book();
                Genre genre;
                int bId = rs.getInt("book.id");
                int lastId = !books.isEmpty() ? books.getLast().getId() : 0;
                if (lastId == bId) {
                    String g = rs.getString("genre_name");
                    if (g != null && !g.isEmpty()) {
                        genre = Genre.valueOf(g.toUpperCase());
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
                String g = rs.getString("genre_name");
                if (g != null && !g.isEmpty()) {
                    genre = Genre.valueOf(g.toUpperCase());
                    book.addGenre(genre);
                }
                book.setIsbn(rs.getString("book.isbn"));
                book.setPublisher(rs.getString("publisher"));
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

    public LinkedList<Genre> getAllGenres() throws DAOException{
        LinkedList<Genre> genres = new LinkedList<>();
        Connection connection = null;
        Statement st = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_GET_ALL_GENRES);
            while (rs.next()) {
                Genre genre = Genre.valueOf(rs.getString("name").toUpperCase());
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

    public boolean editBook(int bookId, String title, int pages, int year, String isbn) throws DAOException{
        boolean isBookEdited = false;
        Connection connection = null;
        PreparedStatement st = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_EDIT_BOOK);
            st.setString(1, title);
            st.setInt(2, pages);
            st.setString(3, isbn);
            st.setInt(4, year);
            st.setInt(5, bookId);
            int res = st.executeUpdate();
            if (res > 0) {
                isBookEdited = true;
            }
            return isBookEdited;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    public boolean addAuthor(String name, String surname, String patronymic, String dateOfBirth) throws DAOException {
        boolean isAuthorAdded = false;
        Connection connection = null;
        PreparedStatement st = null;
        try{
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
        }
        finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }

    @Override
    public LinkedList<Author> getAllAuthors() throws DAOException {
        LinkedList<Author> authors = new LinkedList<>();
        Connection connection = null;
        Statement st = null;
        try{
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
            Collections.sort(authors, Comparator.comparing(a-> a.getSurname()));
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
        try{
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
            Collections.sort(publishers, Comparator.comparing(a->a.getName()));
            return publishers;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }
    @Override
    public boolean addBook(Book book,int publisherId, int genreId, int authorId) throws DAOException{
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
        try{
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
                addGenreStatement.setInt(1,lastBookId);
                addGenreStatement.setInt(2,genreId);
                int resultGenreInsert = addGenreStatement.executeUpdate();
                addAuthorStatement = connection.prepareStatement(SQL_ADD_BOOK_AUTHOR);
                addAuthorStatement.setInt(1,lastBookId);
                addAuthorStatement.setInt(2,authorId);
                int resultAuthorInsert = addAuthorStatement.executeUpdate();
                if (resultBookInsert > 0 && resultGenreInsert > 0 && resultAuthorInsert > 0) {
                    isBookAdded = true;
                }
                connection.commit();
            }else {
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
        try{
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_DELETE_BOOK);
            st.setInt(1, bookId);
            int res = st.executeUpdate();
            if (res > 0) {
                isBookDeleted = true;
            }
            return isBookDeleted;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }
}
