package by.epam.bokhan.command.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.librarian.LibrarianConstant.*;

public class GetBookForEditing extends AbstractCommand{

    public GetBookForEditing(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        String page = ConfigurationManager.getProperty(EDIT_BOOK_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);


    }
}
