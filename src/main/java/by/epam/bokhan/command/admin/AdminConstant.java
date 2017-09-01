package by.epam.bokhan.command.admin;


class AdminConstant {

    static final String PAGE = "page";
    static final String TYPE_OF_TRANSITION = "type_of_transition";
    static final String REDIRECT = "redirect";
    static final String INVALIDATE = "invalidate";
    static final String TO_UNBLOCK_STATUS_PAGE = "path.page.unblock_status";
    static final String TO_UNBLOCK_USER_PAGE = "path.page.unblock_user";
    static final String TO_UNBLOCK_USER_PAGE_COMMAND = "/controller?command=to_unblock_user_page";
    static final String TO_BLOCK_USER_PAGE_COMMAND = "/controller?command=to_block_user_page";
    static final String BLOCK_STATUS_PAGE_COMMAND = "/controller?command=to_block_status_page";
    static final String TO_REMOVE_USER_STATUS_COMMAND = "/controller?command=to_remove_user_status_page";
    static final String TO_UNBLOCK_STATUS_PAGE_COMMAND = "/controller?command=to_unblock_status_page";

    private AdminConstant() {

    }
}
