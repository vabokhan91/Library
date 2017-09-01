package by.epam.bokhan.manager;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class MessageManager {
    private static final String LANGUAGE = "resource/language";
    private static final ResourceBundle resourceBundle;

    static {
        try {
            resourceBundle = PropertyResourceBundle.getBundle(LANGUAGE);

        } catch (MissingResourceException e) {
            throw new RuntimeException(String.format("Can not find resource bundle. Reason : %s", e.getMessage()));
        }
    }

    private MessageManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
