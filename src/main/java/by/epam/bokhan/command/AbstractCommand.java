package by.epam.bokhan.command;

import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.receiver.Receiver;

/**
 * Created by vbokh on 13.07.2017.
 */
public abstract class AbstractCommand {
    protected final String USER_IS_ADDED = "userIsAdded";
    protected final String TO_USER_ADDED_PAGE_COMMAND = "/controller?command=to_user_added_page";
    protected final String TO_USER_NOT_ADDED_PAGE_COMMAND = "/controller?command=to_user_not_added_page";
    protected final String PAGE = "page";
    protected final String TYPE_OF_TRANSITION = "type_of_transition";
    protected final String REDIRECT = "redirect";
    protected final String ERROR_PAGE = "path.page.error";
    protected final String INVALIDATE = "invalidate";
    protected final String USER_INFO_PAGE = "path.page.user_info";
    protected final String FOUND_USER = "foundUser";
    protected final String USER_FOUND_STATUS = "user_found_status";
    protected final String MESSAGE_FOUND_USER_TRUE = "message.found_user_true";
    protected final String MESSAGE_FOUND_USER_FALSE = "message.found_user_false";
    protected final String IS_USER_BLOCKED = "isUserBlocked";
    protected final String TO_BLOCK_USER_SUCCESS_PAGE_COMMAND = "/controller?command=block_user_success_page";
    protected final String TO_BLOCK_USER_FAILED_PAGE_COMMAND = "/controller?command=block_user_failed_page";
    protected final String IS_VALID = "isValid";
    protected final String USER = "user";
    protected final String ADMIN_PAGE = "path.page.admin";
    protected final String LIBRARIAN_PAGE = "path.page.librarian";
    protected final String USER_MAIN_PAGE = "path.page.authorized_user_main";
    protected final String ERROR_LOGIN_PASS_MESSAGE = "errorLoginPassMessage";
    protected final String LOGIN_ERROR_MESSAGE = "message.loginerror";
    protected final String INDEX_PAGE = "path.page.index";
    protected final String SUCCESS_REGISTRATION_PAGE = "path.page.successregistration";
    protected final String USER_INSERT_STATUS = "user_insert_status";
    protected final String MESSAGE_ADD_USER_TRUE = "message.add_user_true";
    protected final String MESSAGE_ADD_USER_FALSE = "message.add_user_false";
    protected final String IS_USER_DELETED = "isUserDeleted";
    protected final String TO_SUCCESS_REMOVE_PAGE_COMMAND = "/controller?command=to_success_remove_user_page";
    protected final String TO_FAIL_REMOVE_PAGE_COMMAND = "/controller?command=to_fail_remove_user_page";
    protected final String IS_USER_UNBLOCKED = "isUserUnblocked";
    protected final String TO_UNBLOCK_USER_SUCCESS_PAGE_COMMAND = "/controller?command=unblock_user_success_page";
    protected final String TO_UNBLOCK_USER_FAILED_PAGE_COMMAND = "/controller?command=unblock_user_failed_page";
    protected final String FIND_USER_PAGE = "path.page.find_user";
    protected final String REGISTRATION_PAGE = "path.page.registration_page";
    protected final String BLOCK_USER_FAILED_PAGE = "path.page.block_failed";
    protected final String TO_BLOCK_STATUS_PAGE = "path.page.block_status";
    protected final String BLOCK_USER_PAGE = "path.page.block_user";
    protected final String TO_FAIL_USER_REMOVE_PAGE = "path.page.fail_user_remove";
    protected final String REMOVE_USER_PAGE = "path.page.remove_user";
    protected final String TO_SUCCESS_USER_REMOVE_PAGE = "path.page.success_user_remove";
    protected final String TO_UNBLOCK_FAILED_PAGE = "path.page.unblock_failed";
    protected final String TO_UNBLOCK_SUCCESS_PAGE = "path.page.unblock_success";
    protected final String TO_UNBLOCK_USER_PAGE = "path.page.unblock_user";
    protected final String TO_ADD_USER_PAGE = "path.page.adduser";
    protected final String TO_USER_ADDED_PAGE = "path.page.user_added";
    protected final String TO_USER_NOT_ADDED_PAGE = "path.page.user_not_added";
    protected final String TO_LIBRARIAN_MAIN_PAGE = "path.page.librarian";
    protected final String TO_UNBLOCK_USER_PAGE_COMMAND = "/controller?command=to_unblock_user_page";
    protected final String TO_BLOCK_USER_PAGE_COMMAND = "/controller?command=to_block_user_page";
    protected final String TO_BLOCKED_USERS_PAGE = "path.page.blocked_users_page";
    protected final String TO_EDIT_USER_PAGE = "path.page.edit_user";
    protected final String TO_EXPLICIT_USER_INFO_PAGE = "path.page.explicit_user_info";
    protected final String TO_MAIN_PAGE = "path.page.startpage";
    protected final String TO_SHOW_ALL_USERS_PAGE = "path.page.show_all_users";
    protected final String TO_USER_IS_EDITED_PAGE = "path.page.user_is_edited";
    protected final String TO_USER_NOT_EDITED_PAGE = "path.page.user_not_edited";
    protected final String IS_USER_EDITED = "isUserEdited";
    protected final String TO_USER_EDITED_PAGE_COMMAND = "/controller?command=to_user_edited_page";
    protected final String TO_USER_NOT_EDITED_PAGE_COMMAND = "/controller?command=to_user_not_edited_page";
    protected final String TO_SHOW_USERS_PAGE_COMMAND = "/controller?command=to_show_users_page";
    protected final String TO_EXPLICIT_USER_INFO_PAGE_COMMAND = "/controller?command=to_explicit_user_info_page";
    protected final String TO_EDIT_USER_PAGE_COMMAND = "/controller?command=to_edit_user_page";
    protected final String TO_REMOVE_USER_PAGE_COMMAND = "/controller?command=to_remove_user_page";

    private Receiver receiver;

    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute(RequestContent content) throws ReceiverException {
        receiver.action(CommandType.takeCommandType(this), content);
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
