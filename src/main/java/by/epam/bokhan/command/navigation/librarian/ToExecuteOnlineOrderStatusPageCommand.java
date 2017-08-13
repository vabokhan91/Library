package by.epam.bokhan.command.navigation.librarian;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 12.08.2017.
 */
public class ToExecuteOnlineOrderStatusPageCommand extends AbstractCommand {
    public ToExecuteOnlineOrderStatusPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty("path.page.book.online_order_status_page");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}