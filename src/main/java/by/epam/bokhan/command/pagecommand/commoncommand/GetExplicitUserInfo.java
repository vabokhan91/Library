package by.epam.bokhan.command.pagecommand.commoncommand;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 29.07.2017.
 */
public class GetExplicitUserInfo extends AbstractCommand{
    public GetExplicitUserInfo(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        try {
            super.execute(content);
            String page = "/controller?command=to_explicit_user_info_page";
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
        } catch (ReceiverException e) {
            String page = ERROR_PAGE;
            content.insertParameter(PAGE, page);
            content.insertParameter(INVALIDATE, false);
            throw e;
        }
    }
}
