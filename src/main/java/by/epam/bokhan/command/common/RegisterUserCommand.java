package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.CommandException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

import static by.epam.bokhan.command.common.CommonConstant.*;


public class RegisterUserCommand extends AbstractCommand {

    public RegisterUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws CommandException {
        super.execute(content);
        boolean isLoginExist =(Boolean) content.getSessionAttributes().get(IS_LOGIN_EXIST);
        if (isLoginExist) {
            content.insertParameter(PAGE, TO_REGISTRATION_PAGE_COMMAND);
            content.insertAttribute(IS_LOGIN_EXIST, isLoginExist);
        }else {
            content.insertParameter(PAGE, REGISTRATION_RESULT_PAGE_COMMAND);
        }
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
        content.insertParameter(INVALIDATE, false);
    }
}
