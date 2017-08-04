package by.epam.bokhan.receiver;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;

/**
 * Created by vbokh on 01.08.2017.
 */
public interface BookReceiver extends Receiver {
    void getAllBooks(RequestContent requestContent) throws ReceiverException;
}
