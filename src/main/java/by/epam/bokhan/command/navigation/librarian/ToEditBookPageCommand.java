package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 05.08.2017.
 */
public class ToEditBookPageCommand extends AbstractCommand {

    public ToEditBookPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty("path.page.edit_book");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
