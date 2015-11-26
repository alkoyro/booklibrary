package by.es.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * User: Alexey.Koyro
 */
public class ComponentFinder {
    private static final ComponentFinder INSTANCE = new ComponentFinder();

    private ComponentFinder() {
    }

    public static ComponentFinder getInstance() {
        return INSTANCE;
    }


    public UIComponent findComponentBy(FacesContext facesContext, String clientId) {
        return findErrorPanel(facesContext.getViewRoot().getChildren(), clientId);
    }

    private UIComponent findErrorPanel(List<UIComponent> components, final String clientId) {
        for (UIComponent component : components) {
            if (component.getClientId().equals(clientId)) {
                return component;
            } else {
                UIComponent result = findErrorPanel(component.getChildren(), clientId);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
