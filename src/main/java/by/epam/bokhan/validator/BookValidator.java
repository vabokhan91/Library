package by.epam.bokhan.validator;

import by.epam.bokhan.entity.Location;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BookValidator {
    private static final String REGEX_FOR_BOOK_ID = "\\d{1,5}";
    private static final String REGEX_FOR_BOOK_TITLE = "[\\d\\w\\W[а-яА-ЯеЁ}]]+";
    private static final String REGEX_FOR_BOOK_ISBN = "(\\d+-\\d+-\\d+-\\d+-\\d+)|(\\d+-\\d+-\\d+-\\d+)";
    private static final String REGEX_FOR_BOOK_PAGE = "\\d{1,5}";
    private static final String REGEX_FOR_BOOK_YEAR = "\\d{1,5}";
    private static final String REGEX_FOR_BOOK_PUBLISHER = "[\\d\\D]{1,50}";
    private static final String REGEX_FOR_BOOK_GENRE = "[a-zA-Zа-яА-ЯеЁ\\s]{1,30}";
    private static final String REGEX_FOR_BOOK_PUBLISHER_ID = "\\d{1,5}";
    private static final String REGEX_FOR_BOOK_GENRE_ID = "\\d{1,5}";
    private static final String REGEX_FOR_AUTHOR_ID = "\\d{1,5}";
    private static final String REGEX_FOR_DATE_OF_BIRTH = "^(\\d){3,4}[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";
    private static final String REGEX_FOR_NAME = "[a-zA-Z\\s]{1,40}|([а-яА-ЯёЁ\\s]{1,40})";
    private static final String REGEX_FOR_SURNAME = "[a-zA-Z\\s]{1,40}|([а-яА-ЯёЁ\\s]{1,40})";
    private static final String REGEX_FOR_PATRONYMIC = "[a-zA-Z\\s]{1,40}|([а-яА-ЯёЁ\\s]{1,40})";
    private static final String REGEX_FOR_BOOK_LOCATION = "[a-zA-Zа-яА-Я_]{1,30}";
    private static final String REGEX_FOR_ORDER_ID = "\\d{1,5}";
    private static final String REGEX_FOR_ONLINE_ORDER_ID = "\\d{1,5}";


    public static boolean isBookIdValid(String bookId) {
        boolean isBookIdValid = false;
        if (bookId != null) {
            Pattern patternForBookId = Pattern.compile(REGEX_FOR_BOOK_ID);
            Matcher matcherForBookId = patternForBookId.matcher(bookId);
            isBookIdValid = matcherForBookId.matches();
        }
        return isBookIdValid;
    }

    public static boolean isBookTitleValid(String title) {
        boolean isBookTitleValid = false;
        if (title != null) {
            Pattern patternForBookTitle = Pattern.compile(REGEX_FOR_BOOK_TITLE);
            Matcher matcherForBookTitle = patternForBookTitle.matcher(title);
            isBookTitleValid = matcherForBookTitle.matches();
        }
        return isBookTitleValid;
    }

    public static boolean isBookIsbnValid(String isbn) {
        boolean isBookIsbnValid = false;
        if (isbn != null) {
            Pattern patternForIsbn = Pattern.compile(REGEX_FOR_BOOK_ISBN);
            Matcher matcherForIsbn = patternForIsbn.matcher(isbn);
            isBookIsbnValid = matcherForIsbn.matches();
        }
        return isBookIsbnValid;
    }

    public static boolean isBookPageValid(String page) {
        boolean isBookPageValid = false;
        if (page != null) {
            Pattern patternForBookPage = Pattern.compile(REGEX_FOR_BOOK_PAGE);
            Matcher matcherForBookPage = patternForBookPage.matcher(page);
            isBookPageValid = matcherForBookPage.matches();
        }
        return isBookPageValid;
    }

    public static boolean isBookYearValid(String year) {
        boolean isBookYearValid = false;
        if (year != null) {
            Pattern patternForBookYear = Pattern.compile(REGEX_FOR_BOOK_YEAR);
            Matcher matcherForBookYear = patternForBookYear.matcher(year);
            isBookYearValid = matcherForBookYear.matches();
        }
        return isBookYearValid;
    }

    public static boolean isBookPublisherValid(String publisher) {
        boolean isBookPublisherValid;
        if (publisher == null) {
            isBookPublisherValid = true;
        }else {
            Pattern patternForBookPublisher = Pattern.compile(REGEX_FOR_BOOK_PUBLISHER);
            Matcher matcherForBookPublisher = patternForBookPublisher.matcher(publisher);
            isBookPublisherValid = matcherForBookPublisher.matches();
        }

        return isBookPublisherValid;
    }

    public static boolean isBookPublisherIdValid(String publisherId) {
        Pattern patternForBookPublisherId = Pattern.compile(REGEX_FOR_BOOK_PUBLISHER_ID);
        Matcher matcherForBookPublisherId = patternForBookPublisherId.matcher(publisherId);
        return publisherId == null || publisherId.isEmpty() || matcherForBookPublisherId.matches();
    }

    public static boolean isBookGenreNameValid(String genreName) {
        boolean isBookGenreValid = false;
        if (genreName != null) {
            Pattern patternForBookGenre = Pattern.compile(REGEX_FOR_BOOK_GENRE);
            Matcher matcherForBookGenre = patternForBookGenre.matcher(genreName);
            isBookGenreValid = matcherForBookGenre.matches();
        }
        return isBookGenreValid;
    }

    public static boolean isBookGenreIdValid(String[] genreIdValues) {
        boolean isBookGenreIdValid = false;
        if (genreIdValues != null) {
            for (String genreIdValue : genreIdValues) {
                Pattern patternForBookGenreId = Pattern.compile(REGEX_FOR_BOOK_GENRE_ID);
                Matcher matcherForBookGenreId = patternForBookGenreId.matcher(genreIdValue);
                isBookGenreIdValid = matcherForBookGenreId.matches();
            }
        }
        return isBookGenreIdValid;
    }

    public static boolean isBookAuthorIdValid(String[] authors) {
        boolean isBookAuthorIdValid = false;
        if (authors != null) {
            Pattern patternForAuthorId = Pattern.compile(REGEX_FOR_AUTHOR_ID);
            for (String author : authors) {
                Matcher matcherForAuthorId = patternForAuthorId.matcher(author);
                isBookAuthorIdValid = matcherForAuthorId.matches();
            }
        }
        return isBookAuthorIdValid;
    }

    public static boolean isDateOfBirthValid(String dateOfBirth) {
        boolean isDateOfBirthValid = false;
        if (dateOfBirth != null) {
            Pattern patternForDateOfBirth = Pattern.compile(REGEX_FOR_DATE_OF_BIRTH);
            Matcher matcherForDateOfBirth = patternForDateOfBirth.matcher(dateOfBirth);
            isDateOfBirthValid = matcherForDateOfBirth.matches();
        }
        return isDateOfBirthValid;
    }

    public static boolean isAuthorNameValid(String name) {
        boolean isNameValid = false;
        if (name != null) {
            Pattern patternForName = Pattern.compile(REGEX_FOR_NAME);
            Matcher matcherForName = patternForName.matcher(name);
            isNameValid = matcherForName.matches();
        }
        return isNameValid;
    }

    public static boolean isAuthorSurnameValid(String surname) {
        boolean isSurnameValid = false;
        if (surname != null) {
            Pattern patternForSurname = Pattern.compile(REGEX_FOR_SURNAME);
            Matcher matcherForSurname = patternForSurname.matcher(surname);
            isSurnameValid = matcherForSurname.matches();
        }
        return isSurnameValid;
    }

    public static boolean isAuthorPatronymicValid(String patronymic) {
        boolean isPatronymicValid = false;
        if (patronymic == null || patronymic.isEmpty()) {
            isPatronymicValid = true;
        }else {
            Pattern patternForPatronymic = Pattern.compile(REGEX_FOR_PATRONYMIC);
            Matcher matcherForPatronymic = patternForPatronymic.matcher(patronymic);
            if (matcherForPatronymic.matches()) {
                isPatronymicValid = true;
            }
        }

        return isPatronymicValid;
    }

    public static boolean isBookLocationValid(String locationValue) {
        boolean isLocationValid = false;
        if (locationValue != null) {
            Pattern patternForLocation = Pattern.compile(REGEX_FOR_BOOK_LOCATION);
            Matcher matcherForLocation = patternForLocation.matcher(locationValue);
            if (matcherForLocation.matches()) {
                Location[] locationValues = Location.values();
                for (Location location : locationValues) {
                    if (location.name().equalsIgnoreCase(locationValue)) {
                        isLocationValid = true;
                    }
                }
            }
        }
        return isLocationValid;
    }

    public static boolean isOrderIdValid(String orderIdValue) {
        boolean isOrderIdValid = false;
        if (orderIdValue != null) {
            Pattern patternForOrderId = Pattern.compile(REGEX_FOR_ORDER_ID);
                Matcher matcherForOrderId = patternForOrderId.matcher(orderIdValue);
                isOrderIdValid = matcherForOrderId.matches();

        }
        return isOrderIdValid;
    }

    public static boolean isOnlineOrderIdValid(String onlineOrderIdValue) {
        boolean isOnlineOrderIdValid = false;
        if (onlineOrderIdValue != null) {
            Pattern patternForOnlineOrderId = Pattern.compile(REGEX_FOR_ONLINE_ORDER_ID);
            Matcher matcherForOnlineOrderId = patternForOnlineOrderId.matcher(onlineOrderIdValue);
            isOnlineOrderIdValid = matcherForOnlineOrderId.matches();

        }
        return isOnlineOrderIdValid;
    }
}
