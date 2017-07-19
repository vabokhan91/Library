package by.epam.bokhan.manager;

import java.util.ResourceBundle;

/**
 * Created by vbokh on 13.07.2017.
 */
public class ConfigurationManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("/resource/config");

    private ConfigurationManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
