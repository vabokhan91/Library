package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;


public class GetExplicitUserInfoCommand extends AbstractCommand {

    public GetExplicitUserInfoCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, TO_EXPLICIT_USER_INFO_PAGE_COMMAND);
        content.insertParameter(INVALIDATE, false);
    }
}
