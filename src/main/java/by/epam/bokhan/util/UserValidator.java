package by.epam.bokhan.util;

import by.epam.bokhan.entity.Role;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserValidator {

    private static final String REGEX_FOR_PASSWORD = "[\\w!()*&^%$@#]{3,12}";
    private static final String REGEX_FOR_LOGIN = "[\\w!()*&^%$@]{5,12}";
    private static final String REGEX_FOR_NAME = "[a-zA-Z\\s]{1,40}|([а-яА-ЯёЁ\\s]{1,40})";
    private static final String REGEX_FOR_SURNAME = "[a-zA-Z\\s]{1,40}|([а-яА-ЯёЁ\\s]{1,40})";
    private static final String REGEX_FOR_PATRONYMIC = "[a-zA-Z\\s]{1,40}|([а-яА-ЯёЁ\\s]{1,40})";
    private static final String REGEX_FOR_MOBILE_PHONE = "^(\\+)?((\\d{2,3}){1}[\\- ]?){1}(\\d{1,2}\\-?\\d{3}\\-?\\d{2}\\-?\\d{2})$";
    private static final String REGEX_FOR_LIBRARY_CARD = "\\d{1,5}";
    private static final String REGEX_FOR_USER_ID = "\\d{1,5}";
    private static final String REGEX_FOR_USER_ROLE = "[a-zA-Zа-яА-Я_]{1,30}";

    /*Checks if password is valid*/
    public static boolean isPasswordValid(String password) {
        boolean isPasswordValid = false;
        if (password != null) {
            Pattern passwordPattern = Pattern.compile(REGEX_FOR_PASSWORD);
            Matcher passwordMatcher = passwordPattern.matcher(password);
            isPasswordValid = passwordMatcher.matches();
        }
        return isPasswordValid;
    }
    /*Checks if passed passwords are equal*/
    public static boolean isPasswordsEquals(String password, String confirmPassword) {
        return !(password == null || password.isEmpty()) && password.equals(confirmPassword);
    }
    /*Checks if login is valid*/
    public static boolean isLoginValid(String login) {
        boolean isLoginValid = false;
        if (login != null) {
            Pattern patternForLogin = Pattern.compile(REGEX_FOR_LOGIN);
            Matcher matcherForLogin = patternForLogin.matcher(login);
            isLoginValid = matcherForLogin.matches();
        }
        return isLoginValid;
    }
    /*Checks if user name is valid*/
    public static boolean isUserNameValid(String name) {
        boolean isNameValid = false;
        if (name != null) {
            Pattern patternForName = Pattern.compile(REGEX_FOR_NAME);
            Matcher matcherForName = patternForName.matcher(name);
            isNameValid = matcherForName.matches();
        }
        return isNameValid;
    }
    /*Checks if user surname if valid*/
    public static boolean isUserSurnameValid(String surname) {
        boolean isSurnameValid = false;
        if (surname != null) {
            Pattern patternForSurname = Pattern.compile(REGEX_FOR_SURNAME);
            Matcher matcherForSurname = patternForSurname.matcher(surname);
            isSurnameValid = matcherForSurname.matches();
        }
        return isSurnameValid;
    }
    /*Checks if user patronymic is valid*/
    public static boolean isUserPatronymicValid(String patronymic) {
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
    /*Checks if user address is valid*/
    public static boolean isUserAddressValid(String address) {
        return address != null && !address.isEmpty();
    }
    /* Checks if user mobile phone is valid */
    public static boolean isUserMobilePhoneValid(String mobilePhone) {
        boolean isMobilePhoneValid = false;
        if (mobilePhone != null) {
            Pattern patternForMobilePhone = Pattern.compile(REGEX_FOR_MOBILE_PHONE);
            Matcher matcherForMobilePhone = patternForMobilePhone.matcher(mobilePhone);
            isMobilePhoneValid = matcherForMobilePhone.matches();
        }
        return isMobilePhoneValid;
    }

    /*Checks if user library card id is valid*/
    public static boolean isLibraryCardIdValid(String libraryCard) {
        boolean isLibraryCardValid = false;
        if (libraryCard != null) {
            Pattern patternForLibraryCard = Pattern.compile(REGEX_FOR_LIBRARY_CARD);
            Matcher matcherForLibraryCard = patternForLibraryCard.matcher(libraryCard);
            isLibraryCardValid = matcherForLibraryCard.matches();
        }

        return isLibraryCardValid;
    }
    /*Checks if user id is valid*/
    public static boolean isUserIdValid(String userId) {
        boolean isUserIdValid = false;
        if (userId != null) {
            Pattern patternForUserId = Pattern.compile(REGEX_FOR_USER_ID);
            Matcher matcherForUserId = patternForUserId.matcher(userId);
            isUserIdValid = matcherForUserId.matches();
        }
        return isUserIdValid;
    }
    /*Checks if user role is valid*/
    public static boolean isUserRoleValid(String roleValue) {
        boolean isRoleValid = false;
        if (roleValue != null) {
            Pattern patternForUserRole = Pattern.compile(REGEX_FOR_USER_ROLE);
            Matcher matcherForUserRole = patternForUserRole.matcher(roleValue);
            if (matcherForUserRole.matches()) {
                Role[] roles = Role.values();
                for (Role role : roles) {
                    if (role.name().equals(roleValue.toUpperCase())) {
                        isRoleValid = true;
                    }
                }
            }
        }
        return isRoleValid;
    }

    /*Checks if string is empty*/
    public static boolean isStringEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
