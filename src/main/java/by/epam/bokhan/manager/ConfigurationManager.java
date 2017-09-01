package by.epam.bokhan.manager;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class ConfigurationManager {
    private static final String CONFIG = "resource/config";
    private static final ResourceBundle resourceBundle;

    static {
        try {
           resourceBundle = PropertyResourceBundle.getBundle(CONFIG);

        } catch (MissingResourceException e) {
            throw new RuntimeException(String.format("Can not find resource bundle. Reason : %s", e.getMessage()));
        }
    }

    private ConfigurationManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
