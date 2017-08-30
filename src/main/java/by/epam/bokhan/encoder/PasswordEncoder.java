package by.epam.bokhan.encoder;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by vbokh on 30.08.2017.
 */
public class PasswordEncoder {
    public static String encodePassword(String password) {
        String hashedPassword = DigestUtils.md5Hex(password);
        return hashedPassword;
    }
}
