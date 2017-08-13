package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;


public class RemoveUserCommand extends AbstractCommand {

    private final String TO_REMOVE_USER_STATUS_COMMAND = "/controller?command=to_remove_user_status_page";

    public RemoveUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, TO_REMOVE_USER_STATUS_COMMAND);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);

        content.insertParameter(INVALIDATE, false);
    }
}
