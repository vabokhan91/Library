package by.epam.bokhan.manager;

import java.util.ResourceBundle;

/**
 * Created by vbokh on 13.07.2017.
 */
public class ConfigurationManager {
    private static final String CONFIG = "resource/config";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(CONFIG);

    private ConfigurationManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
