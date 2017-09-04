package by.epam.bokhan.util;

import org.apache.commons.codec.digest.DigestUtils;


public class PasswordEncoder {
    private static final String SALT = "SaltForPassword123!@#";
    /*Encodes string to md5*/
    public static String encodePassword(String password) {
        return DigestUtils.md5Hex(password + SALT);
    }
}
