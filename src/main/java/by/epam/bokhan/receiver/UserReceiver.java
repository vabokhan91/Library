package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;

public interface UserReceiver extends Receiver {

    /**
     * Registers new user
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void registerUser(RequestContent requestContent) throws ReceiverException;

    /**
     * Login unauthorized user and enter personal cabinet
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void login(RequestContent requestContent) throws ReceiverException;

    /**
     * Logout authorized user
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void logout(RequestContent requestContent) throws ReceiverException;

    /**
     * Changes user password
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void changePassword(RequestContent requestContent) throws ReceiverException;

    /**
     * Changes user login
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void changeLogin(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets user depending on query value
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getUser(RequestContent requestContent) throws ReceiverException;

    /**
     * Blocks user
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void blockUser(RequestContent requestContent) throws ReceiverException;

    /**
     * Unblocks user
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void unblockUser(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets blocked users
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getBlockedUsers(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets not blocked users
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getNotBlockedUsers(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets explicit user information
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getExplicitUserInfo(RequestContent requestContent) throws ReceiverException;

    /**
     * Edits user
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void editUser(RequestContent requestContent) throws ReceiverException;

    /**
     * Removes user
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void removeUser(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets all users
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getAllUsers(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds new user
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void addUser(RequestContent requestContent) throws ReceiverException;

    /**
     * Uploads user photo
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void uploadUserPhoto(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets user orders
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getUserOrders(RequestContent requestContent) throws ReceiverException;

    /**
     * Gets user online orders
     * @param requestContent object holding all request parameters and session attributes
     * @throws ReceiverException
     * */
    void getUserOnlineOrders(RequestContent requestContent) throws ReceiverException;
}
