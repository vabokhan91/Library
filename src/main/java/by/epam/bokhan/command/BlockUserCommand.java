package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 24.07.2017.
 */
public class BlockUserCommand extends AbstractCommand{

    public BlockUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{


            super.execute(content);
            String page;
            if ((Boolean) content.getRequestParameters().get(IS_USER_BLOCKED) ) {
                page = TO_BLOCK_USER_SUCCESS_PAGE_COMMAND;
            }
            else {
                page = TO_BLOCK_USER_FAILED_PAGE_COMMAND;
            }
            content.insertParameter(PAGE, page);
            content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);

        content.insertParameter(INVALIDATE, false);
    }
}
