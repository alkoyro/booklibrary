package by.es.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 14.02.12
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
public class AppProps {

    private static final Logger log = Logger.getLogger(AppProps.class.getName());

    private static final String DEFAULT_PROPERTIES_BUNDLE = "properties/application";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_PROPERTIES_BUNDLE);

    public static String get(String key) {
        String value = "";
        try {
            value = bundle.getString(key);
        } catch (MissingResourceException e) {
            log.warning("Property '" + key + "' not found in application.properties.\nNested exception: " + e.getMessage());
        }
        return value;
    }

}    
