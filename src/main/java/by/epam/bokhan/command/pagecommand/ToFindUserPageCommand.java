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

        String page = ConfigurationManager.getProperty(FIND_USER_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
