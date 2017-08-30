package by.epam.bokhan.validator;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(Enclosed.class)
public class BookValidatorTest {

    @RunWith(Parameterized.class)
    public static class ParametrizedBookIdPart {
        private final String bookIdValue;
        private final boolean expectedValue;

        public ParametrizedBookIdPart(String bookIdValue, boolean expectedValue) {
            this.bookIdValue = bookIdValue;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> bookIdData() {
            return Arrays.asList(new Object[][]{
                    {"qw1", false},
                    {"1", true},
                    {"12", true},
                    {"123", true},
                    {"1234", true},
                    {"12345", true},
                    {"123456", false},
                    {null, false}
            });
        }

        @Test
        public void isBookIdValid() {
            boolean actual = BookValidator.isBookIdValid(bookIdValue);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedBookTitlePart {
        private final String bookTitle;
        private final boolean expectedValue;

        public ParametrizedBookTitlePart(String bookTitle, boolean expectedValue) {
            this.bookTitle = bookTitle;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> bookTitleData() {
            return Arrays.asList(new Object[][]{
                    {"1234", true},
                    {"", false},
                    {"Song", true},
                    {"Song123", true},
                    {"qwerty!", true},
                    {"Song of ice and Fire", true},
                    {"Song of ice and Fire. Part 3", true},
                    {null, false}
            });
        }

        @Test
        public void isBookTitleValidTest() {
            boolean actual = BookValidator.isBookTitleValid(bookTitle);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedBookIsbnPart {
        private final String bookIsbn;
        private final boolean expectedValue;

        public ParametrizedBookIsbnPart(String bookIsbn, boolean expectedValue) {
            this.bookIsbn = bookIsbn;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> bookIsbnData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"", false},
                    {"123-123", false},
                    {"123-456-789", false},
                    {"123-456-789-123", true},
                    {"123-456-789-123-456", true},
                    {"123-456-789-123-456-789", false},
                    {null, false}
            });
        }

        @Test
        public void isBookIsbnValidTest() {
            boolean actual = BookValidator.isBookIsbnValid(bookIsbn);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedBookPagePart {
        private final String bookPageValue;
        private final boolean expectedValue;

        public ParametrizedBookPagePart(String bookPageValue, boolean expectedValue) {
            this.bookPageValue = bookPageValue;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> bookPageData() {
            return Arrays.asList(new Object[][]{
                    {"qw1", false},
                    {"1", true},
                    {"12", true},
                    {"123", true},
                    {"1234", true},
                    {"12345", true},
                    {"123456", false},
                    {null, false}
            });
        }

        @Test
        public void isBookPageValid() {
            boolean actual = BookValidator.isBookPageValid(bookPageValue);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedBookYearPart {
        private final String bookYearValue;
        private final boolean expectedValue;

        public ParametrizedBookYearPart(String bookYearValue, boolean expectedValue) {
            this.bookYearValue = bookYearValue;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> bookYearData() {
            return Arrays.asList(new Object[][]{
                    {"qw1", false},
                    {"1", true},
                    {"12", true},
                    {"123", true},
                    {"1234", true},
                    {"12345", true},
                    {"123456", false},
                    {null, false}
            });
        }

        @Test
        public void isBookYearValid() {
            boolean actual = BookValidator.isBookYearValid(bookYearValue);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedBookPublisherPart {
        private final String bookPublisher;
        private final boolean expectedValue;

        public ParametrizedBookPublisherPart(String bookPublisher, boolean expectedValue) {
            this.bookPublisher = bookPublisher;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> bookPublisherData() {
            return Arrays.asList(new Object[][]{
                    {"1234", true},
                    {"", false},
                    {"ООО \"Издательство АСТ\"", true},
                    {"ДЭМ, Москва", true},
                    {"qwerty!", true},
                    {"qwer-123", true},
                    {null, true}
            });
        }

        @Test
        public void isBookPublisherValidTest() {
            boolean actual = BookValidator.isBookPublisherValid(bookPublisher);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedBookGenreNamePart {
        private final String bookGenreValue;
        private final boolean expectedValue;

        public ParametrizedBookGenreNamePart(String bookGenreValue, boolean expectedValue) {
            this.bookGenreValue = bookGenreValue;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> bookGenreData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"", false},
                    {"TestFalse123", false},
                    {"TestGenre", true},
                    {"Test Genre", true},
                    {"ТестЖанра", true},
                    {null, false}
            });
        }

        @Test
        public void isBookGenreValidTest() {
            boolean actual = BookValidator.isBookGenreNameValid(bookGenreValue);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedDateOfBirthPart {
        private final String dateOfBirth;
        private final boolean expectedValue;

        public ParametrizedDateOfBirthPart(String dateOfBirth, boolean expectedValue) {
            this.dateOfBirth = dateOfBirth;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> dateOfBirthData() {
            return Arrays.asList(new Object[][]{
                    {"1992-01-12", true},
                    {"999 12 13", true},
                    {"1234/11/21", true},
                    {"2017-13-15", false},
                    {"", false},
                    {null, false}
            });
        }

        @Test
        public void isDateOfBirthValid() {
            boolean actual = BookValidator.isDateOfBirthValid(dateOfBirth);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedAuthorNamePart {
        private final String authorName;
        private final boolean expectedValue;

        public ParametrizedAuthorNamePart(String authorName, boolean expectedValue) {
            this.authorName = authorName;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> authorNameData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"", false},
                    {"John", true},
                    {"Erikh Marya", true},
                    {"john123", false},
                    {"qwerty!", false},
                    {"Семен", true},
                    {"Артём", true},
                    {null, false}
            });
        }
        @Test
        public void isAuthorNameValid() {
            boolean actual = BookValidator.isAuthorNameValid(authorName);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedAuthorSurnamePart {
        private final String authorSurname;
        private final boolean expectedValue;

        public ParametrizedAuthorSurnamePart(String authorSurname, boolean expectedValue) {
            this.authorSurname = authorSurname;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> authorSurnameData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"", false},
                    {"John", true},
                    {"Erikh Marya", true},
                    {"john123", false},
                    {"qwerty!", false},
                    {"Семен", true},
                    {"Артём", true},
                    {null, false}
            });
        }
        @Test
        public void isAuthorSurnameValid() {
            boolean actual = BookValidator.isAuthorSurnameValid(authorSurname);
            assertEquals(expectedValue, actual);
        }

    }

    @RunWith(Parameterized.class)
    public static class ParametrizedAuthorPatronymicPart {
        private final String authorPatronymic;
        private final boolean expectedValue;

        public ParametrizedAuthorPatronymicPart(String authorPatronymic, boolean expectedValue) {
            this.authorPatronymic = authorPatronymic;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> authorPatronymicData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"", false},
                    {"John", true},
                    {"Erikh Marya", true},
                    {"john123", false},
                    {"qwerty!", false},
                    {"Семен", true},
                    {"Артём", true},
                    {null, false}
            });
        }
        @Test
        public void isAuthorSurnameValid() {
            boolean actual = BookValidator.isAuthorSurnameValid(authorPatronymic);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedLocationPart {
        private final String locationValue;
        private final boolean expectedValue;

        public ParametrizedLocationPart(String locationValue, boolean expectedValue) {
            this.locationValue = locationValue;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> locationData() {
            return Arrays.asList(new Object[][]{
                    {"storage", true},
                    {"online_order", true},
                    {"subscription", true},
                    {"test", false},
                    {"", false},
                    {null, false}
            });
        }

        @Test
        public void isLocationValid() {
            boolean actual = BookValidator.isBookLocationValid(locationValue);
            assertEquals(expectedValue, actual);
        }
    }


}