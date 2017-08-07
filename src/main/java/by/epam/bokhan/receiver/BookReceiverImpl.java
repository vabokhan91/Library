package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.BookDAO;
import by.epam.bokhan.dao.BookDAOImpl;
import by.epam.bokhan.dao.UserDAOImpl;
import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.util.Date;
import java.util.LinkedList;
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

    public void getExplicitBookInfo(RequestContent requestContent)throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        List<Book> books;
        int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
        try {
            books = dao.getExplicitBookInfo(bookId);
            requestContent.insertParameter("foundBook", books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getBookForEditing(RequestContent requestContent)throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        List<Book> books;
        int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
        try {
            books = dao.getBookForEditing(bookId);
            requestContent.insertParameter("foundBook", books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
    @Override
    public void getGenres(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        LinkedList<Genre> genres;
        try {
            genres = bookDAO.getAllGenres();
            requestContent.insertParameter("genres", genres);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void editBook(RequestContent requestContent) throws ReceiverException{
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookEdited;
        try{
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
            String title =(String) requestContent.getRequestParameters().get("book_title");
            int pages = Integer.parseInt((String) requestContent.getRequestParameters().get("book_pages"));
            String isbn = (String) requestContent.getRequestParameters().get("book_isbn");
            int year = Integer.parseInt((String) requestContent.getRequestParameters().get("book_year"));
            isBookEdited = bookDAO.editBook(bookId, title,pages,year,isbn);
            requestContent.insertParameter("isBookEdited", isBookEdited);
        }catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void addAuthor(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean authorIsAdded;
        try{
            String name =(String) requestContent.getRequestParameters().get("author_name");
            String surname = (String) requestContent.getRequestParameters().get("author_surname");
            String patronymic = (String) requestContent.getRequestParameters().get("author_pathonymic");
            String dateOfBirth = (String) requestContent.getRequestParameters().get("date_of_birth");
            authorIsAdded = bookDAO.addAuthor(name,surname,patronymic, dateOfBirth);
            requestContent.insertParameter("isAuthorAdded", authorIsAdded);
        }catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getGenresAuthorsPublishers(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        LinkedList<Genre> genres;
        LinkedList<Author> authors;
        LinkedList<Publisher> publishers;
        try {
            genres = bookDAO.getAllGenres();
            requestContent.insertParameter("genres", genres);
            authors = bookDAO.getAllAuthors();
            requestContent.insertParameter("authors", authors);
            publishers = bookDAO.getAllPublishers();
            requestContent.insertParameter("publishers", publishers);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookAdded;
        try{
            String title =(String) requestContent.getRequestParameters().get("book_title");
            int pages = Integer.parseInt((String)requestContent.getRequestParameters().get("book_pages")) ;
            int yearOfPublishing = Integer.parseInt((String)requestContent.getRequestParameters().get("book_year"));
            String isbn = (String) requestContent.getRequestParameters().get("book_isbn");
            int publisherId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_publisher"));
            int genreId = (Integer.parseInt((String) requestContent.getRequestParameters().get("book_genre")));
            int authorId = Integer.parseInt((String)requestContent.getRequestParameters().get("book_author"));
            String description = (String) requestContent.getRequestParameters().get("book_description");
            Location location = Location.valueOf(((String) requestContent.getRequestParameters().get("book_location")).toUpperCase());
            Book book = new Book();
            book.setTitle(title);
            book.setPages(pages);
            book.setYear(yearOfPublishing);
            book.setIsbn(isbn);

            book.setDescription(description);
            book.setLocation(location);
            isBookAdded = bookDAO.addBook(book,publisherId, genreId,authorId);
            requestContent.insertParameter("isBookAdded", isBookAdded);
        }catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void deleteBook(RequestContent requestContent) throws ReceiverException{
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookDeleted;
        try{
            int bookId = Integer.parseInt((String)requestContent.getRequestParameters().get("book_id"));
            isBookDeleted = bookDAO.deleteBook(bookId);
            requestContent.insertParameter("isBookDeleted", isBookDeleted);
        }catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
