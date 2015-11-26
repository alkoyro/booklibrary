package by.es.bean;

import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.User;
import by.es.realm.entity.predefined.Permission;
import by.es.realm.entity.predefined.UserStatus;
import by.es.tags.PermissionResolver;
import by.es.tags.ResourceMessageProvider;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.*;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class EditUserBean implements Serializable
{
    public static final String EDIT_USER_BUNDLE_NAME = "editUserBundle";
    public static final String ERROR_KEY_NAME = "error.icon";
    private static final Logger log = Logger.getLogger(EditUserBean.class.getName());

    @Inject
    private UserListBean userListBean;

    @EJB
    private UserDAO userDAO;
    private User user;
    private List<SelectItem> allowedPermissionItems;
    private List<SelectItem> userPermissionItems;
    private HtmlSelectOneMenu selectStatusItem;
    private String resultPermissions;
    private UIComponent errorsPanel;

    public void initEnv(Long userId)
    {
        if (user == null)
        {
            this.user = userDAO.loadUserBy(userId);

            initUserPermissions();
            initAllowedPermissions();
        }
    }

    public List<SelectItem> getAllowedPermissionItems()
    {
        return allowedPermissionItems;
    }

    public List<SelectItem> getUserPermissionItems()
    {
        return userPermissionItems;
    }

    private void initUserPermissions()
    {
        Set<Permission> permissions = user.getPermissions();
        userPermissionItems = new ArrayList<SelectItem>();
        for (Permission permission : permissions)
        {
            SelectItem item = new SelectItem(permission, permission.name());
            userPermissionItems.add(item);
        }
    }

    private void initAllowedPermissions()
    {
        Permission[] permissions = PermissionResolver.getCurrentUserPermissionsExcept(user.getPermissions());
        allowedPermissionItems = new ArrayList<SelectItem>();
        for (Permission permission : permissions)
        {
            SelectItem item = new SelectItem(permission, permission.name());
            allowedPermissionItems.add(item);
        }
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    public void clearWarnings()
    {
        errorsPanel.getChildren().clear();
    }

    private void addWarningMessage(String message)
    {
        UIOutput errorMessage = new UIOutput();
        errorMessage.setValue(message);
        UIGraphic errorIcon = new UIGraphic();
        errorIcon.setRendererType("javax.faces.Image");
        errorIcon.setValue(ResourceMessageProvider.getIcon(ERROR_KEY_NAME, EDIT_USER_BUNDLE_NAME));
        errorsPanel.getChildren().add(errorIcon);
        errorsPanel.getChildren().add(errorMessage);
    }

    public void validateDiscount(FacesContext context, UIComponent toValidate, Object value)
    {
        Double discountValue = (Double) value;
        if (discountValue > 100 || discountValue < 0)
        {
            String errorMessage = ResourceMessageProvider.getMessage("notValidDiscount.text", EDIT_USER_BUNDLE_NAME);
            ((UIInput) toValidate).setValid(false);
            addWarningMessage(errorMessage);
        }
    }

    public void applyChanges()
    {
        User updatedUser = getUser();
        Set<Permission> udpdatedPermissions = applyPermissions();
        updatedUser.setPermissions(udpdatedPermissions);
        userDAO.updateUser(updatedUser);
        userListBean.setDirty(true);
    }

    private Set<Permission> applyPermissions()
    {
        Set<Permission> udpdatedPermissions = new HashSet<Permission>();
        StringTokenizer stringTokenizer = new StringTokenizer(getResultPermissions(), ",");
        while (stringTokenizer.hasMoreTokens())
        {
            udpdatedPermissions.add(Permission.valueOf(Permission.class,
                    stringTokenizer.nextToken()));
        }
        return udpdatedPermissions;
    }

    public HtmlSelectOneMenu getSelectStatusItem()
    {
        return selectStatusItem;
    }

    public void setSelectStatusItem(HtmlSelectOneMenu selectStatusItem)
    {
        this.selectStatusItem = selectStatusItem;
        initStatusSelectItem();
    }

    private void initStatusSelectItem()
    {
        for (UserStatus status : UserStatus.values())
        {
            UISelectItem item = new UISelectItem();
            item.setItemLabel(ResourceMessageProvider.getUserStatusMessage(status));
            item.setItemValue(status);
            selectStatusItem.getChildren().add(item);
        }
    }

    public String getResultPermissions()
    {
        return resultPermissions;
    }

    public void setResultPermissions(String resultPermissions)
    {
        this.resultPermissions = resultPermissions;
    }

    public UIComponent getErrorsPanel()
    {
        return errorsPanel;
    }

    public void setErrorsPanel(UIComponent errorsPanel)
    {
        this.errorsPanel = errorsPanel;
    }
}
