package by.epam.bokhan.command.pagecommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 22.07.2017.
 */
public class ToRegistrationPageCommand extends AbstractCommand {

    public ToRegistrationPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {

            String page = ConfigurationManager.getProperty(REGISTRATION_PAGE);
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
    }
}
