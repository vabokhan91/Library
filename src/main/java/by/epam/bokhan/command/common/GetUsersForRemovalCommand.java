package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.common.CommonConstant.*;

public class GetUsersForRemovalCommand extends AbstractCommand {

    public GetUsersForRemovalCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, TO_REMOVE_USER_PAGE_COMMAND);
        content.insertParameter(INVALIDATE, false);
        content.insertParameter(INVALIDATE, false);
    }

}
