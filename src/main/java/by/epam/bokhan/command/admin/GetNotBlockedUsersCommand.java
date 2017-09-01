package by.epam.bokhan.command.admin;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.admin.AdminConstant.*;



public class GetNotBlockedUsersCommand extends AbstractCommand{



    public GetNotBlockedUsersCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
            super.execute(content);
            content.insertParameter(PAGE, ConfigurationManager.getProperty(BLOCKED_USERS_PAGE));
            content.insertParameter(INVALIDATE, false);
    }
}
