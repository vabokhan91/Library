package by.epam.bokhan.validator;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(Enclosed.class)
public class UserValidatorTest {

    @RunWith(Parameterized.class)
    public static class ParametrizedPasswordPart {
        private final String password;
        private final boolean expectedValue;

        public ParametrizedPasswordPart(String password, boolean expectedValue) {
            this.password = password;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> passwordData() {
            return Arrays.asList(new Object[][]{
                    {"1234", true},
                    {"12", false},
                    {"qwerty", true},
                    {"qwerty123", true},
                    {"qwerty123!", true},
                    {"qwerty123!@#", true},
                    {"qwerty123!@#123", false},
                    {null, false}
            });
        }

        @Test
        public void isPasswordValidTest() {
            boolean actual = UserValidator.isPasswordValid(password);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedLoginPart {
        private final String login;
        private final boolean expectedValue;

        public ParametrizedLoginPart(String login, boolean expectedValue) {
            this.login = login;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> loginData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"12", false},
                    {"qwerty", true},
                    {"qwerty123", true},
                    {"qwerty123!", true},
                    {"qwerty123!@", true},
                    {"qwerty123!@#123", false},
                    {null, false}
            });
        }

        @Test
        public void isLoginValidTest() {
            boolean actual = UserValidator.isLoginValid(login);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedUserNamePart {
        private final String userName;
        private final boolean expectedValue;

        public ParametrizedUserNamePart(String userName, boolean expectedValue) {
            this.userName = userName;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> nameData() {
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
        public void isNameValidTest() {
            boolean actual = UserValidator.isUserNameValid(userName);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedUserSurnamePart {
        private final String userSurname;
        private final boolean expectedValue;

        public ParametrizedUserSurnamePart(String userSurname, boolean expectedValue) {
            this.userSurname = userSurname;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> surnameData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"", false},
                    {"Snow", true},
                    {"snow123", false},
                    {"qwerty!", false},
                    {"Daenerys Stormborn", true},
                    {"Daenerys Stormborn of the House Targaryen", false},
                    {null, false}
            });
        }

        @Test
        public void isSurnameValidTest() {
            boolean actual = UserValidator.isUserSurnameValid(userSurname);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedUserPatronymicPart {
        private final String userPatronymic;
        private final boolean expectedValue;

        public ParametrizedUserPatronymicPart(String userPatronymic, boolean expectedValue) {
            this.userPatronymic = userPatronymic;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> patronymicData() {
            return Arrays.asList(new Object[][]{
                    {"1234", false},
                    {"", true},
                    {"Snow", true},
                    {"snow123", false},
                    {"qwerty!", false},
                    {"Daenerys Stormborn", true},
                    {"Daenerys Stormborn of the House Targaryen", false},
                    {null, true}
            });
        }

        @Test
        public void isSurnameValidTest() {
            boolean actual = UserValidator.isUserPatronymicValid(userPatronymic);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedMobilePhonePart {
        private final String mobilePhone;
        private final boolean expectedValue;

        public ParametrizedMobilePhonePart(String mobilePhone, boolean expectedValue) {
            this.mobilePhone = mobilePhone;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> mobilePhoneData() {
            return Arrays.asList(new Object[][]{
                    {"+375291234567", true},
                    {"375291234567", true},
                    {"", false},
                    {"+375-29-123-45-67", true},
                    {"+3752912345617", false},
                    {null, false}
            });
        }

        @Test
        public void isMobilephoneValidTest() {
            boolean actual = UserValidator.isUserMobilePhoneValid(mobilePhone);
            assertEquals(expectedValue, actual);
        }
    }


    @RunWith(Parameterized.class)
    public static class ParametrizedRolePart {
        private final String roleName;
        private final boolean expectedValue;

        public ParametrizedRolePart(String roleName, boolean expectedValue) {
            this.roleName = roleName;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> roleData() {
            return Arrays.asList(new Object[][]{
                    {"Administrator", true},
                    {"Client", true},
                    {"Librarian", true},
                    {"Security", false},
                    {"", false},
                    {null, false}
            });
        }

        @Test
        public void isUserRoleValid() {
            boolean actual = UserValidator.isUserRoleValid(roleName);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedLibraryCardPart {
        private final String libraryCardValue;
        private final boolean expectedValue;

        public ParametrizedLibraryCardPart(String libraryCardValue, boolean expectedValue) {
            this.libraryCardValue = libraryCardValue;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> libraryCardDataData() {
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
        public void isUserLibraryCardNumberValid() {
            boolean actual = UserValidator.isLibraryCardIdValid(libraryCardValue);
            assertEquals(expectedValue, actual);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedUserIdPart {
        private final String userIdValue;
        private final boolean expectedValue;

        public ParametrizedUserIdPart(String userIdValue, boolean expectedValue) {
            this.userIdValue = userIdValue;
            this.expectedValue = expectedValue;
        }

        @Parameterized.Parameters
        public static List<Object[]> userIdData() {
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
        public void isUserIdValueValid() {
            boolean actual = UserValidator.isUserIdValid(userIdValue);
            assertEquals(expectedValue, actual);
        }
    }

    public static class NonParametrizedPart {
        private static final String PASSWORD = "1234";
        private static final String CONFIRM_PASSWORD = "1234";
        private static final boolean expected = true;

        @Test
        public void isPasswordsEqual() {
            boolean actual = UserValidator.isPasswordsEquals(PASSWORD, CONFIRM_PASSWORD);
            assertEquals(expected, actual);
        }
    }


}