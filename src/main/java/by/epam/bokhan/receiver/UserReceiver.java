package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;

public interface UserReceiver extends Receiver {

    void registerUser(RequestContent requestContent) throws ReceiverException;

    void login(RequestContent content) throws ReceiverException;

    void logout(RequestContent content) throws ReceiverException;

    void changePassword(RequestContent requestContent) throws ReceiverException;

    void changeLogin(RequestContent requestContent) throws ReceiverException;

    void findUser(RequestContent requestContent) throws ReceiverException;

    void blockUser(RequestContent requestContent) throws ReceiverException;

    void unblockUser(RequestContent requestContent) throws ReceiverException;

    void getBlockedUsers(RequestContent requestContent) throws ReceiverException;

    void getNotBlockedUsers(RequestContent requestContent) throws ReceiverException;

    void getExplicitUserInfo(RequestContent requestContent) throws ReceiverException;

    void editUser(RequestContent requestContent) throws ReceiverException;

    void removeUser(RequestContent requestContent) throws ReceiverException;

    void getAllUsers(RequestContent requestContent) throws ReceiverException;

    void addUser(RequestContent requestContent) throws ReceiverException;

    void uploadUserPhoto(RequestContent requestContent) throws ReceiverException;

    void getUserOrders(RequestContent requestContent) throws ReceiverException;

    void getUserOnlineOrders(RequestContent requestContent) throws ReceiverException;
}
