package by.epam.bokhan.command.pagecommand.admincommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 27.07.2017.
 */
public class ToShowBlockedUsersPage extends AbstractCommand{
    public ToShowBlockedUsersPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
//        String page = ConfigurationManager.getProperty(BLOCK_USER_PAGE);
        String page = ConfigurationManager.getProperty("path.page.blocked_users_page");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
