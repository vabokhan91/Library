package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 16.07.2017.
 */
public class AddUserCommand extends AbstractCommand {
    public AddUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {

        try {
            super.execute(content);
            String page = ConfigurationManager.getProperty("path.page.adduser");
            content.insertParameter("page", page);
            if(Boolean.parseBoolean((String) content.getRequestParameters().get("userIsAdded"))){
                content.insertParameter("user_insert_status", MessageManager.getProperty("message.add_user_true"));
            }else {
                content.insertParameter("user_insert_status", MessageManager.getProperty("message.add_user_false"));
            }

        } catch (DAOException e) {
            String page = ConfigurationManager.getProperty("path.page.error");
            content.insertParameter("page", page);
        }
        content.insertParameter("invalidate", "false");
    }
}
