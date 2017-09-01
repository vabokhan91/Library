package by.epam.bokhan.command.admin;


class AdminConstant {

    static final String PAGE = "page";
    static final String TYPE_OF_TRANSITION = "type_of_transition";
    static final String REDIRECT = "redirect";
    static final String INVALIDATE = "invalidate";
    static final String UNBLOCK_USER_PAGE = "path.page.unblock_user";
    static final String BLOCKED_USERS_PAGE = "path.page.block_user";
    static final String BLOCK_STATUS_PAGE_COMMAND = "/controller?command=to_block_status_page";
    static final String TO_REMOVE_USER_STATUS_COMMAND = "/controller?command=to_remove_user_status_page";
    static final String TO_UNBLOCK_STATUS_PAGE_COMMAND = "/controller?command=to_unblock_status_page";

    private AdminConstant() {

    }
}
