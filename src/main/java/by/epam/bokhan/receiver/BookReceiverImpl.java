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

    public void getExplicitBookInfo(RequestContent requestContent) throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        List<Book> books;
        List<Order> orders;
        int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
        try {
            books = dao.getExplicitBookInfo(bookId);
            orders = dao.getBooksLastOrder(bookId);
            requestContent.insertParameter("foundBook", books);
            requestContent.insertParameter("foundOrder", orders);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void getBookForEditing(RequestContent requestContent) throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        List<Book> books;
        List<Genre> genres;
        List<Author> authors;
        List<Publisher> publishers;
        int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
        try {
            books = dao.getBookForEditing(bookId);
            requestContent.insertParameter("foundBook", books);
            genres = dao.getAllGenres();
            requestContent.insertParameter("genres", genres);
            authors = dao.getAllAuthors();
            requestContent.insertParameter("authors", authors);
            publishers = dao.getAllPublishers();
            requestContent.insertParameter("publishers", publishers);

        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getAllGenres(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        LinkedList<Genre> genres;
        try {
            genres = bookDAO.getAllGenres();
            requestContent.insertParameter("genres", genres);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void editBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookEdited;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
            String title = (String) requestContent.getRequestParameters().get("book_title");
            int pages = Integer.parseInt((String) requestContent.getRequestParameters().get("book_pages"));
            String isbn = (String) requestContent.getRequestParameters().get("book_isbn");
            int year = Integer.parseInt((String) requestContent.getRequestParameters().get("book_year"));
            Book book = new Book();
            book.setId(bookId);
            book.setTitle(title);
            book.setPages(pages);
            book.setIsbn(isbn);
            book.setYear(year);
            String[] genreIdStrings = (String[]) requestContent.getRequestParameterValues().get("book_genre");
            int[] genreId = new int[genreIdStrings.length];
            for(int i = 0; i < genreIdStrings.length;i++) {
                genreId[i] = Integer.parseInt(genreIdStrings[i]);
            }
            String[] authorsIds = (String[]) requestContent.getRequestParameterValues().get("book_author");
            int[] authorIds = new int[authorsIds.length];
            for(int i = 0; i < authorsIds.length;i++) {
                authorIds[i] = Integer.parseInt(authorsIds[i]);
            }

            isBookEdited = bookDAO.editBook(book,genreId, authorIds);
            requestContent.insertAttribute("isBookEdited", isBookEdited);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void addAuthor(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean authorIsAdded;
        try {
            String name = (String) requestContent.getRequestParameters().get("author_name");
            String surname = (String) requestContent.getRequestParameters().get("author_surname");
            String patronymic = (String) requestContent.getRequestParameters().get("author_pathonymic");
            String dateOfBirth = (String) requestContent.getRequestParameters().get("date_of_birth");
            authorIsAdded = bookDAO.addAuthor(name, surname, patronymic, dateOfBirth);
            requestContent.insertAttribute("isAuthorAdded", authorIsAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addPublisher(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isPublisherAdded;
        try {
            String publisherName = (String) requestContent.getRequestParameters().get("publisher_name");

            isPublisherAdded = bookDAO.addPublisher(publisherName);
            requestContent.insertAttribute("isPublisherAdded", isPublisherAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addGenre(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isGenreAdded;
        try {
            String genreName = (String) requestContent.getRequestParameters().get("genre_name");

            isGenreAdded = bookDAO.addGenre(genreName);
            requestContent.insertAttribute("isGenreAdded", isGenreAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deleteGenre(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isGenreDeleted;
        try {
            String[] genres = (String[]) requestContent.getRequestParameterValues().get("book_genre");
            int[] genresId = new int[genres.length];
            for(int i = 0; i < genres.length; i++) {
                genresId[i] = Integer.parseInt(genres[i]);
            }
            isGenreDeleted = bookDAO.deleteGenre(genresId);
            requestContent.insertAttribute("isGenreDeleted", isGenreDeleted);
        } catch (DAOException e) {
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
        try {
            String title = (String) requestContent.getRequestParameters().get("book_title");
            int pages = Integer.parseInt((String) requestContent.getRequestParameters().get("book_pages"));
            int yearOfPublishing = Integer.parseInt((String) requestContent.getRequestParameters().get("book_year"));
            String isbn = (String) requestContent.getRequestParameters().get("book_isbn");
            int publisherId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_publisher"));
            String genresId[] = (String[]) requestContent.getRequestParameterValues().get("book_genre");
            int genreId[] = new int[genresId.length];
            for (int i = 0; i < genreId.length; i++) {
                String stringValue = genresId[i];
                int id = Integer.parseInt(stringValue);
                genreId[i] = id;
            }

            String authorsId[]= (String[]) requestContent.getRequestParameterValues().get("book_author");
            int[] authorId = new int[authorsId.length];
            for (int i = 0; i < authorId.length; i++) {
                String stringValue = authorsId[i];
                int id = Integer.parseInt(stringValue);
                authorId[i] = id;

            }
            String description = (String) requestContent.getRequestParameters().get("book_description");
            Location location = Location.valueOf(((String) requestContent.getRequestParameters().get("book_location")).toUpperCase());
            Book book = new Book();
            book.setTitle(title);
            book.setPages(pages);
            book.setYear(yearOfPublishing);
            book.setIsbn(isbn);

            book.setDescription(description);
            book.setLocation(location);
            isBookAdded = bookDAO.addBook(book, publisherId, genreId, authorId);
            requestContent.insertParameter("isBookAdded", isBookAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void deleteBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookDeleted;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
            isBookDeleted = bookDAO.deleteBook(bookId);
            requestContent.insertParameter("isBookDeleted", isBookDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOrderAdded;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
            String typeOfOrder = (String) requestContent.getRequestParameters().get("type_of_order");
            int librarianId = Integer.parseInt((String) requestContent.getRequestParameters().get("librarian_id"));
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));

            isOrderAdded = bookDAO.addOrder(bookId, typeOfOrder, librarianId, libraryCard);

            requestContent.insertAttribute("isOrderAdded", isOrderAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getUserOrders(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Order> userOrders;
        try {
            int libraryCard;
            libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
            userOrders = bookDAO.getUserOrders(libraryCard);

            requestContent.insertAttribute("userOrders", userOrders);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void returnBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookReturned;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
            int orderId = Integer.parseInt((String) requestContent.getRequestParameters().get("order_id"));
            isBookReturned = bookDAO.returnBook(orderId, bookId);
            requestContent.insertAttribute("isBookReturned", isBookReturned);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
