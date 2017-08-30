package by.epam.bokhan.property;

import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertFalse;

public class PropertyTest {
    private static final String DB_CONFIG = "resource/database.properties";
    private static final String CONFIG_PROP = "resource/config.properties";
    private static final String LANGUAGE = "resource/config.properties";

    @Test
    public void databasePropTest() throws Exception{
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream(DB_CONFIG);
        properties.load(is);
        assertFalse(properties.isEmpty());
    }

    @Test
    public void configPropTest() throws Exception{
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream(CONFIG_PROP);
        properties.load(is);
        assertFalse(properties.isEmpty());
    }

    @Test
    public void languagePropTest() throws Exception{
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream(LANGUAGE);
        properties.load(is);
        assertFalse(properties.isEmpty());
    }
}
