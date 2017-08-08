package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 03.08.2017.
 */
public class ToFindBookPage extends AbstractCommand {

    public ToFindBookPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty("path.page.book.find_book");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
