package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

import java.sql.SQLException;

/**
 * Created by vbokh on 17.07.2017.
 */
public class RemoveUserCommand extends AbstractCommand{
    public RemoveUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws SQLException {

        super.execute(content);

        String page = ConfigurationManager.getProperty("path.page.remove_user");
        content.insertAttributes("page", page);
        if (Boolean.parseBoolean((String)content.getRequestParameters().get("isUserDeleted")) ) {
            content.insertAttributes("user_remove_status", MessageManager.getProperty("message.remove_user_true"));
        }
        else {
            content.insertAttributes("user_remove_status", MessageManager.getProperty("message.remove_user_false"));
        }
        content.insertAttributes("invalidate", "false");
    }
}
