package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 04.08.2017.
 */
public class GetExplicitBookInfoCommand extends AbstractCommand{
    public GetExplicitBookInfoCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {

        super.execute(content);

//        ...
        String page = "/controller?command=to_explicit_book_info_page";
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);

    }
}
