package by.epam.bokhan.command.navigation;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.navigation.NavigationConstant.*;

public class ToChangeLoginPageCommand extends AbstractCommand {

    public ToChangeLoginPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {
        String page = ConfigurationManager.getProperty(CHANGE_LOGIN_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);

    }
}
