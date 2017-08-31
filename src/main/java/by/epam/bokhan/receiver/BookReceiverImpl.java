package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.dao.BookDAO;
import by.epam.bokhan.dao.BookDAOImpl;
import by.epam.bokhan.entity.*;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

import static by.epam.bokhan.receiver.ReceiverConstant.*;
import static by.epam.bokhan.validator.BookValidator.*;
import static by.epam.bokhan.validator.UserValidator.*;


public class BookReceiverImpl implements BookReceiver {
    private static final Logger LOGGER = LogManager.getLogger();

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
            requestContent.insertParameter(BOOKS, books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void findBookForUser(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Book> books = null;
        String titleValue = (String) requestContent.getRequestParameters().get(BOOK_TITLE);
        try {
            if (isBookTitleValid(titleValue)) {
                books = bookDAO.findBookByTitle(titleValue);
            }
            requestContent.insertParameter(BOOKS, books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getExplicitBookInfo(RequestContent requestContent) throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        Book book = null;
        List<Order> orders = null;
        String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
        try {
            if (isBookIdValid(bookIdValue)) {
                int bookId = Integer.parseInt(bookIdValue);
                book = dao.getExplicitBookInfo(bookId);
                orders = dao.getBooksLastOrder(bookId);
            }
            requestContent.insertParameter(FOUND_BOOK, book);
            requestContent.insertParameter(FOUND_ORDER, orders);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getBookForEditing(RequestContent requestContent) throws ReceiverException {
        BookDAO dao = new BookDAOImpl();
        Book books = null;
        List<Genre> genres = null;
        List<Author> authors = null;
        List<Publisher> publishers = null;
        String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
        try {
            if (isBookIdValid(bookIdValue)) {
                int bookId = Integer.parseInt(bookIdValue);
                books = dao.getExplicitBookInfo(bookId);
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
        List<Genre> genres;
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
            String bookDescription = (String) requestContent.getRequestParameters().get(BOOK_DESCRIPTION);
            String publisherIdValue = (String) requestContent.getRequestParameters().get(BOOK_PUBLISHER);
            String[] genreIdValues = (String[]) requestContent.getRequestParameterValues().get(BOOK_GENRE);
            String[] authorIdValues = (String[]) requestContent.getRequestParameterValues().get(BOOK_AUTHOR);
            Part bookImage = (Part) requestContent.getMultiTypeParts().get(BOOK_IMAGE);
            if (isBookIdValid(bookIdValue) && isBookTitleValid(title) && isBookIsbnValid(isbn) && isBookPageValid(pageValue) && isBookYearValid(yearValue)
                    && isBookPublisherIdValid(publisherIdValue) && isBookGenreIdValid(genreIdValues) && isBookAuthorIdValid(authorIdValues)) {
                Book book = new Book();
                int bookId = Integer.parseInt(bookIdValue);
                int pages = Integer.parseInt(pageValue);
                int year = Integer.parseInt(yearValue);
                Publisher publisher = new Publisher();
                int publisherId = Integer.parseInt(publisherIdValue);
                publisher.setId(publisherId);
                book.setPublisher(publisher);
                book.setId(bookId);
                book.setTitle(title);
                book.setPages(pages);
                book.setIsbn(isbn);
                book.setYear(year);
                book.setDescription(bookDescription);
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
                if (bookImage != null) {
                    String image = convertImageToBase64(bookImage);
                    if (!image.isEmpty()) {
                        book.setImage(image);
                    }
                }
                isBookEdited = bookDAO.editBook(book);
            }
            requestContent.insertAttribute(IS_BOOK_EDITED, isBookEdited);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
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
        List<Publisher> publishers;
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
        boolean isGenreAdded = false;
        try {
            String genreName = (String) requestContent.getRequestParameters().get(GENRE_NAME);
            if (isBookGenreNameValid(genreName)) {
                Genre genre = new Genre();
                genre.setName(genreName);
                isGenreAdded = bookDAO.addGenre(genre);
            }
            requestContent.insertAttribute(IS_GENRE_ADDED, isGenreAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deleteGenre(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isGenreDeleted = false;
        try {
            String[] genreIdValues = (String[]) requestContent.getRequestParameterValues().get(BOOK_GENRE);
            List<Genre> genres = new ArrayList<>();
            if (isBookGenreIdValid(genreIdValues)) {
                for (String genreIdValue : genreIdValues) {
                    Genre genre = new Genre();
                    genre.setId(Integer.parseInt(genreIdValue));
                    genres.add(genre);
                }
                isGenreDeleted = bookDAO.deleteGenre(genres);
            }
            requestContent.insertAttribute(IS_GENRE_DELETED, isGenreDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getAllAuthors(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Author> authors;
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
        boolean isAuthorDeleted = false;
        try {
            String[] authorIdValues = (String[]) requestContent.getRequestParameterValues().get(BOOK_AUTHOR);
            List<Author> authors = new ArrayList<>();
            if (isBookAuthorIdValid(authorIdValues)) {
                for (String authorIdValue : authorIdValues) {
                    Author author = new Author();
                    author.setId(Integer.parseInt(authorIdValue));
                    authors.add(author);
                }
                isAuthorDeleted = bookDAO.deleteAuthor(authors);
            }
            requestContent.insertAttribute(IS_AUTHOR_DELETED, isAuthorDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getGenresAuthorsPublishers(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Genre> genres;
        List<Author> authors;
        List<Publisher> publishers;
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
        boolean isBookAdded = false;
        try {
            String title = (String) requestContent.getRequestParameters().get(BOOK_TITLE);
            String pageValue = (String) requestContent.getRequestParameters().get(BOOK_PAGES);
            String yearValue = (String) requestContent.getRequestParameters().get(BOOK_YEAR);
            String publisherIdValue = (String) requestContent.getRequestParameters().get(BOOK_PUBLISHER);
            String isbn = (String) requestContent.getRequestParameters().get(BOOK_ISBN);
            String genresIdValues[] = (String[]) requestContent.getRequestParameterValues().get(BOOK_GENRE);
            String authorsIdValues[] = (String[]) requestContent.getRequestParameterValues().get(BOOK_AUTHOR);
            String description = (String) requestContent.getRequestParameters().get(BOOK_DESCRIPTION);
            String locationValue = (String) requestContent.getRequestParameters().get(BOOK_LOCATION);
            Part bookImage = (Part) requestContent.getMultiTypeParts().get(BOOK_IMAGE);
            if (isBookTitleValid(title) && isBookPageValid(pageValue) && isBookYearValid(yearValue) && isBookPublisherIdValid(publisherIdValue)
                    && isBookIsbnValid(isbn) && isBookGenreIdValid(genresIdValues) && isBookAuthorIdValid(authorsIdValues)
                    && isBookLocationValid(locationValue)) {
                Book book = new Book();
                int pages = Integer.parseInt(pageValue);
                int yearOfPublishing = Integer.parseInt(yearValue);
                Publisher publisher = new Publisher();
                publisher.setId(Integer.parseInt(publisherIdValue));
                Location location = Location.valueOf((locationValue).toUpperCase());
                List<Genre> genres = new ArrayList<>();
                for (String genreIdValue : genresIdValues) {
                    Genre genre = new Genre();
                    genre.setId(Integer.parseInt(genreIdValue));
                    genres.add(genre);
                }
                List<Author> authors = new ArrayList<>();
                for (String authorIdValue : authorsIdValues) {
                    Author author = new Author();
                    author.setId(Integer.parseInt(authorIdValue));
                    authors.add(author);
                }
                if (bookImage != null) {
                    String image = convertImageToBase64(bookImage);
                    if (!image.isEmpty()) {
                        book.setImage(image);
                    }
                }
                book.setTitle(title);
                book.setPages(pages);
                book.setYear(yearOfPublishing);
                book.setIsbn(isbn);
                book.setDescription(description);
                book.setLocation(location);
                book.setPublisher(publisher);
                book.setGenre(genres);
                book.setAuthors(authors);
                isBookAdded = bookDAO.addBook(book);
            }
            requestContent.insertAttribute(IS_BOOK_ADDED, isBookAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deleteBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookDeleted = false;
        try {
            String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
            if (isBookIdValid(bookIdValue)) {
                int bookId = Integer.parseInt(bookIdValue);
                isBookDeleted = bookDAO.deleteBook(bookId);
            }
            requestContent.insertAttribute(IS_BOOK_DELETED, isBookDeleted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOrderAdded = false;
        try {
            String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
            String librarianIdValue = (String) requestContent.getRequestParameters().get(LIBRARIAN_ID);
            String libraryCardId = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
            String typeOfOrder = (String) requestContent.getRequestParameters().get(TYPE_OF_ORDER);
            if (isBookIdValid(bookIdValue) && isUserIdValid(librarianIdValue) && isLibraryCardIdValid(libraryCardId) && isBookLocationValid(typeOfOrder)) {
                int bookId = Integer.parseInt(bookIdValue);
                int librarianId = Integer.parseInt(librarianIdValue);
                int libraryCard = Integer.parseInt(libraryCardId);
                Book book = new Book();
                book.setId(bookId);
                Order order = new Order();
                User client = new User();
                client.setLibraryCardNumber(libraryCard);
                order.setUser(client);
                User librarian = new User();
                librarian.setId(librarianId);
                order.setLibrarian(librarian);
                order.setBook(book);
                isOrderAdded = bookDAO.addOrder(order, typeOfOrder);
            }
            requestContent.insertAttribute(IS_ORDER_ADDED, isOrderAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }


    @Override
    public void returnBook(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isBookReturned = false;
        try {
            String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
            String orderIdValue = (String) requestContent.getRequestParameters().get(ORDER_ID);
            if (isBookIdValid(bookIdValue) && isOrderIdValid(orderIdValue)) {
                int bookId = Integer.parseInt(bookIdValue);
                int orderId = Integer.parseInt(orderIdValue);
                isBookReturned = bookDAO.returnBook(orderId, bookId);
            }
            requestContent.insertAttribute(IS_BOOK_RETURNED, isBookReturned);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void addOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderAdded = false;
        try {
            String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
            String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
            if (isBookIdValid(bookIdValue) && isLibraryCardIdValid(libraryCardValue)) {
                int bookId = Integer.parseInt(bookIdValue);
                int libraryCard = Integer.parseInt(libraryCardValue);
                isOnlineOrderAdded = bookDAO.addOnlineOrder(bookId, libraryCard);
            }
            requestContent.insertAttribute(IS_ONLINE_ORDER_ADDED, isOnlineOrderAdded);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void cancelOnlineOrder(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        boolean isOnlineOrderCancelled = false;
        try {
            String onlineOrderIdValue = (String) requestContent.getRequestParameters().get(ORDER_ID);
            String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
            if (isOnlineOrderIdValid(onlineOrderIdValue) && isBookIdValid(bookIdValue)) {
                int onlineOrderId = Integer.parseInt(onlineOrderIdValue);
                Location onlineOrderStatus = bookDAO.onlineOrderStatus(onlineOrderId).getLocation();
                if (!Location.CANCELED.equals(onlineOrderStatus)) {
                    int bookId = Integer.parseInt(bookIdValue);
                    isOnlineOrderCancelled = bookDAO.cancelOnlineOrder(onlineOrderId, bookId);
                }
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
            String onlineOrderIdValue = (String) requestContent.getRequestParameters().get(ONLINE_ORDER_ID);
            String bookIdValue = (String) requestContent.getRequestParameters().get(BOOK_ID);
            String libraryCardValue = (String) requestContent.getRequestParameters().get(LIBRARY_CARD);
            String librarianIdValue = (String) requestContent.getRequestParameters().get(LIBRARIAN_ID);
            String typeOfOrder = (String) requestContent.getRequestParameters().get(TYPE_OF_ORDER);
            if (isOnlineOrderIdValid(onlineOrderIdValue) && isBookIdValid(bookIdValue) && isLibraryCardIdValid(libraryCardValue)
                    && isUserIdValid(librarianIdValue) && typeOfOrder != null) {
                int onlineOrderId = Integer.parseInt(onlineOrderIdValue);
                Location location = bookDAO.onlineOrderStatus(onlineOrderId).getLocation();
                if (Location.BOOKED.equals(location)) {
                    int bookId = Integer.parseInt(bookIdValue);
                    int libraryCard = Integer.parseInt(libraryCardValue);
                    int librarianId = Integer.parseInt(librarianIdValue);
                    OnlineOrder onlineOrder = new OnlineOrder();
                    onlineOrder.setId(onlineOrderId);
                    Book book = new Book();
                    book.setId(bookId);
                    onlineOrder.setBook(book);
                    User client = new User();
                    client.setLibraryCardNumber(libraryCard);
                    onlineOrder.setUser(client);
                    User librarian = new User();
                    librarian.setId(librarianId);
                    onlineOrder.setLibrarian(librarian);
                    isOnlineOrderExecuted = bookDAO.executeOnlineOrder(onlineOrder, typeOfOrder);
                }
            }
            requestContent.insertAttribute(IS_ONLINE_ORDER_EXECUTED, isOnlineOrderExecuted);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getBookByGenre(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Book> books = null;
        try {
            String bookGenre = (String) requestContent.getRequestParameters().get(GENRE_NAME);
            if (isBookGenreNameValid(bookGenre)) {
                Genre genre = new Genre();
                genre.setName(bookGenre);
                books = bookDAO.getBooksByGenre(genre);
            }
            requestContent.insertParameter(BOOKS, books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void getRandomBooks(RequestContent requestContent) throws ReceiverException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Book> books;
        try {
            books = bookDAO.getRandomBooks(RANDOM_NUMBER_OF_BOOKS);
            requestContent.insertParameter(FOUND_BOOKS, books);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    private String convertImageToBase64(Part bookImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        String image = null;
        try {
            is = bookImage.getInputStream();
            int reads = is.read();
            while (reads != -1) {
                baos.write(reads);
                reads = is.read();
            }
            byte[] b = baos.toByteArray();
            image = Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, String.format("Can not convert to string. Reason : %s", e));
        }finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, String.format("Can not close stream. Reason : %s", e));
            }
        }
        return image;
    }
}
