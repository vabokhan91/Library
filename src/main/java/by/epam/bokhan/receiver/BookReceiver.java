package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.entity.Book;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.util.List;

/**
 * Created by vbokh on 01.08.2017.
 */
public interface BookReceiver extends Receiver {
    void getAllBooks(RequestContent requestContent) throws ReceiverException;

    void getExplicitBookInfo(RequestContent requestContent) throws ReceiverException;

    void findBook(RequestContent requestContent) throws ReceiverException;

    void getBookForEditing(RequestContent requestContent) throws ReceiverException;

    void getGenres(RequestContent requestContent) throws ReceiverException;

    void editBook(RequestContent requestContent) throws ReceiverException;

    void addAuthor(RequestContent requestContent) throws ReceiverException;

    void getGenresAuthorsPublishers(RequestContent requestContent) throws ReceiverException;

    void addBook(RequestContent requestContent) throws ReceiverException;

    void deleteBook(RequestContent requestContent) throws ReceiverException;

    void addOrder(RequestContent requestContent) throws ReceiverException;

    void getUserOrders(RequestContent requestContent) throws ReceiverException;

    void returnBook(RequestContent requestContent) throws ReceiverException;
}
