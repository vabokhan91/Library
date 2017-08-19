package by.epam.bokhan.dao;

import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;

import java.util.LinkedList;
import java.util.List;


public interface BookDAO {
    List<Book> getAllBooks() throws DAOException;

    List<Book> findBookById(int bookId) throws DAOException;

    List<Book> findBookByTitle(String title) throws DAOException;

    List<Book> getExplicitBookInfo(int bookId) throws DAOException;

    List<Order> getBooksLastOrder(int bookId) throws DAOException;

    List<Book> getBookForEditing(int bookId) throws DAOException;

    List<Genre> getAllGenres() throws DAOException;

    boolean editBook(Book book) throws DAOException;

    boolean addAuthor(Author author) throws DAOException;

    List<Author> getAllAuthors() throws DAOException;

    List<Publisher> getAllPublishers() throws DAOException;

    boolean addBook(Book book) throws DAOException;

    boolean deleteBook(int bookId) throws DAOException;

    boolean addOrder(Book book, String typeOfOrder) throws DAOException;

    List<Order> getUserOrders(int libraryCard) throws DAOException;

    boolean returnBook(int orderId, int bookId) throws DAOException;

    boolean addPublisher(String publisherName) throws DAOException;

    boolean addGenre(Genre genre) throws DAOException;

    boolean deleteGenre(List<Genre> genres) throws DAOException;

    boolean deleteAuthor(List<Author> authors) throws DAOException;

    boolean deletePublisher(List<Publisher> publishers) throws DAOException;

    boolean addOnlineOrder(int bookId, int libraryCard) throws DAOException;

    List<Order> getUserOnlineOrders(int libraryCard) throws DAOException;

    boolean cancelOnlineOrder(int orderId, int bookId) throws DAOException;

    OnlineOrder onlineOrderStatus(int orderId) throws DAOException;

    boolean executeOnlineOrder(OnlineOrder onlineOrder,String typeOfOrder) throws DAOException;

    List<Book> getBooksByGenre(Genre genre) throws DAOException;
}
