package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 17.07.2017.
 */
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
