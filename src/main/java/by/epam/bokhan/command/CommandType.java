package by.epam.bokhan.command;

import by.epam.bokhan.command.pagecommand.*;
import by.epam.bokhan.command.pagecommand.admincommand.*;
import by.epam.bokhan.command.pagecommand.commoncommand.ToAddUserPageCommand;
import by.epam.bokhan.command.pagecommand.librariancommand.ToLibrarianMainPageCommand;
import by.epam.bokhan.command.pagecommand.commoncommand.ToUserAddedPage;
import by.epam.bokhan.command.pagecommand.commoncommand.ToUserNotAddedPage;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.receiver.UserReceiverImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vbokh on 13.07.2017.
 */
public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException{
            ((UserReceiverImpl) getCommand().getReceiver()).login(content);
        }
    },
    LOGOUT(new LogoutCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) {
            ((UserReceiverImpl) getCommand().getReceiver()).signOut(content);
        }
    },
    ADD_USER(new AddUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {
            ((UserReceiverImpl) getCommand().getReceiver()).addUser(content);
        }
    },
    REMOVE_USER(new RemoveUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {
            ((UserReceiverImpl) getCommand().getReceiver()).removeUser(content);
        }
    },
    FIND_USER(new FindUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {
            ((UserReceiverImpl) getCommand().getReceiver()).findUser(content);
        }
    },
    REGISTER(new RegisterUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {
            ((UserReceiverImpl) getCommand().getReceiver()).addUser(content);
        }
    },
    TO_REGISTRATION_PAGE(new ToRegistrationPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_FIND_USER_PAGE(new ToFindUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_REMOVE_USER_PAGE(new ToRemoveUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_ADMIN_PAGE(new ToAdminPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_SUCCESS_REMOVE_USER_PAGE(new ToSuccessRemoveUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_FAIL_REMOVE_USER_PAGE(new ToFailRemoveUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_ADD_USER_PAGE(new ToAddUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_USER_ADDED_PAGE(new ToUserAddedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_USER_NOT_ADDED_PAGE(new ToUserNotAddedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_LIBRARIAN_MAIN_PAGE(new ToLibrarianMainPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },TO_BLOCK_USER_PAGE(new ToBlockUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },BLOCK_USER(new BlockUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {
            ((UserReceiverImpl) getCommand().getReceiver()).blockUser(content);
        }
    },BLOCK_USER_FAILED_PAGE(new BlockFailedPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    },BLOCK_USER_SUCCESS_PAGE(new BlockSuccessPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws DAOException {

        }
    };

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;

    }

    public AbstractCommand getCommand() {
        return command;
    }

    public abstract void doReceiver(RequestContent content) throws DAOException;

    public static CommandType takeCommandType(AbstractCommand command) {
        ArrayList<CommandType> result = new ArrayList<>();
        List<CommandType> types = Arrays.asList(CommandType.values());
        types.stream().filter(t -> t.getCommand().equals(command)).forEach(t -> result.add(t));
        System.out.println(result.size());
        return result.get(0);
    }
}
