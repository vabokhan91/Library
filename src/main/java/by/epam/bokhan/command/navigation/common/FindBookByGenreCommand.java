package by.epam.bokhan.command.navigation.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;


public class FindBookByGenreCommand extends AbstractCommand {

    public FindBookByGenreCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        super.execute(content);
        content.insertParameter(PAGE, TO_MAIN_PAGE_COMMAND);
        content.insertParameter(INVALIDATE, false);
    }
}
