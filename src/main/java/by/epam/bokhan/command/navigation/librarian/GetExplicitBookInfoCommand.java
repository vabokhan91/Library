package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;


public class GetExplicitBookInfoCommand extends AbstractCommand{

    public GetExplicitBookInfoCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, ConfigurationManager.getProperty(TO_EXPLICIT_BOOK_INFO_PAGE));
        content.insertParameter(INVALIDATE, false);

    }
}
