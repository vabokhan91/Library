package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 04.08.2017.
 */
public class ToExplicitBookInfoPage extends AbstractCommand{
    public ToExplicitBookInfoPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty("path.page.book.explicit_book_info");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
