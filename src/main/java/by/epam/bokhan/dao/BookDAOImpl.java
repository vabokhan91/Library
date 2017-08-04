package by.epam.bokhan.dao;

import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.pool.ConnectionPool;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private static final String SQL_FIND_BOOK_BY_ID = "SELECT book.id, book.title,book.pages, book.location, genres.genre_name, authors.name as author_name, authors.surname as author_surname, authors.patronymic as author_patronymic, book.isbn, book.year, publisher.name as publisher\n" +
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
            st.setString(1, title + "%");
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


        /*try {
            connection = ConnectionPool.getInstance().getConnection();
            st = connection.prepareStatement(SQL_FIND_BOOK_BY_TITLE);

            st.setString(1, title + "%");
            ResultSet rs = st.executeQuery();
            book = new Book();
            while (rs.next()) {
                String bookTitle = rs.getString("book.title");
                if (bookTitle == title) {
                    String authorName = rs.getString("author_name");
                    String authorSurname = rs.getString("author_surname");
                    String authorPatronymic = rs.getString("author_patronymic");
                    Author author = new Author();
                    author.setName(authorName);
                    author.setSurname(authorSurname);
                    author.setPatronymic(authorPatronymic);
                    if (!book.getAuthors().contains(author)) {
                        book.addAuthor(author);
                    }
                    continue;
                }
                book.setId(rs.getInt("book.id"));
                book.setTitle(bookTitle);
                book.setYear(rs.getInt("book.year"));
                book.setPages(rs.getInt("book.pages"));
                book.setIsbn(rs.getString("book.isbn"));
                book.setPublisher(rs.getString("publisher"));
            }*/
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
            closeConnection(connection);
        }
    }
}
