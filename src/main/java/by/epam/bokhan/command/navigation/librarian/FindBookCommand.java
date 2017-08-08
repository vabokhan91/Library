package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 04.08.2017.
 */
public class FindBookCommand extends AbstractCommand {
    public FindBookCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);

        String page = ConfigurationManager.getProperty("path.page.book.found_book_page");
        content.insertParameter(PAGE, page);

        content.insertParameter(INVALIDATE, false);
    }
}
