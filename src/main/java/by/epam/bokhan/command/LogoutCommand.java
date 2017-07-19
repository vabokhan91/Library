package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

import java.sql.SQLException;

/**
 * Created by vbokh on 13.07.2017.
 */
public class LogoutCommand extends AbstractCommand {


    public LogoutCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws SQLException {


        super.execute(content);

        String page = ConfigurationManager.getProperty("path.page.index");
        content.insertAttributes("page", page);
    }
}
