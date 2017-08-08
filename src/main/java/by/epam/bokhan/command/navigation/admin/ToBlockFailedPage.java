package by.epam.bokhan.command.navigation.admin;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

public class ToBlockFailedPage extends AbstractCommand{

    public ToBlockFailedPage(Receiver receiver) {
        super(receiver);
    }
    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty(BLOCK_USER_FAILED_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
