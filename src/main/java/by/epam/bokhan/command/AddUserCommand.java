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
//            String page = ConfigurationManager.getProperty("path.page.adduser");


            String page;
            if ((Boolean) content.getRequestParameters().get("userIsAdded")) {
//               content.insertParameter("user_insert_status", MessageManager.getProperty("message.add_user_true"));
                page = "/controller?command=to_user_added_page";
            }
            else {
//                content.insertParameter("user_remove_status", MessageManager.getProperty("message.remove_user_false"));
                page = "/controller?command=to_user_not_added_page";
            }
            content.insertParameter("page", page);
            content.insertParameter("type_of_transition", "redirect");
        } catch (DAOException e) {
            String page = ConfigurationManager.getProperty("path.page.error");
            content.insertParameter("page", page);
        }
        content.insertParameter("invalidate", "false");

    }
}
