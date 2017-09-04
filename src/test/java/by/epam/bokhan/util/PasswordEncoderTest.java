package by.epam.bokhan.util;

import by.epam.bokhan.util.PasswordEncoder;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordEncoderTest {
    private static final String TEST_PASSWORD = "1234";
    private static final String EXPECTED_HASHED_PASSWORD = "f92053adfd331a88ae14117f3d4c3efe";

    @Test
    public void encodePasswordTest() throws Exception {
        String hashedPassword = PasswordEncoder.encodePassword(TEST_PASSWORD);
        assertEquals(EXPECTED_HASHED_PASSWORD, hashedPassword);
    }
}