package by.epam.bokhan.command.pagecommand.admincommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 24.07.2017.
 */
public class ToBlockUserPageCommand extends AbstractCommand{
    public ToBlockUserPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty("path.page.block_user");
        content.insertParameter("page", page);
        content.insertParameter("invalidate", "false");
    }
}
