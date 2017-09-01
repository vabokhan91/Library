package by.epam.bokhan.command.admin;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.admin.AdminConstant.*;



public class GetBlockedUsersCommand extends AbstractCommand{

    public GetBlockedUsersCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
            super.execute(content);
            content.insertParameter(PAGE, TO_UNBLOCK_USER_PAGE_COMMAND);
            content.insertParameter(INVALIDATE, false);
    }
}
