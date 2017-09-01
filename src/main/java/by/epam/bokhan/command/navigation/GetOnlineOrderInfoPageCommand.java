package by.epam.bokhan.command.navigation;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;
import static by.epam.bokhan.command.navigation.NavigationConstant.*;


public class GetOnlineOrderInfoPageCommand extends AbstractCommand {

    public GetOnlineOrderInfoPageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException{
        super.execute(content);
        content.insertParameter(PAGE, TO_EXECUTE_ONLINE_ORDER_PAGE);
        content.insertParameter(INVALIDATE, false);
    }
}
