package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

import static by.epam.bokhan.command.common.CommonConstant.*;

public class GetAllUsersCommand extends AbstractCommand {


    public GetAllUsersCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, ConfigurationManager.getProperty(SHOW_ALL_USERS_PAGE));
        content.insertParameter(INVALIDATE, false);

    }

}
