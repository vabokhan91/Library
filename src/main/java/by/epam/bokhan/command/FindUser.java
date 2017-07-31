package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 17.07.2017.
 */
public class FindUser extends AbstractCommand {

    public FindUser(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {
        try {
            super.execute(content);
            String page = ConfigurationManager.getProperty(USER_INFO_PAGE);
            content.insertParameter(PAGE, page);
            if (content.getRequestParameters().get(FOUND_USER) != null) {
                content.insertParameter(USER_FOUND_STATUS, MessageManager.getProperty(MESSAGE_FOUND_USER_TRUE));
            } else {
                content.insertParameter(USER_FOUND_STATUS, MessageManager.getProperty(MESSAGE_FOUND_USER_FALSE));
            }
        } catch (ReceiverException e) {
//            error page
            String page = ConfigurationManager.getProperty(ERROR_PAGE);
            content.insertParameter(PAGE, page);
        }
        content.insertParameter(INVALIDATE, false);
    }
}
