package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.entity.Book;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.util.List;


public interface BookReceiver extends Receiver {
    void getAllBooks(RequestContent requestContent) throws ReceiverException;

    void getExplicitBookInfo(RequestContent requestContent) throws ReceiverException;

    void findBook(RequestContent requestContent) throws ReceiverException;

    void getBookForEditing(RequestContent requestContent) throws ReceiverException;

    void editBook(RequestContent requestContent) throws ReceiverException;

    void addAuthor(RequestContent requestContent) throws ReceiverException;

    void getGenresAuthorsPublishers(RequestContent requestContent) throws ReceiverException;

    void addBook(RequestContent requestContent) throws ReceiverException;

    void deleteBook(RequestContent requestContent) throws ReceiverException;

    void addOrder(RequestContent requestContent) throws ReceiverException;

    void getUserOrders(RequestContent requestContent) throws ReceiverException;

    void returnBook(RequestContent requestContent) throws ReceiverException;

    void addPublisher(RequestContent requestContent) throws ReceiverException;

    void addGenre(RequestContent requestContent) throws ReceiverException;

    void getAllGenres(RequestContent requestContent) throws ReceiverException;

    void deleteGenre(RequestContent requestContent) throws ReceiverException;

    void getAllAuthors(RequestContent requestContent) throws ReceiverException;

    void deleteAuthor(RequestContent requestContent) throws ReceiverException;

    void getAllPublishers(RequestContent requestContent) throws ReceiverException;

    void deletePublisher(RequestContent requestContent) throws ReceiverException;

    void addOnlineOrder(RequestContent requestContent) throws ReceiverException;

    void getUserOnlineOrders(RequestContent requestContent) throws ReceiverException;

    void cancelOnlineOrder(RequestContent requestContent) throws ReceiverException;

    void executeOnlineOrder(RequestContent requestContent) throws ReceiverException;

    void findBookByGenre(RequestContent requestContent) throws ReceiverException;

}
