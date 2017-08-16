package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.BookDAO;
import by.epam.bokhan.dao.BookDAOImpl;
import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static by.epam.bokhan.receiver.ReceiverConstant.*;
import static by.epam.bokhan.validator.BookValidator.*;


public class BookReceiverImpl implements BookReceiver {


    @Override
    public void getAllBooks(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        try {
            List<Book> books = bookDAO.getAllBooks();
            requestContent.insertParameter(BOOKS, books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void findBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();


        List<Book> books = null;
        String queryValue = (String) requestContent.getRequestParameters().get(FIND_QUERY_VALUE);
        try {
            if (isBookIdValid(queryValue)) {
                int bookId = Integer.parseInt(queryValue);
                books = bookDAO.findBookById(bookId);
            } else if (isBookTitleValid(queryValue)) {
                books = bookDAO.findBookByTitle(queryValue);
            }
            requestContent.insertParameter(FOUND_BOOK, books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getExplicitBookInfo(RequestContent requestContent) throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        List<Book> books = null;
        List<Order> orders = null;
        String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
        try {
            if (isBookIdValid(bookIdValue)) {
                int bookId = Integer.parseInt(bookIdValue);
                books = dao.getExplicitBookInfo(bookId);
                orders = dao.getBooksLastOrder(bookId);
            }

            requestContent.insertParameter(FOUND_BOOK, books);
            requestContent.insertParameter(FOUND_ORDER, orders);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getBookForEditing(RequestContent requestContent) throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        List<Book> books = null;
        List<Genre> genres = null;
        List<Author> authors = null;
        List<Publisher> publishers = null;
        String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
        try {
            if (isBookIdValid(bookIdValue)) {
                int bookId = Integer.parseInt(bookIdValue);
                books = dao.getBookForEditing(bookId);
                genres = dao.getAllGenres();
                authors = dao.getAllAuthors();
                publishers = dao.getAllPublishers();
            }
            requestContent.insertParameter(FOUND_BOOK, books);
            requestContent.insertParameter(GENRES, genres);
            requestContent.insertParameter(AUTHORS, authors);
            requestContent.insertParameter(PUBLISHERS, publishers);
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
            requestContent.insertParameter(GENRES, genres);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void editBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookEdited = false;
        try {
            String title = (String) requestContent.getRequestParameters().get(BOOK_TITLE);
            String isbn = (String) requestContent.getRequestParameters().get(BOOK_ISBN);
            String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
            String pageValue = (String) requestContent.getRequestParameters().get(BOOK_PAGES);
            String yearValue = (String) requestContent.getRequestParameters().get(BOOK_YEAR);
            String publisherIdValue = (String) requestContent.getRequestParameters().get(BOOK_PUBLISHER);
            String[] genreIdValues = (String[]) requestContent.getRequestParameterValues().get(BOOK_GENRE);
            String[] authorIdValues = (String[]) requestContent.getRequestParameterValues().get(BOOK_AUTHOR);
            if (isBookIdValid(bookIdValue) && isBookTitleValid(title) && isBookIsbnValid(isbn) && isBookPageValid(pageValue) && isBookYearValid(yearValue)
                    && isBookPublisherIdValid(publisherIdValue) && isBookGenreIdValid(genreIdValues) && isBookAuthorIdValid(authorIdValues)) {
                int bookId = Integer.parseInt(bookIdValue);
                int pages = Integer.parseInt(pageValue);
                int year = Integer.parseInt(yearValue);
                int publisherId = Integer.parseInt(publisherIdValue);
                Book book = new Book();
                book.setId(bookId);
                book.setTitle(title);
                book.setPages(pages);
                book.setIsbn(isbn);
                book.setYear(year);
                Publisher publisher = new Publisher();
                publisher.setId(publisherId);
                book.setPublisher(publisher);
                for (String genreId : genreIdValues) {
                    Genre genre = new Genre();
                    genre.setId(Integer.parseInt(genreId));
                    book.addGenre(genre);
                }
                for (String authorId : authorIdValues) {
                    Author author = new Author();
                    author.setId(Integer.parseInt(authorId));
                    book.addAuthor(author);
                }
                isBookEdited = bookDAO.editBook(book);
            }
            requestContent.insertAttribute(IS_BOOK_EDITED, isBookEdited);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void addAuthor(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean authorIsAdded = false;
        try {
            String name = (String) requestContent.getRequestParameters().get(AUTHOR_NAME);
            String surname = (String) requestContent.getRequestParameters().get(AUTHOR_SURNAME);
            String patronymic = (String) requestContent.getRequestParameters().get(AUTHOR_PATRONYMIC);
            String dateOfBirthValue = (String) requestContent.getRequestParameters().get(DATE_OF_BIRTH);
            if (isAuthorNameValid(name) && isAuthorSurnameValid(surname) && isAuthorPatronymicValid(patronymic) && isDateOfBirthValid(dateOfBirthValue)) {
                Author author = new Author();
                author.setName(name);
                author.setSurname(surname);
                author.setPatronymic(patronymic);
                LocalDate dateOfBirth = LocalDate.parse(dateOfBirthValue);
                author.setDateOfBirth(dateOfBirth);
                authorIsAdded = bookDAO.addAuthor(author);
            }
            requestContent.insertAttribute(IS_AUTHOR_ADDED, authorIsAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addPublisher(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isPublisherAdded = false;
        try {
            String publisherNameValue = (String) requestContent.getRequestParameters().get(PUBLISHER_NAME);
            if (isBookPublisherValid(publisherNameValue)) {
                isPublisherAdded = bookDAO.addPublisher(publisherNameValue);
            }
            requestContent.insertAttribute(IS_PUBLISHER_ADDED, isPublisherAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deletePublisher(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isPublisherDeleted;
        try {
            String[] publisherIdValues = (String[]) requestContent.getRequestParameterValues().get(BOOK_PUBLISHER);
            List<Publisher> publishers = new ArrayList<>();
            for (String publisherId : publisherIdValues) {
                if (isBookPublisherIdValid(publisherId)) {
                    Publisher publisher = new Publisher();
                    publisher.setId(Integer.parseInt(publisherId));
                    publishers.add(publisher);
                }
            }
            isPublisherDeleted = bookDAO.deletePublisher(publishers);
            requestContent.insertAttribute(IS_PUBLISHER_DELETED, isPublisherDeleted);
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
            requestContent.insertParameter(PUBLISHERS, publishers);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addGenre(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isGenreAdded;
        try {
            String genreName = (String) requestContent.getRequestParameters().get(GENRE_NAME);

            isGenreAdded = bookDAO.addGenre(genreName);
            requestContent.insertAttribute(IS_GENRE_ADDED, isGenreAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deleteGenre(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isGenreDeleted;
        try {
            String[] genres = (String[]) requestContent.getRequestParameterValues().get(BOOK_GENRE);
            int[] genresId = new int[genres.length];
            for (int i = 0; i < genres.length; i++) {
                genresId[i] = Integer.parseInt(genres[i]);
            }
            isGenreDeleted = bookDAO.deleteGenre(genresId);
            requestContent.insertAttribute(IS_GENRE_DELETED, isGenreDeleted);
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
            requestContent.insertParameter(AUTHORS, authors);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deleteAuthor(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isAuthorDeleted;
        try {
            String[] authors = (String[]) requestContent.getRequestParameterValues().get(BOOK_AUTHOR);
            int[] authorsIds = new int[authors.length];
            for (int i = 0; i < authors.length; i++) {
                authorsIds[i] = Integer.parseInt(authors[i]);
            }
            isAuthorDeleted = bookDAO.deleteAuthor(authorsIds);
            requestContent.insertAttribute(IS_AUTHOR_DELETED, isAuthorDeleted);
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
            requestContent.insertParameter(GENRES, genres);
            authors = bookDAO.getAllAuthors();
            requestContent.insertParameter(AUTHORS, authors);
            publishers = bookDAO.getAllPublishers();
            requestContent.insertParameter(PUBLISHERS, publishers);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookAdded;
        try {
            String title = (String) requestContent.getRequestParameters().get(BOOK_TITLE);
            int pages = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_PAGES));
            int yearOfPublishing = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_YEAR));
            String isbn = (String) requestContent.getRequestParameters().get(BOOK_ISBN);
            int publisherId = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_PUBLISHER));
            String genresId[] = (String[]) requestContent.getRequestParameterValues().get(BOOK_GENRE);
            int genreId[] = new int[genresId.length];
            for (int i = 0; i < genreId.length; i++) {
                String stringValue = genresId[i];
                int id = Integer.parseInt(stringValue);
                genreId[i] = id;
            }

            String authorsId[] = (String[]) requestContent.getRequestParameterValues().get(BOOK_AUTHOR);
            int[] authorId = new int[authorsId.length];
            for (int i = 0; i < authorId.length; i++) {
                String stringValue = authorsId[i];
                int id = Integer.parseInt(stringValue);
                authorId[i] = id;

            }
            String description = (String) requestContent.getRequestParameters().get(BOOK_DESCRIPTION);
            Location location = Location.valueOf(((String) requestContent.getRequestParameters().get(BOOK_LOCATION)).toUpperCase());
            Book book = new Book();
            book.setTitle(title);
            book.setPages(pages);
            book.setYear(yearOfPublishing);
            book.setIsbn(isbn);

            book.setDescription(description);
            book.setLocation(location);
            isBookAdded = bookDAO.addBook(book, publisherId, genreId, authorId);
            requestContent.insertAttribute(IS_BOOK_ADDED, isBookAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    public void deleteBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookDeleted;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_ID));
            isBookDeleted = bookDAO.deleteBook(bookId);
            requestContent.insertAttribute(IS_BOOK_DELETED, isBookDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOrderAdded;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_ID));
            String typeOfOrder = (String) requestContent.getRequestParameters().get(TYPE_OF_ORDER);
            int librarianId = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARIAN_ID));
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARY_CARD));

            isOrderAdded = bookDAO.addOrder(bookId, typeOfOrder, librarianId, libraryCard);

            requestContent.insertAttribute(IS_ORDER_ADDED, isOrderAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getUserOrders(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Order> userOrders;
        try {
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARY_CARD));
            userOrders = bookDAO.getUserOrders(libraryCard);

            requestContent.insertAttribute(USER_ORDERS, userOrders);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void returnBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookReturned;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_ID));
            int orderId = Integer.parseInt((String) requestContent.getRequestParameters().get(ORDER_ID));
            isBookReturned = bookDAO.returnBook(orderId, bookId);
            requestContent.insertAttribute(IS_BOOK_RETURNED, isBookReturned);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderAdded;
        try {
            int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_ID));
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARY_CARD));
            isOnlineOrderAdded = bookDAO.addOnlineOrder(bookId, libraryCard);
            requestContent.insertAttribute(IS_ONLINE_ORDER_ADDED, isOnlineOrderAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getUserOnlineOrders(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Order> userOrders;
        try {
            int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARY_CARD));
            userOrders = bookDAO.getUserOnlineOrders(libraryCard);

            requestContent.insertParameter(USER_ORDERS, userOrders);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void cancelOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderCancelled = false;
        try {
            int orderId = Integer.parseInt((String) requestContent.getRequestParameters().get(ORDER_ID));
            String orderStatus = bookDAO.onlineOrderStatus(orderId).getStatus();
            if (!orderStatus.equals(STATUS_CANCELED)) {
                int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_ID));
                isOnlineOrderCancelled = bookDAO.cancelOnlineOrder(orderId, bookId);
            }
            requestContent.insertAttribute(IS_ONLINE_ORDER_CANCELLED, isOnlineOrderCancelled);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }


    @Override
    public void executeOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderExecuted = false;
        try {
            int onlineOrderId = Integer.parseInt((String) requestContent.getRequestParameters().get(ONLINE_ORDER_ID));

            String orderStatus = bookDAO.onlineOrderStatus(onlineOrderId).getStatus();
            if (orderStatus.equals("booked")) {
                int bookId = Integer.parseInt((String) requestContent.getRequestParameters().get(BOOK_ID));
                int libraryCard = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARY_CARD));
                int librarianId = Integer.parseInt((String) requestContent.getRequestParameters().get(LIBRARIAN_ID));
                String typeOfOrder = (String) requestContent.getRequestParameters().get(TYPE_OF_ORDER);
                isOnlineOrderExecuted = bookDAO.executeOnlineOrder(onlineOrderId, typeOfOrder, bookId, libraryCard, librarianId);

            }
            requestContent.insertAttribute(IS_ONLINE_ORDER_EXECUTED, isOnlineOrderExecuted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

//    find book part

    @Override
    public void findBookByGenre(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Book> books;
        try {
            String bookGenre = (String) requestContent.getRequestParameters().get(GENRE_NAME);
            Genre genre = new Genre();
            genre.setName(bookGenre);
            books = bookDAO.getBooksByGenre(genre);
            requestContent.insertParameter(FOUND_BOOKS, books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }
}
