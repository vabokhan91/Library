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

    LinkedList<Genre> getAllGenres() throws DAOException;

    boolean editBook(Book book) throws DAOException;

    boolean addAuthor(Author author) throws DAOException;

    LinkedList<Author> getAllAuthors() throws DAOException;

    LinkedList<Publisher> getAllPublishers() throws DAOException;

    boolean addBook(Book book,int publisherId, int genreId[], int[] authorId) throws DAOException;

    boolean deleteBook(int bookId) throws DAOException;

    boolean addOrder(int bookId, String typeOfOrder, int librarianId, int libraryCard) throws DAOException;

    List<Order> getUserOrders(int libraryCard) throws DAOException;

    boolean returnBook(int orderId, int bookId) throws DAOException;

    boolean addPublisher(String publisherName) throws DAOException;

    boolean addGenre(String genreName) throws DAOException;

    boolean deleteGenre(int[] genreIds) throws DAOException;

    boolean deleteAuthor(int[] authorIds) throws DAOException;

    boolean deletePublisher(List<Publisher> publishers) throws DAOException;

    boolean addOnlineOrder(int bookId, int libraryCard) throws DAOException;

    List<Order> getUserOnlineOrders(int libraryCard) throws DAOException;

    boolean cancelOnlineOrder(int orderId, int bookId) throws DAOException;

    OnlineOrder onlineOrderStatus(int orderId) throws DAOException;

    boolean executeOnlineOrder(int onlineOrderId,String typeOfOrder, int bookId, int libraryCard, int librarianId) throws DAOException;

    List<Book> getBooksByGenre(Genre genre) throws DAOException;
}
