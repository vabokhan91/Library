package by.epam.bokhan.command.common;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.CommandException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.receiver.Receiver;

import static by.epam.bokhan.command.common.CommonConstant.*;

public class FindBookByGenreCommand extends AbstractCommand {

    public FindBookByGenreCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(RequestContent content) throws CommandException {
        super.execute(content);
        content.insertParameter(PAGE, ConfigurationManager.getProperty(TO_SHOW_BOOKS_PAGE));
        content.insertParameter(INVALIDATE, false);
    }
}
