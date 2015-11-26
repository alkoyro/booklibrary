package by.es.validator;

import by.es.tags.ResourceMessageProvider;
import by.es.util.ComponentFinder;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * User: Alexey.Koyro
 */
public class RequiredValueValidator extends AbstractValidator {
    public static final String NOT_VALID_MESSAGE_KEY = "requiredFieldIsEmpty.error";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        if (((String) value).isEmpty()) {

            ((UIInput) uiComponent).setValid(false);
            String errorMessage = ResourceMessageProvider.
                    getMessage(NOT_VALID_MESSAGE_KEY, ERROR_MESSAGE_BUNDLE);
            errorMessage += uiComponent.getClientId();
            UIComponent errorPanel = ComponentFinder.getInstance().findComponentBy(facesContext, ERROR_PANE_ID);
            if (errorPanel != null) {
                addWarningMessageTo(errorPanel, errorMessage);
            } else {
                throw new ValidatorException(new FacesMessage(errorMessage));
            }
        }
    }
}

