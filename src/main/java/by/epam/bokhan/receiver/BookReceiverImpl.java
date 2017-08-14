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
            int publisherId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_publisher"));
            Publisher publisher = new Publisher();
            publisher.setId(publisherId);
            Book book = new Book();
            book.setId(bookId);
            book.setTitle(title);
            book.setPages(pages);
            book.setIsbn(isbn);
            book.setYear(year);
            book.setPublisher(publisher);
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
    public void deletePublisher(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isPublisherDeleted;
        try {
            String[] publishers = (String[]) requestContent.getRequestParameterValues().get("book_publisher");
            int[] publishersIds = new int[publishers.length];
            for(int i = 0; i < publishers.length; i++) {
                publishersIds[i] = Integer.parseInt(publishers[i]);
            }
            isPublisherDeleted = bookDAO.deletePublisher(publishersIds);
            requestContent.insertAttribute("isPublisherDeleted", isPublisherDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getAllPublishers(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        LinkedList<Publisher> publishers;
        try {
            publishers = bookDAO.getAllPublishers();
            requestContent.insertParameter("publishers", publishers);
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
    public void getAllAuthors(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        LinkedList<Author> authors;
        try {
            authors = bookDAO.getAllAuthors();
            requestContent.insertParameter("authors", authors);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deleteAuthor(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isAuthorDeleted;
        try {
            String[] authors = (String[]) requestContent.getRequestParameterValues().get("book_author");
            int[] authorsIds = new int[authors.length];
            for(int i = 0; i < authors.length; i++) {
                authorsIds[i] = Integer.parseInt(authors[i]);
            }
            isAuthorDeleted = bookDAO.deleteAuthor(authorsIds);
            requestContent.insertAttribute("isAuthorDeleted", isAuthorDeleted);
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
            requestContent.insertAttribute("isBookAdded", isBookAdded);
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
            requestContent.insertAttribute("isBookDeleted", isBookDeleted);
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
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
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

    @Override
    public void addOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderAdded;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
            isOnlineOrderAdded = bookDAO.addOnlineOrder(bookId, libraryCard);
            requestContent.insertAttribute("isOnlineOrderAdded", isOnlineOrderAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getUserOnlineOrders(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Order> userOrders;
        try {
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
            userOrders = bookDAO.getUserOnlineOrders(libraryCard);

            requestContent.insertParameter("userOrders", userOrders);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void cancelOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderCancelled = false;
        try {
            int orderId = Integer.parseInt((String) requestContent.getRequestParameters().get("order_id"));
            String orderStatus = bookDAO.onlineOrderStatus(orderId).getStatus();
            if (!orderStatus.equals("canceled")) {
                int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
                isOnlineOrderCancelled = bookDAO.cancelOnlineOrder(orderId, bookId);
            }
            requestContent.insertAttribute("isOnlineOrderCancelled", isOnlineOrderCancelled);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }


    @Override
    public void executeOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderExecuted = false;
        try {
            int onlineOrderId = Integer.parseInt((String) requestContent.getRequestParameters().get("online_order_id"));

            String orderStatus = bookDAO.onlineOrderStatus(onlineOrderId).getStatus();
            if (orderStatus.equals("booked")) {
                int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get("book_id"));
                int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get("library_card"));
                int librarianId = Integer.parseInt((String) requestContent.getRequestParameters().get("librarian_id"));
                String typeOfOrder = (String) requestContent.getRequestParameters().get("type_of_order");
                isOnlineOrderExecuted = bookDAO.executeOnlineOrder(onlineOrderId,typeOfOrder, bookId, libraryCard, librarianId);

            }
            requestContent.insertAttribute("isOnlineOrderExecuted", isOnlineOrderExecuted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
