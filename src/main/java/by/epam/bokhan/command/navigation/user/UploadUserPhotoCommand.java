package by.epam.bokhan.command.navigation.user;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 18.08.2017.
 */
public class UploadUserPhotoCommand extends AbstractCommand {

    public UploadUserPhotoCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, TO_USER_MAIN_PAGE);
        content.insertParameter(TYPE_OF_TRANSITION,REDIRECT);
        content.insertParameter(INVALIDATE, false);
    }
}
