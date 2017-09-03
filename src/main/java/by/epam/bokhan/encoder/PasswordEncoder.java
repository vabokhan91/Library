package by.epam.bokhan.encoder;

import org.apache.commons.codec.digest.DigestUtils;


public class PasswordEncoder {
    /*Encodes string to md5*/
    public static String encodePassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
