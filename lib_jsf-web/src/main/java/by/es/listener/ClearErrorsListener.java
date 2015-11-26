package by.es.listener;

import by.es.util.ComponentFinder;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * User: Alexey.Koyro
 */
@Named
@ApplicationScoped
public class ClearErrorsListener {
    public static final String ERROR_PANE_ID = "errorMessages";

    public void clearWarnings() {
        UIComponent errorPanel = ComponentFinder.getInstance().
                findComponentBy(FacesContext.getCurrentInstance(), ERROR_PANE_ID);
        if (errorPanel != null) {
            errorPanel.getChildren().clear();
        }

    }
}
