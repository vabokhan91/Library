package by.epam.bokhan.command.navigation.admin;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 27.07.2017.
 */
public class GetBlockedUsersCommand extends AbstractCommand{

    public GetBlockedUsersCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

            super.execute(content);
            String page = TO_UNBLOCK_USER_PAGE_COMMAND;
            content.insertParameter(PAGE, page);

        content.insertParameter(INVALIDATE, false);
    }
}
