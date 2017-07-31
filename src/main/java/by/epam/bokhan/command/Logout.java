package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 13.07.2017.
 */
public class Logout extends AbstractCommand {

    public Logout(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {
        try {
            super.execute(content);
            String page = ConfigurationManager.getProperty(INDEX_PAGE);
            content.insertParameter(PAGE, page);
        } catch (ReceiverException e) {
            String page = ConfigurationManager.getProperty(ERROR_PAGE);
            content.insertParameter(PAGE, page);
        }
    }
}
