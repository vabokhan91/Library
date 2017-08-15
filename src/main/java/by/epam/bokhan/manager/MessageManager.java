package by.epam.bokhan.manager;

import java.util.ResourceBundle;


public class MessageManager {
    private static final String LANGUAGE = "resource/language";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(LANGUAGE);

    private MessageManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
