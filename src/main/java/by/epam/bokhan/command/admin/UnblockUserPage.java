package by.epam.bokhan.command.admin;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.UserReceiverImpl;
import static by.epam.bokhan.command.admin.AdminConstant.*;


public class UnblockUserPage extends AbstractCommand {

    public UnblockUserPage(UserReceiverImpl userReceiver) {
        super(userReceiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty(TO_UNBLOCK_USER_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
