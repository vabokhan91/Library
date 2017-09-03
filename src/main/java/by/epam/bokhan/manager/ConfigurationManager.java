package by.epam.bokhan.manager;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class ConfigurationManager {
    /* Name of language property file*/
    private static final String CONFIG = "resource/config";
    /* Resource bundle to get properties*/
    private static final ResourceBundle RESOURCE_BUNDLE;

    static {
        try {
           RESOURCE_BUNDLE = PropertyResourceBundle.getBundle(CONFIG);

        } catch (MissingResourceException e) {
            throw new RuntimeException(String.format("Can not find resource bundle. Reason : %s", e.getMessage()));
        }
    }
    /*Private to make it singleton*/
    private ConfigurationManager() {
    }
    /*Gets property from resource bundle*/
    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
