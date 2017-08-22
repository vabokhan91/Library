package by.epam.bokhan.command.navigation;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;


public class ToUserFindBookCommand extends AbstractCommand {

    public ToUserFindBookCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        String page = ConfigurationManager.getProperty(FIND_BOOK_FOR_USER);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
