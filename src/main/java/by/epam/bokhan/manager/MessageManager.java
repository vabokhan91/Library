package by.epam.bokhan.manager;

import java.util.ResourceBundle;

/**
 * Created by vbokh on 13.07.2017.
 */
public class MessageManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resource/language");

    private MessageManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
