package by.epam.bokhan.command.navigation.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 14.08.2017.
 */
public class ToRegistrationResultCommand extends AbstractCommand {
    public ToRegistrationResultCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        String page = ConfigurationManager.getProperty(REGISTRATION_RESULT);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }


}
