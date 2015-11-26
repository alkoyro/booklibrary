package by.es.tags;

import by.es.realm.entity.predefined.UserStatus;

import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

/**
 * Created by Alexey.Koyro
 */
public final class ResourceMessageProvider
{
    public static final String USER_BUNDLE_NAME = "userStatusBundle";

    private ResourceMessageProvider()
    {
    }

    private static ResourceBundle getBundle(String bundleName)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, bundleName);
    }

    public static String getMessage(String key, String bundleName)
    {
        ResourceBundle resourceBundle = getBundle(bundleName);
        return resourceBundle.getString(key);
    }

    public static String getIcon(String key, String bundleName)
    {
        ResourceBundle resourceBundle = getBundle(bundleName);
        return resourceBundle.getString(key);
    }

    public static String getUserStatusMessage(UserStatus status)
    {
        ResourceBundle resourceBundle = getBundle(USER_BUNDLE_NAME);
        if(status == null)
        {
            return resourceBundle.getString("unknown");
        }

        return resourceBundle.getString(status.name());
    }
}
