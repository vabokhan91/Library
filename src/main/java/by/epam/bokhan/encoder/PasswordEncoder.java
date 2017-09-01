package by.epam.bokhan.encoder;

import org.apache.commons.codec.digest.DigestUtils;


public class PasswordEncoder {
    public static String encodePassword(String password) {
        String hashedPassword = DigestUtils.md5Hex(password);
        return hashedPassword;
    }
}
