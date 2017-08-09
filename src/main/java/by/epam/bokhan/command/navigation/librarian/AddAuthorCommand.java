package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 05.08.2017.
 */
public class AddAuthorCommand extends AbstractCommand {
    public AddAuthorCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);
        String page = "/controller?command=to_add_author_page";

        content.insertParameter(PAGE, page);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);

        content.insertParameter(INVALIDATE, false);

    }
}
