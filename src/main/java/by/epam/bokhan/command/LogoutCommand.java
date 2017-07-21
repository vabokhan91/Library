package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 13.07.2017.
 */
public class LogoutCommand extends AbstractCommand {


    public LogoutCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) {


        try {
            super.execute(content);
            String page = ConfigurationManager.getProperty("path.page.index");
            content.insertParameter("page", page);
        } catch (DAOException e) {
            String page = ConfigurationManager.getProperty("path.page.error");
            content.insertParameter("page", page);
        }


    }
}
