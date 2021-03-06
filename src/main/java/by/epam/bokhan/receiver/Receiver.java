package by.epam.bokhan.receiver;

import by.epam.bokhan.command.CommandType;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;


public interface Receiver {
    /**
     * Executes method doReceiver() of the passed command type
     * @param type type of command, which method doReceiver will be executed
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    default void action(CommandType type, RequestContent requestContent) throws ReceiverException {
        type.doReceiver(requestContent);
    }
}
