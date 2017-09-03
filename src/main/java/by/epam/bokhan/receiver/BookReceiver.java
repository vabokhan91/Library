package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;


public interface BookReceiver extends Receiver {

    /**
     * Gets all books
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getAllBooks(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets explicit information about book
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getExplicitBookInfo(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets book
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getBook(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets book by title
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getBookByTitle(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets book for editing
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getBookForEditing(RequestContent requestContent) throws ReceiverException;

    /**
     * Edits book
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void editBook(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds new author
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void addAuthor(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds all authors, genres and publishers
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getGenresAuthorsPublishers(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds book
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void addBook(RequestContent requestContent) throws ReceiverException;

    /**
     * Deletes book
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void deleteBook(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds new order
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void addOrder(RequestContent requestContent) throws ReceiverException;

    /**
     * Returns book
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void returnBook(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds new publisher
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void addPublisher(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds new genre
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void addGenre(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets all genres
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getAllGenres(RequestContent requestContent) throws ReceiverException;

    /**
     * Deletes genre
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void deleteGenre(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets all authors
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getAllAuthors(RequestContent requestContent) throws ReceiverException;

    /**
     * Deletes author
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void deleteAuthor(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets all publishers
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getAllPublishers(RequestContent requestContent) throws ReceiverException;

    /**
     * Deleter all publishers
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void deletePublisher(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds new online order
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void addOnlineOrder(RequestContent requestContent) throws ReceiverException;

    /**
     * Cancels online order
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void cancelOnlineOrder(RequestContent requestContent) throws ReceiverException;

    /**
     * Executes online order
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void executeOnlineOrder(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets book by genre
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getBookByGenre(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets arbitrary books
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getRandomBooks(RequestContent requestContent) throws ReceiverException;
}
