package by.epam.bokhan.command.navigation;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.navigation.NavigationConstant.*;

public class ToUserOrdersPageCommand extends AbstractCommand {
    public ToUserOrdersPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty(USER_ORDERS_PAGE);
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
