package by.epam.bokhan.command.user;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.CommandException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.user.UserConstant.*;


public class UploadUserPhotoCommand extends AbstractCommand {

    public UploadUserPhotoCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws CommandException {
        super.execute(content);
        content.insertParameter(PAGE, TO_USER_MAIN_PAGE);
        content.insertParameter(TYPE_OF_TRANSITION,REDIRECT);
        content.insertParameter(INVALIDATE, false);
    }
}
