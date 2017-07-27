package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 17.07.2017.
 */
public class RemoveUserCommand extends AbstractCommand{

    public RemoveUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {

        try {
            super.execute(content);
            String page;
            if ((Boolean) content.getRequestParameters().get(IS_USER_DELETED))  {
                page = TO_SUCCESS_REMOVE_PAGE_COMMAND;
            }
            else {
                page = TO_FAIL_REMOVE_PAGE_COMMAND;
            }
            content.insertParameter(PAGE, page);
            content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
        } catch (ReceiverException e) {
            String page = ConfigurationManager.getProperty(ERROR_PAGE);
            content.insertParameter(PAGE, page);
        }
        content.insertParameter(INVALIDATE, false);
    }
}
