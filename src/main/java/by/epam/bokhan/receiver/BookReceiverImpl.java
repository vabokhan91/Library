package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.BookDAO;
import by.epam.bokhan.dao.BookDAOImpl;
import by.epam.bokhan.dao.UserDAOImpl;
import by.epam.bokhan.entity.Book;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.util.List;

/**
 * Created by vbokh on 01.08.2017.
 */
public class BookReceiverImpl implements BookReceiver {

    @Override
    public void getAllBooks(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        try {
            List<Book> books = bookDAO.getAllBooks();
            requestContent.insertParameter("books", books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }


    public void findBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        int bookId;
        String title;
        List<Book> books = null;
        String typeOfSearch = (String) requestContent.getRequestParameters().get("type_of_search");
        try {
            if (typeOfSearch.equalsIgnoreCase("by_id")) {
                bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("find_query_value"));
                books = bookDAO.findBookById(bookId);
            } else if (typeOfSearch.equalsIgnoreCase("by_title")) {
                title = (String) requestContent.getRequestParameters().get("find_query_value");
                books = bookDAO.findBookByTitle(title);
            }
            requestContent.insertParameter("foundBook", books);
        } catch (DAOException e) {
            requestContent.insertParameter("foundBook", null);
            throw new ReceiverException(String.format("Can not find book. Reason : %s", e.getMessage()), e);
        }
    }
}
