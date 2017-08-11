package by.epam.bokhan.command.navigation.user;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 11.08.2017.
 */
public class ToUserFindBookCommand extends AbstractCommand {
    public ToUserFindBookCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        String page = ConfigurationManager.getProperty("path.page.user.find_book");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);

    }
}
