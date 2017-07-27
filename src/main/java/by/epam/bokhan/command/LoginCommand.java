package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 13.07.2017.
 */
public class LoginCommand extends AbstractCommand {

    public LoginCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)throws ReceiverException {
//think about redirect
        try {
            super.execute(content);

            if ((Boolean) content.getRequestParameters().get(IS_VALID)) {
                User user = (User)content.getRequestParameters().get(USER);
                int role = user.getRoleId();
                if (role == 4) {
                    String page = ConfigurationManager.getProperty(ADMIN_PAGE);
                    content.insertParameter(PAGE, page);
                } else if (role == 3) {
                    String page = ConfigurationManager.getProperty(LIBRARIAN_PAGE);
                    content.insertParameter(PAGE, page);
                } else if (role == 2) {
                    String page = ConfigurationManager.getProperty(USER_MAIN_PAGE);
                    content.insertParameter(PAGE, page);
                }
            } else {
                content.insertParameter(ERROR_LOGIN_PASS_MESSAGE, MessageManager.getProperty(LOGIN_ERROR_MESSAGE));
                String page = ConfigurationManager.getProperty(INDEX_PAGE);
                content.insertParameter(PAGE, page);
            }
        } catch (ReceiverException e) {
            content.insertParameter(ERROR_LOGIN_PASS_MESSAGE, MessageManager.getProperty(LOGIN_ERROR_MESSAGE));
            String page = ConfigurationManager.getProperty(INDEX_PAGE);
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
            throw new ReceiverException(e);
        }
        content.insertParameter(INVALIDATE, false);
    }
}
