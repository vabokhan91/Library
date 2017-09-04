package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.CommandException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;


public abstract class AbstractCommand{
    private Receiver receiver;

    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(RequestContent content) throws CommandException {
        try {
            receiver.action(CommandType.takeCommandType(this), content);
        } catch (ReceiverException e) {
            throw new CommandException(e);
        }
    }

    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractCommand that = (AbstractCommand) o;

        return receiver != null ? receiver.equals(that.receiver) : that.receiver == null;
    }

    @Override
    public int hashCode() {
        return receiver != null ? receiver.hashCode() : 0;
    }
}
