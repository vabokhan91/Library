package by.epam.bokhan.command.admin;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;


public class BlockUserCommand extends AbstractCommand {

    public BlockUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{
            super.execute(content);
            content.insertParameter(PAGE, BLOCK_STATUS_PAGE_COMMAND);
            content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
            content.insertParameter(INVALIDATE, false);
    }
}
