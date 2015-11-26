/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.User;
import by.es.realm.HashProvider;
import by.es.realm.entity.predefined.Permission;
import by.es.realm.entity.predefined.UserStatus;
import by.es.tags.ResourceMessageProvider;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Aleksey.Yaroshenko
 */
@ManagedBean
@ViewScoped
public class RegistrationBean {
    public static final String REGISTRATION_BUNDLE_NAME = "registrationBundle";
    public static final String ERROR_KEY_NAME = "error.icon";

    @Inject
    private UserBean userBean;
    @Inject
    private UserListBean userListBean;

    @EJB
    private UserDAO userDAO;
    private String login;
    private String firstPassword;
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private String email;

    private UIInput loginInput;
    private UIInput firstPasswordInput;
    private UIInput repeatPasswordInput;
    private UIInput firstNameInput;
    private UIInput lastNameInput;
    private UIInput emailInput;

    private UIComponent errorsPanel;

    /**
     * performs saving user and than login
     */
    public void register() {
        User user = new User();
        user.setLogin(login);
        user.setPassword(HashProvider.hashValue(firstPassword));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPermissions(getPermissions());
        user.setUserStatus(UserStatus.NORMAL);
        userDAO.addUser(user);
        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).
                    login(login, firstPassword);
            userBean.setUser(userDAO.findUserByLogin(login));
            userListBean.setDirty(true);
        } catch (ServletException e) {
            //todo if problems with authentificating user after registration saved data have to be rolled back
        }
    }

    private Set<Permission> getPermissions() {
        Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(Permission.VISIT_USER_AREA);
        permissions.add(Permission.ADD_TO_CART);
        permissions.add(Permission.LOOK_INTO_CART);

        return permissions;
    }

    /**
     * validates repeated password
     *
     * @param context
     * @param toValidate
     * @param value
     */
    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
        if (value.equals("") || !firstPasswordInput.getValue().equals(value)) {
            String errorMessage = ResourceMessageProvider.getMessage("notValidPassword.text", REGISTRATION_BUNDLE_NAME);
            ((UIInput) toValidate).setValid(false);
            addWarningMessage(errorMessage);
        }
    }

    public void validateLogin(FacesContext context, UIComponent toValidate, Object value) {

        if (userDAO.isUserExists((String) value)) {
            String errorMessage = ResourceMessageProvider.getMessage("userExist.text", REGISTRATION_BUNDLE_NAME);
            ((UIInput) toValidate).setValid(false);
            addWarningMessage(errorMessage);
        }
    }


    private void addWarningMessage(String message) {
        UIOutput errorMessage = new UIOutput();
        errorMessage.setValue(message);
        UIGraphic errorIcon = new UIGraphic();
        errorIcon.setRendererType("javax.faces.Image");
        errorIcon.setValue(ResourceMessageProvider.getIcon(ERROR_KEY_NAME, REGISTRATION_BUNDLE_NAME));
        errorsPanel.getChildren().add(errorIcon);
        errorsPanel.getChildren().add(errorMessage);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public UIInput getLoginInput() {
        return loginInput;
    }

    public void setLoginInput(UIInput loginInput) {
        this.loginInput = loginInput;
    }

    public UIInput getFirstPasswordInput() {
        return firstPasswordInput;
    }

    public void setFirstPasswordInput(UIInput firstPasswordInput) {
        this.firstPasswordInput = firstPasswordInput;
    }

    public UIInput getRepeatPasswordInput() {
        return repeatPasswordInput;
    }

    public void setRepeatPasswordInput(UIInput repeatPasswordInput) {
        this.repeatPasswordInput = repeatPasswordInput;
    }

    public UIInput getFirstNameInput() {
        return firstNameInput;
    }

    public void setFirstNameInput(UIInput firstNameInput) {
        this.firstNameInput = firstNameInput;
    }

    public UIInput getLastNameInput() {
        return lastNameInput;
    }

    public void setLastNameInput(UIInput lastNameInput) {
        this.lastNameInput = lastNameInput;
    }

    public UIInput getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(UIInput emailInput) {
        this.emailInput = emailInput;
    }

    public UIComponent getErrorsPanel() {
        return errorsPanel;
    }

    public void setErrorsPanel(UIComponent errorsPanel) {
        this.errorsPanel = errorsPanel;
    }
}
