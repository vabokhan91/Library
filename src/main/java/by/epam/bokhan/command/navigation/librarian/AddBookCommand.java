package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 05.08.2017.
 */
public class AddBookCommand extends AbstractCommand {
    public AddBookCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);

        String page = "/controller?command=to_add_book_page";
        /*if ((Boolean) content.getRequestParameters().get("isBookAdded")) {
            content.insertAttribute("bookAddStatus", MessageManager.getProperty("message.book_added_successfully"));
        } else {
            content.insertAttribute("bookAddStatus", MessageManager.getProperty("message.book_was_not_added"));
        }*/
        content.insertParameter(PAGE, page);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
        content.insertParameter(INVALIDATE, false);

    }
}
