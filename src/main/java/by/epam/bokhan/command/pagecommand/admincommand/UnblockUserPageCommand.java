package by.epam.bokhan.command.pagecommand.admincommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.UserReceiverImpl;

/**
 * Created by vbokh on 24.07.2017.
 */
public class UnblockUserPageCommand extends AbstractCommand {

    public UnblockUserPageCommand(UserReceiverImpl userReceiver) {
        super(userReceiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty(TO_UNBLOCK_USER_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
