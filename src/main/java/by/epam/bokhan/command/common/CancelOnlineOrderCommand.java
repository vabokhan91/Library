package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.common.CommonConstant.*;

public class CancelOnlineOrderCommand extends AbstractCommand {

    public CancelOnlineOrderCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, CANCEL_ONLINE_ORDER_STATUS_COMMAND);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);

        content.insertParameter(INVALIDATE, false);
    }
}
