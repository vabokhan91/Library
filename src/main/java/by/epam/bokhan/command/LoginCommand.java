package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 13.07.2017.
 */
public class LoginCommand extends AbstractCommand {

    public LoginCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {

        try {
            super.execute(content);

            if (Boolean.parseBoolean((String) content.getRequestParameters().get("isValid"))) {
                User user = (User)content.getRequestParameters().get("user");
                int role = user.getRoleId();
                if (role == 4) {
                    String page = ConfigurationManager.getProperty("path.page.admin");
                    content.insertParameter("page", page);
                } else if (role == 3) {
                    String page = ConfigurationManager.getProperty("path.page.librarian");
                    content.insertParameter("page", page);
                } else if (role == 2) {
                    String page = ConfigurationManager.getProperty("path.page.authorized_user_main");
                    content.insertParameter("page", page);
                }
            } else {
                content.insertParameter("errorLoginPassMessage", MessageManager.getProperty("message.loginerror"));
                String page = ConfigurationManager.getProperty("path.page.index");
                content.insertParameter("page", page);
            }
        } catch (DAOException e) {
            String page = ConfigurationManager.getProperty("path.page.error");
            content.insertParameter("page", page);

        }
        content.insertParameter("invalidate", "false");
    }
}
