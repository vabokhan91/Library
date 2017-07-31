package by.epam.bokhan.command.navigation.admin;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 27.07.2017.
 */
public class GetNotBlockedUsers extends AbstractCommand{

    public GetNotBlockedUsers(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        try {
            super.execute(content);
            String page = TO_BLOCK_USER_PAGE_COMMAND;
            content.insertParameter(PAGE, page);
        } catch (ReceiverException e) {
            String page = TO_BLOCK_USER_FAILED_PAGE_COMMAND;
            content.insertParameter(PAGE, page);
            throw e;
        }
        content.insertParameter(INVALIDATE, false);
    }
}
