package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;
import by.epam.bokhan.exception.CommandException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.common.CommonConstant.*;

public class LoginCommand extends AbstractCommand {


    public LoginCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws CommandException {
        super.execute(content);
        if ((Boolean) content.getRequestParameters().get(IS_VALID)) {
            User user = (User) content.getSessionAttributes().get(USER);
            Role role = user.getRole();
            switch (role) {
                case CLIENT:
                    content.insertParameter(PAGE, TO_USER_MAIN_PAGE);
                    content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
                    break;
                case LIBRARIAN:
                    content.insertParameter(PAGE, TO_LIBRARIAN_MAIN_PAGE_COMMAND);
                    content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
                    break;
                case ADMINISTRATOR:
                    content.insertParameter(PAGE, TO_ADMIN_MAIN_PAGE);
                    content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
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
