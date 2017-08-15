package by.epam.bokhan.factory;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.command.CommandType;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.manager.MessageManager;


public class CommandFactory {

    private static final String COMMAND = "command";
    private static final String WRONG_ACTION_MESSAGE = "message.wrongaction";
    private static final String WRONG_ACTION = "wrongAction";

    public AbstractCommand defineCommand(RequestContent content) {
        AbstractCommand current = null;
        String action = (String) content.getRequestParameters().get(COMMAND);
        if(action != null && !action.isEmpty()) {
            try {
                CommandType currentCommand = CommandType.valueOf(action.toUpperCase());
                current = currentCommand.getCommand();
            } catch (IllegalArgumentException e) {
                content.insertParameter(WRONG_ACTION, action + MessageManager.getProperty(WRONG_ACTION_MESSAGE));
            }
        }
        return current;
    }
}
