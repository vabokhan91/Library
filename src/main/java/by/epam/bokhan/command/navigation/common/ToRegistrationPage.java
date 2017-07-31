package by.epam.bokhan.command.navigation.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 22.07.2017.
 */
public class ToRegistrationPage extends AbstractCommand {

    public ToRegistrationPage(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {

            String page = ConfigurationManager.getProperty(REGISTRATION_PAGE);
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
    }
}
