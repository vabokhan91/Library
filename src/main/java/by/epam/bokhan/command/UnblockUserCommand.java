package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 24.07.2017.
 */
public class UnblockUserCommand extends AbstractCommand {

    private final String TO_UNBLOCK_STATUS_PAGE = "/controller?command=to_unblock_status_page";

    public UnblockUserCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{
            super.execute(content);
            String page = TO_UNBLOCK_STATUS_PAGE;
            content.insertParameter(PAGE, page);
            content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
            content.insertParameter(INVALIDATE, false);

}}
