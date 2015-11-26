package by.es.validator;

import by.es.tags.ResourceMessageProvider;
import by.es.util.ComponentFinder;
import org.apache.commons.validator.GenericValidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * User: Alexey.Koyro
 */
public class EmailValidator extends AbstractValidator{

    public static final String NOT_VALID_MESSAGE_KEY = "notValidPassword.error";
    public static final String REG_EXP_PATTERN =
            "(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\\-+)|([A-Za-z0-9]+\\.+)|([A-Za-z0-9]+\\++))*[A-Za-z0-9]+@((\\w+\\-+)|(\\w+\\.))*\\w{1,63}\\.[a-zA-Z]{2,6}";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        if (!GenericValidator.matchRegexp(value.toString(), REG_EXP_PATTERN)) {
            ((UIInput) uiComponent).setValid(false);
            String errorMessage = ResourceMessageProvider.
                    getMessage(NOT_VALID_MESSAGE_KEY, ERROR_MESSAGE_BUNDLE);
            UIComponent errorPanel = ComponentFinder.getInstance().findComponentBy(facesContext, ERROR_PANE_ID);
            if (errorPanel != null) {
                addWarningMessageTo(errorPanel, errorMessage);
            } else {
                throw new ValidatorException(new FacesMessage(errorMessage));
            }
        }
    }
}
