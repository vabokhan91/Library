package by.epam.bokhan.command.navigation;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;


public class GetOnlineOrderInfoPageCommand extends AbstractCommand {

    public GetOnlineOrderInfoPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{
        super.execute(content);
        content.insertParameter(PAGE, "/controller?command=to_execute_online_order_page");
        content.insertParameter(INVALIDATE, false);
    }
}
