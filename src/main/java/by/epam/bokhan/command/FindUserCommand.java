package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

import java.sql.SQLException;

/**
 * Created by vbokh on 17.07.2017.
 */
public class FindUserCommand extends AbstractCommand {
    public FindUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws SQLException {

        super.execute(content);

        String page = ConfigurationManager.getProperty("path.page.user_info");
        content.insertAttributes("page", page);
        if (content.getRequestParameters().get("foundUser") != null) {
            content.insertAttributes("user_found_status", MessageManager.getProperty("message.found_user_true"));
        } else {
            content.insertAttributes("user_found_status", MessageManager.getProperty("message.found_user_false"));
        }
        content.insertAttributes("invalidate", "false");
    }
}
