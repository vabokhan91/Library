package by.epam.bokhan.factory;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.command.CommandType;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.MessageManager;

/**
 * Created by vbokh on 13.07.2017.
 */
public class CommandFactory {

    public AbstractCommand defineCommand(RequestContent content) {
        AbstractCommand current = null;
        String action = (String) content.getRequestParameters().get("command");
        if(action != null && !action.isEmpty()) {
            try {
                CommandType currentCommand = CommandType.valueOf(action.toUpperCase());
                current = currentCommand.getCommand();
            } catch (IllegalArgumentException e) {
                content.insertParameter("wrongAction", action + MessageManager.getProperty("message.wrongAction"));
            }
        }
        return current;
    }
}
