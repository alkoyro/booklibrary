package by.es.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 10.02.12
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class AppURLCreator {
    public static String getAppURL() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme());
        url.append("://");
        url.append(request.getServerName());
        if (request.getServerPort() > 0) {
            url.append(':');
            url.append(request.getServerPort());
        }

        url.append(request.getContextPath());
        return url.toString();
    }

    public static String getParameterString(Map<String, String[]> parameterMap) {
        return getParameterString(parameterMap, true);
    }

    public static String getParameterString(Map<String, String[]> parameterMap, boolean isFirst) {
        StringBuilder url = new StringBuilder();
        if (parameterMap.isEmpty()) {
            return "";
        }

        if (isFirst) {
            url.append('?');
        } else {
            url.append('&');
        }

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            for (String val : entry.getValue()) {
                url.append(entry.getKey());
                url.append('=');
                url.append(val);
                url.append('&');
            }
        }
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }
}
