package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 06.08.2017.
 */
public class DeleteBookCommand extends AbstractCommand {
    public DeleteBookCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);

        String page = "/controller?command=to_book_delete_result_page";
        if ((Boolean) content.getRequestParameters().get("isBookDeleted")) {
            content.insertAttribute("bookDeleteStatus", MessageManager.getProperty("label.book.delete_success"));
        } else {
            content.insertAttribute("bookDeleteStatus", MessageManager.getProperty("label.book.book_was_not_deleted"));
        }
        content.insertParameter(PAGE, page);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);

        content.insertParameter(INVALIDATE, false);

    }
}
