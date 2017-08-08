package by.epam.bokhan.dao;

import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vbokh on 01.08.2017.
 */
public interface BookDAO {
    List<Book> getAllBooks() throws DAOException;

    List<Book> findBookById(int bookId) throws DAOException;

    List<Book> findBookByTitle(String title) throws DAOException;

    List<Book> getExplicitBookInfo(int bookId) throws DAOException;


    List<Order> getBooksLastOrder(int bookId) throws DAOException;

    List<Book> getBookForEditing(int bookId) throws DAOException;

    LinkedList<Genre> getAllGenres() throws DAOException;

    boolean editBook(int bookId, String title, int pages, int year, String isbn) throws DAOException;

    boolean addAuthor(String name, String surname, String patronymic, String dateOfBirth) throws DAOException;

    LinkedList<Author> getAllAuthors() throws DAOException;

    LinkedList<Publisher> getAllPublishers() throws DAOException;

    boolean addBook(Book book,int publisherId, int genreId, int authorId) throws DAOException;

    boolean deleteBook(int bookId) throws DAOException;

    boolean addOrder(int bookId, String typeOfOrder, int librarianId, int libraryCard) throws DAOException;
}
