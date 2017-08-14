package by.epam.bokhan.command.navigation.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;


public class ToExplicitUserPage extends AbstractCommand{

    public ToExplicitUserPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        String page = ConfigurationManager.getProperty(TO_EXPLICIT_USER_INFO_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
