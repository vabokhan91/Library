package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 24.07.2017.
 */
public class BlockUserCommand extends AbstractCommand{

    private final String TO_BLOCK_STATUS_PAGE = "/controller?command=to_block_status_page";

    public BlockUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{
            super.execute(content);
            String page = TO_BLOCK_STATUS_PAGE;
            content.insertParameter(PAGE, page);
            content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
            content.insertParameter(INVALIDATE, false);
    }
}
