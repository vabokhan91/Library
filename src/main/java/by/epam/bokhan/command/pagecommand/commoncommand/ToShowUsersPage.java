package by.epam.bokhan.command.pagecommand.commoncommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 29.07.2017.
 */
public class ToShowUsersPage extends AbstractCommand{
    public ToShowUsersPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        String page = ConfigurationManager.getProperty("path.page.show_all_users");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
