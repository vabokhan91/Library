package by.epam.bokhan.command.pagecommand.admincommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 27.07.2017.
 */
public class GetBlockedUsers extends AbstractCommand{
    public GetBlockedUsers(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        try {
            super.execute(content);
            String page = "/controller?command=to_unblock_user_page";
            content.insertParameter("page", page);
        } catch (ReceiverException e) {
            String page = TO_UNBLOCK_USER_FAILED_PAGE_COMMAND;
            content.insertParameter(PAGE, page);
            throw e;
        }
        content.insertParameter(INVALIDATE, false);
    }
}
