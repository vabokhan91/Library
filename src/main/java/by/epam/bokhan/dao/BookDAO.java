package by.epam.bokhan.dao;

import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;

import java.util.List;


public interface BookDAO {
    /**
     * Gets all books from database
     *
     * @return List with found books
     * @throws DAOException
     */
    List<Book> getAllBooks() throws DAOException;

    /**
     * Gets book by id
     *
     * @param bookId book id
     * @return List with found books
     * @throws DAOException
     */
    List<Book> getBookById(int bookId) throws DAOException;

    /**
     * Gets book by title
     *
     * @param title book title
     * @return List with found books
     * @throws DAOException
     */
    List<Book> getBookByTitle(String title) throws DAOException;

    /**
     * Gets book with explicit information
     *
     * @param bookId book id
     * @return found book
     * @throws DAOException
     */
    Book getExplicitBookInfo(int bookId) throws DAOException;

    /**
     * Gets last order of the book
     *
     * @param bookId book id
     * @return order of the book
     * @throws DAOException
     */
    Order getLastOrderOfBook(int bookId) throws DAOException;

    /**
     * Gets all genres
     *
     * @return List with found genres
     * @throws DAOException
     */
    List<Genre> getAllGenres() throws DAOException;

    /**
     * Edits book
     *
     * @param book book that will be edited
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean editBook(Book book) throws DAOException;

    /**
     * Adds new author
     *
     * @param author author that will be added
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean addAuthor(Author author) throws DAOException;

    /**
     * Gets all authors from database
     *
     * @return List with found authors
     * @throws DAOException
     */
    List<Author> getAllAuthors() throws DAOException;

    /**
     * Gets all publishers from database
     *
     * @return List with found publishers
     * @throws DAOException
     */
    List<Publisher> getAllPublishers() throws DAOException;

    /**
     * Adds new book to database
     *
     * @param book book that will be added
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean addBook(Book book) throws DAOException;

    /**
     * Deletes book from database
     *
     * @param bookId book id
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean deleteBookById(int bookId) throws DAOException;

    /**
     * Adds new order to database
     *
     * @param order       order that will be added
     * @param typeOfOrder type of the order
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean addOrder(Order order, String typeOfOrder) throws DAOException;

    /**
     * Returns book to library
     *
     * @param orderId order id
     * @param bookId  book id
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean returnBook(int orderId, int bookId) throws DAOException;

    /**
     * Adds new publisher to database
     *
     * @param publisher publisher that will be added
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean addPublisher(Publisher publisher) throws DAOException;

    /**
     * Adds new genre to database
     *
     * @param genre genre that will be added
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean addGenre(Genre genre) throws DAOException;

    /**
     * Deletes genre from database
     *
     * @param genres genres that will be deleted
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean deleteGenre(List<Genre> genres) throws DAOException;

    /**
     * Deletes author from database
     *
     * @param authors authors that will be deleted
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean deleteAuthor(List<Author> authors) throws DAOException;

    /**
     * Deletes publisher from database
     *
     * @param publishers publishers that will be deleted
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean deletePublisher(List<Publisher> publishers) throws DAOException;

    /**
     * Adds new online order to database
     *
     * @param bookId      book id
     * @param libraryCard library card number
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean addOnlineOrder(int bookId, int libraryCard) throws DAOException;

    /**
     * Cancels online order
     *
     * @param orderId id of the canceled order
     * @param bookId  book id
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean cancelOnlineOrder(int orderId, int bookId) throws DAOException;

    /**
     * Gets online order status
     *
     * @param orderId id of the online order
     * @return status of the order
     * @throws DAOException
     */
    OrderStatus onlineOrderStatus(int orderId) throws DAOException;

    /**
     * Executes online order
     *
     * @param onlineOrder online order that will be executed
     * @param typeOfOrder type of the order
     * @return boolean, depending on operation result
     * @throws DAOException
     */
    boolean executeOnlineOrder(OnlineOrder onlineOrder, String typeOfOrder) throws DAOException;

    /**
     * Gets books from database by their genre
     * @param genre genre of the book
     * @return List of the found books
     * @throws DAOException
     */
    List<Book> getBooksByGenre(Genre genre) throws DAOException;

    /**
     * Gets random books from database
     * @param numberOfBooks number of returned books
     * @return List of the found books
     * @throws DAOException
     */
    List<Book> getRandomBooks(int numberOfBooks) throws DAOException;
}
