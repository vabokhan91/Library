package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 10.08.2017.
 */
public class DeleteGenreCommand extends AbstractCommand {
    public DeleteGenreCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);

        String page = "/controller?command=to_delete_genre_page";

        content.insertParameter(PAGE, page);
        content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);

        content.insertParameter(INVALIDATE, false);

    }
}
