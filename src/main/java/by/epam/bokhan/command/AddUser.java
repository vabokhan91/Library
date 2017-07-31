package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 16.07.2017.
 */
public class AddUser extends AbstractCommand {

    public AddUser(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {

        try {
            super.execute(content);
            String page;
            if ((Boolean) content.getRequestParameters().get(USER_IS_ADDED)) {
                page = TO_USER_ADDED_PAGE_COMMAND;
            }
            else {
                page = TO_USER_NOT_ADDED_PAGE_COMMAND;
            }
            content.insertParameter(PAGE, page);
            content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
        } catch (ReceiverException e) {
            String page = ConfigurationManager.getProperty(TO_USER_NOT_ADDED_PAGE_COMMAND);
            content.insertParameter(PAGE, page);
        }
        content.insertParameter(INVALIDATE, false);

    }
}
