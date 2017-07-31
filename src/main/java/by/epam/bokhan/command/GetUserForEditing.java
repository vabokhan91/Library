package by.epam.bokhan.command;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 30.07.2017.
 */
public class GetUserForEditing extends AbstractCommand {



    public GetUserForEditing(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        try {
            super.execute(content);
            String page = TO_EDIT_USER_PAGE_COMMAND;
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
        } catch (ReceiverException e) {
            String page = TO_USER_NOT_EDITED_PAGE;
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
            throw e;
        }

    }

}
