package by.epam.bokhan.dao;

import by.epam.bokhan.entity.Book;
import by.epam.bokhan.exception.DAOException;

import java.util.List;

/**
 * Created by vbokh on 01.08.2017.
 */
public interface BookDAO {
    List<Book> getAllBooks() throws DAOException;

    List<Book> findBookById(int bookId) throws DAOException;

    List<Book> findBookByTitle(String title) throws DAOException;
}
