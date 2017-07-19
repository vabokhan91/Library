package by.epam.bokhan.receiver;


import by.epam.bokhan.content.RequestContent;

import java.sql.SQLException;

public interface UserReceiver extends Receiver {

    void signIn(RequestContent content) throws SQLException;
    void signOut(RequestContent content);
}
