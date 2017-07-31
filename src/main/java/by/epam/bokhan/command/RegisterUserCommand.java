package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 22.07.2017.
 */
public class RegisterUserCommand extends AbstractCommand {

    public RegisterUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

            super.execute(content);
            String page = ConfigurationManager.getProperty(SUCCESS_REGISTRATION_PAGE);
            content.insertParameter(PAGE, page);
            if((Boolean) content.getRequestParameters().get(USER_IS_ADDED)){
                content.insertParameter(USER_INSERT_STATUS, MessageManager.getProperty(MESSAGE_ADD_USER_TRUE));
            }else {
                content.insertParameter(USER_INSERT_STATUS, MessageManager.getProperty(MESSAGE_ADD_USER_FALSE));
            }


        content.insertParameter(INVALIDATE, false);
    }
}
