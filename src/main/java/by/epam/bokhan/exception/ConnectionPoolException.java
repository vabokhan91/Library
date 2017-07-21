package by.epam.bokhan.exception;

/**
 * Created by vbokh on 21.07.2017.
 */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
