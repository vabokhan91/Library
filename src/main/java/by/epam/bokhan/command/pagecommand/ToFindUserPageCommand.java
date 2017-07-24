package by.epam.bokhan.command.pagecommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 23.07.2017.
 */
public class ToFindUserPageCommand extends AbstractCommand {
    public ToFindUserPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {

        String page = ConfigurationManager.getProperty("path.page.find_user");
        content.insertParameter("page", page);
        content.insertParameter("invalidate", "false");
    }
}
