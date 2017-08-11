package by.epam.bokhan.command.navigation.user;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 10.08.2017.
 */
public class ToUserMainPage extends AbstractCommand {
    public ToUserMainPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        String page = ConfigurationManager.getProperty("path.page.user.main_page");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);

    }
}
