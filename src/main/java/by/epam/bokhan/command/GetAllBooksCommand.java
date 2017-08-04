package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 01.08.2017.
 */
public class GetAllBooksCommand extends AbstractCommand {
    public GetAllBooksCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);
        String page = "/controller?command=to_show_books_page";
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
