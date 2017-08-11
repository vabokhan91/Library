package by.epam.bokhan.command.navigation.user;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 11.08.2017.
 */
public class UserFindBookCommand extends AbstractCommand {
    public UserFindBookCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);

        String page = ConfigurationManager.getProperty("path.page.book.found_book_for_user");
        content.insertParameter(PAGE, page);

        content.insertParameter(INVALIDATE, false);
    }
}
