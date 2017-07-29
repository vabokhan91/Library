package by.epam.bokhan.command.pagecommand.commoncommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 29.07.2017.
 */
public class GetUsersForRemoval extends AbstractCommand {
    public GetUsersForRemoval(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{
        try {
            super.execute(content);
            String page = "/controller?command=to_remove_user_page";
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
        } catch (ReceiverException e) {
            String page = TO_FAIL_REMOVE_PAGE_COMMAND;
            content.insertParameter(PAGE, page);
            throw e;
        }
        content.insertParameter(INVALIDATE, false);
    }

}
