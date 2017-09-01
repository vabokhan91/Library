package by.epam.bokhan.command;

import by.epam.bokhan.command.admin.*;
import by.epam.bokhan.command.common.*;
import by.epam.bokhan.command.librarian.*;
import by.epam.bokhan.command.navigation.*;
import by.epam.bokhan.command.user.*;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.impl.BookReceiverImpl;
import by.epam.bokhan.receiver.UserReceiver;
import by.epam.bokhan.receiver.impl.UserReceiverImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException{
            ((UserReceiverImpl) getCommand().getReceiver()).login(content);
        }
    },
    LOGOUT(new LogoutCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) {
            ((UserReceiverImpl) getCommand().getReceiver()).logout(content);
        }
    },ERROR_PAGE(new ToErrorPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) {
        }
    },ADD_USER(new AddUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).addUser(content);
        }
    },REMOVE_USER(new RemoveUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).removeUser(content);
        }
    },TO_REMOVE_USER_STATUS_PAGE(new ToRemoveUserStatusPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },FIND_USER(new FindUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).findUser(content);
        }
    },REGISTER(new RegisterUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).registerUser(content);
        }
    },TO_REGISTRATION_PAGE(new ToRegistrationPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_REGISTRATION_RESULT(new ToRegistrationResultCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_MAIN_PAGE(new ToMainPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getRandomBooks(content);
        }
    },TO_FIND_USER_PAGE(new ToFindUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_REMOVE_USER_PAGE(new ToRemoveUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_ADMIN_PAGE(new ToAdminPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_ADD_USER_PAGE(new ToAddUserPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_LIBRARIAN_MAIN_PAGE(new ToLibrarianMainPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },BLOCK_USER(new BlockUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).blockUser(content);
        }
    },TO_BLOCK_STATUS_PAGE(new ToBlockStatusPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },UNBLOCK_USER(new UnblockUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).unblockUser(content);
        }
    },TO_UNBLOCK_STATUS_PAGE(new ToUnblockStatusPageCommand(new UserReceiverImpl())) {
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
    },GET_ALL_USERS(new GetAllUsersCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getAllUsers(content);
        }
    },GET_EXPLICIT_USER_INFO(new GetExplicitUserInfoCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getExplicitUserInfo(content);
        }
    },GET_USER_FOR_EDITING(new GetUserForEditingCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).findUser(content);
        }
    },EDIT_USER(new EditUserCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).editUser(content);
        }
    },TO_USER_EDIT_STATUS(new ToUserEditStatusCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException{

        }
    },GET_ALL_BOOKS(new GetAllBooksCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getAllBooks(content);
        }
    },TO_FIND_BOOK_PAGE(new ToFindBookPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },FIND_BOOK(new FindBookCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).findBook(content);
        }
    },GET_EXPLICIT_BOOK_INFO(new GetExplicitBookInfoCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getExplicitBookInfo(content);
        }
    },TO_EXPLICIT_BOOK_INFO_PAGE(new ToExplicitBookInfoPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },GET_BOOK_FOR_EDITING(new GetBookForEditing(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getBookForEditing(content);
        }
    },EDIT_BOOK(new EditBookCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).editBook(content);
        }
    },TO_BOOK_EDIT_STATUS_PAGE(new ToBookEditStatusPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_ADD_AUTHOR_PAGE(new ToAddAuthorPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },ADD_AUTHOR(new AddAuthorCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).addAuthor(content);
        }
    },TO_ADD_BOOK_PAGE(new ToAddBookPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getGenresAuthorsPublishers(content);
        }
    },ADD_BOOK(new AddBookCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).addBook(content);
        }
    },DELETE_BOOK(new DeleteBookCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).deleteBook(content);
        }
    },TO_BOOK_DELETE_RESULT_PAGE(new ToBookDeleteResultPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_ADD_ORDER_PAGE(new ToAddOrderPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getExplicitBookInfo(content);
        }
    },ADD_ORDER(new AddOrderCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).addOrder(content);
        }
    },TO_ORDER_STATUS_PAGE(new ToOrderStatusPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_FIND_USER_ORDERS_PAGE(new ToFindUserOrdersPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },GET_USER_ORDERS(new GetUserOrdersCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).getUserOrders(content);
        }
    },RETURN_BOOK(new ReturnBookCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).returnBook(content);
        }
    },TO_BOOK_RETURN_STATUS_PAGE(new ToBookReturnStatusPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_ADD_PUBLISHER_PAGE(new ToAddPublisherPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },ADD_PUBLISHER(new AddPublisherCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).addPublisher(content);
        }
    },TO_ADD_GENRE_PAGE(new ToAddGenrePageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },ADD_GENRE(new AddGenreCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).addGenre(content);
        }
    },TO_DELETE_GENRE_PAGE(new ToDeleteGenrePageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getAllGenres(content);
        }
    },DELETE_GENRE(new DeleteGenreCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).deleteGenre(content);
        }
    },TO_DELETE_AUTHOR_PAGE(new ToDeleteAuthorPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getAllAuthors(content);
        }
    },DELETE_AUTHOR(new DeleteAuthorCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).deleteAuthor(content);
        }
    },TO_DELETE_PUBLISHER_PAGE(new ToDeletePublisherPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getAllPublishers(content);
        }
    },DELETE_PUBLISHER(new DeletePublisherCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).deletePublisher(content);
        }
    },TO_USER_MAIN_PAGE(new ToUserMainPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_CHANGE_PASSWORD_PAGE(new ToChangePasswordPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },CHANGE_PASSWORD(new ChangePasswordCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).changePassword(content);
        }
    },TO_CHANGE_PASSWORD_STATUS_PAGE(new ToChangePasswordStatusPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_CHANGE_LOGIN_PAGE(new ToChangeLoginPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },CHANGE_LOGIN(new ChangeLoginCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).changeLogin(content);
        }
    },UPLOAD_USER_PHOTO(new UploadUserPhotoCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).uploadUserPhoto(content);
        }
    },TO_USER_FIND_BOOK_PAGE(new ToUserFindBookCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },USER_FIND_BOOK(new UserFindBookCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).findBookForUser(content);
        }
    },TO_ADD_ONLINE_ORDER_PAGE(new ToAddOnlineOrderPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getExplicitBookInfo(content);
        }
    },ADD_ONLINE_ORDER(new AddOnlineOrderCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).addOnlineOrder(content);
        }
    },TO_ADD_ONLINE_ORDER_STATUS(new ToAddOnlineOrderStatusCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_GET_ONLINE_ORDERS_PAGE(new ToOnlineOrdersPageCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).getUserOnlineOrders(content);
        }
    },CANCEL_ONLINE_ORDER(new CancelOnlineOrderCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).cancelOnlineOrder(content);
        }
    },TO_CANCEL_ONLINE_ORDER_STATUS_PAGE(new ToCancelOnlineOrderStatusCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },TO_FIND_USER_ONLINE_ORDERS(new ToFindUserOnlineOrdersCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },GET_ONLINE_ORDER_INFORMATION_PAGE(new GetOnlineOrderInfoPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getExplicitBookInfo(content);
        }
    },TO_EXECUTE_ONLINE_ORDER_PAGE(new ToExecuteOnlineOrderCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    }
    ,EXECUTE_ONLINE_ORDER(new ExecuteOnlineOrderCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).executeOnlineOrder(content);
        }
    },TO_EXECUTE_ONLINE_ORDER_STATUS_PAGE(new ToExecuteOnlineOrderStatusPageCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {

        }
    },FIND_BOOK_BY_GENRE(new FindBookByGenreCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getBookByGenre(content);
        }
    },GET_RANDOM_BOOKS_FROM_DATABASE(new GetRandomBooksFromDBCommand(new BookReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((BookReceiverImpl) getCommand().getReceiver()).getRandomBooks(content);
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
        types.stream().filter(t -> t.getCommand().equals(command)).forEach(result::add);
        return result.get(0);
    }
}
