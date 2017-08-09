package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 09.08.2017.
 */
public class AddPublisherCommand extends AbstractCommand {
    public AddPublisherCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);

        String page = "/controller?command=to_add_publisher_page";

        content.insertParameter(PAGE, page);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
        content.insertParameter(INVALIDATE, false);

    }
}
