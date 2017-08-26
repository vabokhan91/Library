package by.epam.bokhan.command.navigation;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;


public class ToMainPage extends AbstractCommand{

    public ToMainPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        String page = ConfigurationManager.getProperty(TO_MAIN_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}