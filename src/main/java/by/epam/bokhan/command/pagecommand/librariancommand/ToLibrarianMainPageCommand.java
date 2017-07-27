package by.epam.bokhan.command.pagecommand.librariancommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 23.07.2017.
 */
public class ToLibrarianMainPageCommand extends AbstractCommand{



    public ToLibrarianMainPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {

        String page = ConfigurationManager.getProperty(TO_LIBRARIAN_MAIN_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
