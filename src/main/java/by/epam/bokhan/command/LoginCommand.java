package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;
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

            super.execute(content);

            if ((Boolean) content.getRequestParameters().get(IS_VALID)) {
                User user = (User)content.getSessionAttributes().get(USER);
                Role role = user.getRole();
                String page;

                switch (role) {
                    case CLIENT:
                        page = ConfigurationManager.getProperty(USER_MAIN_PAGE);
                        content.insertParameter(PAGE, page);
                        break;
                    case LIBRARIAN:
                        page = ConfigurationManager.getProperty(LIBRARIAN_PAGE);
                        content.insertParameter(PAGE, page);
                        break;
                    case ADMINISTRATOR:
                        page = ConfigurationManager.getProperty(ADMIN_PAGE);
                        content.insertParameter(PAGE, page);
                        break;
                }
            } else {
                content.insertAttribute(ERROR_LOGIN_PASS_MESSAGE, MessageManager.getProperty(LOGIN_ERROR_MESSAGE));
                String page = ConfigurationManager.getProperty(INDEX_PAGE);
                content.insertParameter(PAGE, page);
                content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
            }

        content.insertParameter(INVALIDATE, false);
    }
}
