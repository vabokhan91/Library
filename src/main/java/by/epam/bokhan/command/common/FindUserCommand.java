package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.common.CommonConstant.*;

public class FindUserCommand extends AbstractCommand {

    public FindUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{
            super.execute(content);
            String page = ConfigurationManager.getProperty(USER_INFO_PAGE);
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
    }
}
