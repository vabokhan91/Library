package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

public class GetUserForEditingCommand extends AbstractCommand {

    public GetUserForEditingCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, TO_EDIT_USER_PAGE_COMMAND);
        content.insertParameter(INVALIDATE, false);
    }

}
