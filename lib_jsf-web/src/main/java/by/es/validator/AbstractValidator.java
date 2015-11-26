package by.es.validator;

import by.es.tags.ResourceMessageProvider;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIOutput;
import javax.faces.validator.Validator;

/**
 * User: Alexey.Koyro
 */
public abstract class AbstractValidator implements Validator {
    public static final String ERROR_KEY_NAME = "error.icon";
    public static final String ERROR_MESSAGE_BUNDLE = "errors";
    public static final String ERROR_PANE_ID = "errorMessages";

    protected void addWarningMessageTo(UIComponent errorsPanel, String message) {
        UIOutput errorMessage = new UIOutput();
        errorMessage.setValue(message);
        UIGraphic errorIcon = new UIGraphic();
        errorIcon.setRendererType("javax.faces.Image");
        errorIcon.setValue(ResourceMessageProvider.getIcon(ERROR_KEY_NAME, ERROR_MESSAGE_BUNDLE));
        errorsPanel.getChildren().add(errorIcon);
        errorsPanel.getChildren().add(errorMessage);
    }
}
