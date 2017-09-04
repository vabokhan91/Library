package by.epam.bokhan.suite;


import by.epam.bokhan.util.PasswordEncoderTest;
import by.epam.bokhan.pool.ConnectionPoolTest;
import by.epam.bokhan.property.PropertyTest;
import by.epam.bokhan.util.BookValidatorTest;
import by.epam.bokhan.util.UserValidatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        PasswordEncoderTest.class,
        ConnectionPoolTest.class,
        PropertyTest.class,
        BookValidatorTest.class,
        UserValidatorTest.class
})
@RunWith(Suite.class)
public class SuiteTest {

}
