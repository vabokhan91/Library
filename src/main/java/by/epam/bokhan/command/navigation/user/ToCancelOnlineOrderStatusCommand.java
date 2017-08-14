package by.epam.bokhan.command.navigation.user;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 14.08.2017.
 */
public class ToCancelOnlineOrderStatusCommand extends AbstractCommand {
    public ToCancelOnlineOrderStatusCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {
        String page = ConfigurationManager.getProperty("path.page.order.cancel_online_order_status");
        content.insertParameter(PAGE, page);
        content.insertParameter(INVALIDATE, false);
    }
}
