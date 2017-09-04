package by.epam.bokhan.command.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.CommandException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.librarian.LibrarianConstant.*;

public class DeletePublisherCommand extends AbstractCommand {

    public DeletePublisherCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws CommandException {
        super.execute(content);
        content.insertParameter(PAGE, TO_DELETE_PUBLISHER_PAGE_COMMAND);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
        content.insertParameter(INVALIDATE, false);
    }
}
