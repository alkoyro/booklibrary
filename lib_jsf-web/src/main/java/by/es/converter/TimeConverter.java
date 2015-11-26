package by.es.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 07.02.12
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class TimeConverter implements Converter {

    private static final Logger log = Logger.getLogger(TimeConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {

        long time = Math.abs(((Long) o).longValue());
        int sec = Math.round(time / 1000) % 60;
        int min = Math.round(time / (1000 * 60)) % 60;
        int hour = Math.round(time / (1000 * 60 * 60)) % 24;
        int day = Math.round(time / (1000 * 60 * 60 * 24));

        log.info(sec + "sec " + min + "min " + hour + "h  " + day + "d");

        StringBuilder sb = new StringBuilder();

        if (day > 0) {
            sb.append(day);
            if (day > 1) {
                sb.append(" days ");
            } else {
                sb.append(" day ");
            }
        }

        if (hour > 0) {
            sb.append(hour);
            if (hour > 1) {
                sb.append(" hours ");
            } else {
                sb.append(" hour ");
            }
        }

        if (min > 0) {
            sb.append(min);
            if (min > 1) {
                sb.append(" minutes ");
            } else {
                sb.append(" minute ");
            }
        }

        if (sec >= 0) {
            sb.append(sec);
            sb.append(" sec");
        }

        return sb.toString();
    }
}
