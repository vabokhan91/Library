package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 17.07.2017.
 */
public class FindUserCommand extends AbstractCommand {
    public FindUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {
        try {
            super.execute(content);
            String page = ConfigurationManager.getProperty("path.page.user_info");
            content.insertParameter("page", page);
            if (content.getRequestParameters().get("foundUser") != null) {
                content.insertParameter("user_found_status", MessageManager.getProperty("message.found_user_true"));
            } else {
                content.insertParameter("user_found_status", MessageManager.getProperty("message.found_user_false"));
            }
        } catch (DAOException e) {
//            error page
            String page = ConfigurationManager.getProperty("path.page.error");
            content.insertParameter("page", page);
        }
        content.insertParameter("invalidate", "false");
    }
}
