package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 24.07.2017.
 */
public class BlockUserCommand extends AbstractCommand{
    public BlockUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {

        try {
            super.execute(content);
            String page;
            if ((Boolean) content.getRequestParameters().get("isUserBlocked") ) {
                page = "/controller?command=block_user_success_page";
            }
            else {
                page = "/controller?command=block_user_failed_page";
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
