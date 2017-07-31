package by.epam.bokhan.command;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 31.07.2017.
 */
public class EditUser extends AbstractCommand {



    public EditUser(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content)  {

        try {
            super.execute(content);
            String page;
            if ((Boolean) content.getRequestParameters().get(IS_USER_EDITED)) {
                page = TO_USER_EDITED_PAGE_COMMAND;
            }
            else {
                page = TO_USER_NOT_EDITED_PAGE_COMMAND;
            }
            content.insertParameter(PAGE, page);
            content.insertParameter(TYPE_OF_TRANSITION, REDIRECT);
        } catch (ReceiverException e) {
            
            String page = ConfigurationManager.getProperty(TO_USER_NOT_EDITED_PAGE);
            content.insertParameter(PAGE, page);
        }
        content.insertParameter(INVALIDATE, false);

    }
}
