package by.epam.bokhan.command.navigation;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;


public class ToOnlineOrdersPageCommand extends AbstractCommand {

    public ToOnlineOrdersPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        String page = ConfigurationManager.getProperty(USERS_ONLINE_ORDERS);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
