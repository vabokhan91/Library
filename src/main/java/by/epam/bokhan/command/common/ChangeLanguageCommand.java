package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 28.08.2017.
 */
public class ChangeLanguageCommand extends AbstractCommand {
    public ChangeLanguageCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws ReceiverException {
        String language = (String) content.getRequestParameters().get("new_language");
        String page = (String) content.getRequestParameters().get("current_page");
        content.insertAttribute("language", language);
        content.insertParameter(PAGE, page);


        content.insertParameter(INVALIDATE, false);

    }
}
