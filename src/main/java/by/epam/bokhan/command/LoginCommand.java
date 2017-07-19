package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.logic.LoginLogic;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by vbokh on 13.07.2017.
 */
public class LoginCommand extends AbstractCommand {

    public LoginCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws SQLException {

        super.execute(content);


        if (content.getRequestParameters().get("isValid").equals("true")) {
            String page = ConfigurationManager.getProperty("path.page.main");
            content.insertAttributes("page", page);
        }else {
            content.insertAttributes("errorLoginPassMessage", MessageManager.getProperty("message.loginerror"));
            String page = ConfigurationManager.getProperty("path.page.index");
            content.insertAttributes("page", page);
        }
        content.insertAttributes("invalidate", "false");
    }
}
