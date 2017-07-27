package by.epam.bokhan.exception;

/**
 * Created by vbokh on 25.07.2017.
 */
public class ReceiverException extends Exception{
    public ReceiverException() {
        super();
    }

    public ReceiverException(String message) {
        super(message);
    }

    public ReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiverException(Throwable cause) {
        super(cause);
    }
}
