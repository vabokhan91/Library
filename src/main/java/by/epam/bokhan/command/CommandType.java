package by.epam.bokhan.command;

import by.epam.bokhan.command.navigation.admin.*;
import by.epam.bokhan.command.navigation.common.*;
import by.epam.bokhan.command.navigation.librarian.FindBook;
import by.epam.bokhan.command.navigation.librarian.ToFindBookPage;
import by.epam.bokhan.command.navigation.librarian.ToLibrarianMainPage;
import by.epam.bokhan.command.navigation.librarian.ToShowBooksPage;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.CommandException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.BookReceiverImpl;
import by.epam.bokhan.receiver.UserReceiverImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vbokh on 13.07.2017.
 */
public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException{
            ((UserReceiverImpl) getCommand().getReceiver()).login(content);
        }
    },
    LOGOUT(new LogoutCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) {
            ((UserReceiverImpl) getCommand().getReceiver()).signOut(content);
        }
    },
    ERROR_PAGE(new ErrorPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) {
        }
    },
    ADD_USER(new AddUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).addUser(content);
        }
    },
    REMOVE_USER(new RemoveUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).removeUser(content);
        }
    },
    FIND_USER(new FindUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).findUser(content);
        }
    },
    REGISTER(new RegisterUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).addUser(content);
        }
    },TO_REGISTRATION_PAGE(new ToRegistrationPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_MAIN_PAGE(new ToMainPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_FIND_USER_PAGE(new ToFindUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_REMOVE_USER_PAGE(new ToRemoveUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_ADMIN_PAGE(new ToAdminPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_SUCCESS_REMOVE_USER_PAGE(new ToSuccessRemoveUser(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_FAIL_REMOVE_USER_PAGE(new ToRemoveFailUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_ADD_USER_PAGE(new ToAddUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_USER_ADDED_PAGE(new ToUserAddedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_USER_NOT_ADDED_PAGE(new ToUserNotAddedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_LIBRARIAN_MAIN_PAGE(new ToLibrarianMainPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_BLOCK_USER_PAGE(new ToBlockUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },BLOCK_USER(new BlockUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).blockUser(content);
        }
    },BLOCK_USER_FAILED_PAGE(new ToBlockFailedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },BLOCK_USER_SUCCESS_PAGE(new ToBlockSuccessPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_UNBLOCK_USER_PAGE(new UnblockUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },UNBLOCK_USER(new UnblockUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).unblockUser(content);
        }
    },UNBLOCK_USER_SUCCESS_PAGE(new UnblockSuccessPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },UNBLOCK_USER_FAILED_PAGE(new UnblockFailedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },GET_BLOCKED_USERS(new GetBlockedUsersCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getBlockedUsers(content);
        }
    },GET_NOT_BLOCKED_USERS(new GetNotBlockedUsersCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getNotBlockedUsers(content);
        }
    },GET_USERS_FOR_REMOVAL(new GetUsersForRemovalCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getAllUsers(content);
        }
    },GET_ALL_USERS(new GetAllUsersCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getAllUsers(content);
        }
    },TO_SHOW_USERS_PAGE(new ToShowUsersPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },GET_EXPLICIT_USER_INFO(new GetExplicitUserInfoCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getExplicitUserInfo(content);
        }
    },TO_EXPLICIT_USER_INFO_PAGE(new ToExplicitUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },GET_USER_FOR_EDITING(new GetUserForEditingCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getUser(content);
        }
    },TO_EDIT_USER_PAGE(new ToEditUserPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },EDIT_USER(new EditUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).editUser(content);
        }
    },TO_USER_EDITED_PAGE(new ToUserEditedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException{

        }
    },TO_USER_NOT_EDITED_PAGE(new ToUserNotEditedPage(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },GET_ALL_BOOKS(new GetAllBooksCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getAllBooks(content);
        }
    },TO_SHOW_BOOKS_PAGE(new ToShowBooksPage(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_FIND_BOOK_PAGE(new ToFindBookPage(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },FIND_BOOK(new FindBook(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).findBook(content);
        }
    };

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public abstract void doReceiver(RequestContent content) throws ReceiverException;

    public static CommandType takeCommandType(AbstractCommand command) {
        ArrayList<CommandType> result = new ArrayList<>();
        List<CommandType> types = Arrays.asList(CommandType.values());
        types.stream().filter(t -> t.getCommand().equals(command)).forEach(t -> result.add(t));
        return result.get(0);
    }
}
