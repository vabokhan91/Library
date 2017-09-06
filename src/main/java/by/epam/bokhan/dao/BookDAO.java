package by.epam.bokhan.dao;

import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;

import java.util.List;


public interface BookDAO {
    /*Gets all books from database*/
    List<Book> getAllBooks() throws DAOException;

    /*Gets book by id*/
    List<Book> getBookById(int bookId) throws DAOException;

    /*Gets book by title*/
    List<Book> getBookByTitle(String title) throws DAOException;

   /*Gets book with explicit information*/
    Book getExplicitBookInfo(int bookId) throws DAOException;

   /*Gets last order of the book*/
    Order getLastOrderOfBook(int bookId) throws DAOException;

    /*Gets all genres*/
    List<Genre> getAllGenres() throws DAOException;

    /*Edits book*/
    boolean editBook(Book book) throws DAOException;

    /*Adds new author*/
    boolean addAuthor(Author author) throws DAOException;

    /*Gets all authors from database*/
    List<Author> getAllAuthors() throws DAOException;

    /*Gets all publishers from database*/
    List<Publisher> getAllPublishers() throws DAOException;

    /*Adds new book to database*/
    boolean addBook(Book book) throws DAOException;

    /*Deletes book from database*/
    boolean deleteBookById(int bookId) throws DAOException;

    /*Adds new order to database*/
    boolean addOrder(Order order, String typeOfOrder) throws DAOException;

   /*Returns book to library*/
    boolean returnBook(int orderId, int bookId) throws DAOException;

    /*Adds new publisher to database*/
    boolean addPublisher(Publisher publisher) throws DAOException;

    /*Adds new genre to database*/
    boolean addGenre(Genre genre) throws DAOException;

    /*Deletes genre from database*/
    boolean deleteGenre(List<Genre> genres) throws DAOException;

   /*Deletes author from database*/
    boolean deleteAuthor(List<Author> authors) throws DAOException;

    /*Deletes publisher from database*/
    boolean deletePublisher(List<Publisher> publishers) throws DAOException;

    /*Adds new online order to database*/
    boolean addOnlineOrder(int bookId, int libraryCard) throws DAOException;

    /*Cancels online order*/
    boolean cancelOnlineOrder(int orderId, int bookId) throws DAOException;

    /*Gets online order status*/
    OrderStatus onlineOrderStatus(int orderId) throws DAOException;

    /*Executes online order*/
    boolean executeOnlineOrder(OnlineOrder onlineOrder, String typeOfOrder) throws DAOException;

    /*Gets books from database by their genre*/
    List<Book> getBooksByGenre(Genre genre) throws DAOException;

    /*Gets random books from database*/
    List<Book> getRandomBooks(int numberOfBooks) throws DAOException;
}
