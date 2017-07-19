package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.controller.Controller;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

import java.sql.SQLException;

/**
 * Created by vbokh on 16.07.2017.
 */
public class AddUserCommand extends AbstractCommand {
    public AddUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws SQLException {

        super.execute(content);

        String page = ConfigurationManager.getProperty("path.page.adduser");
        content.insertAttributes("page", page);
        if (content.getRequestParameters().get("userIsAdded") == "true") {
            content.insertAttributes("user_insert_status", MessageManager.getProperty("message.add_user_true"));
        }
        else {
            content.insertAttributes("user_insert_status", MessageManager.getProperty("message.add_user_false"));
        }
        content.insertAttributes("invalidate", "false");
    }
}
