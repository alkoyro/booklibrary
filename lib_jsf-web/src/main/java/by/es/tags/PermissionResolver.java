package by.es.tags;

import by.es.bean.UserBean;
import by.es.ejb.entity.User;
import by.es.realm.entity.predefined.Permission;

import javax.faces.context.FacesContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexey.Koyro
 */
public final class PermissionResolver
{
    private PermissionResolver()
    {
    }

    public static boolean isAllowed(String permission)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        return getCurrentUser() != null &&
                context.getExternalContext().isUserInRole(permission);
    }

    /**
     * loads the user who is current in session
     * @return
     */
    private static User getCurrentUser()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().
                evaluateExpressionGet(context, "#{userBean}", UserBean.class);
        return userBean.getUser();
    }

    /**
     * checks is current user have enought permissions to edit param user
     * <p/>
     * example: param user have permission to be a admin, but current user can edit managers only!!!
     *
     * @param user
     * @return
     */
    public static boolean isAllowedToEdit(User user)
    {
        //the case where user tries to edit himself
        if (user.equals(getCurrentUser()))
        {
            return false;
        }

        //this case is can everything
        if (isAllowed(Permission.VISIT_ADMIN_AREA.name()) &&
                isAllowed(Permission.EDIT_MANAGER.name()) && isAllowed(Permission.EDIT_USER.name()))
        {
            return true;
        }

        if(user.getPermissions().contains(Permission.VISIT_ADMIN_AREA))
        {
            return false;
        }

        //case where manager can edit managers and rest of users
        if (isAllowed(Permission.EDIT_USER.name()) &&
                isAllowed(Permission.EDIT_MANAGER.name()))
        {
            return true;
        }

        //case where manager can edit managers but users
        if (isAllowed(Permission.EDIT_MANAGER.name())
                &&
                !user.getPermissions().contains(Permission.EDIT_USER))
        {
            return true;
        }

        //case where user can edit user
        if (isAllowed(Permission.EDIT_USER.name())
                &&
                !user.getPermissions().contains(Permission.VISIT_MANAGEMENT_AREA)
                &&
                !user.getPermissions().contains(Permission.EDIT_MANAGER))
        {
            return true;
        }

        return false;

    }

    public static Permission[] getCurrentUserPermissionsExcept(Set<Permission> exceptPermissions)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().
                evaluateExpressionGet(context, "#{userBean}", UserBean.class);
        Set<Permission> currentUserPermissions = userBean.getUser().getPermissions();
        Set<Permission> result = new HashSet<Permission>();

        for (Permission permission : currentUserPermissions)
        {
            if (!exceptPermissions.contains(permission))
            {
                result.add(permission);
            }
        }

        return result.toArray(new Permission[result.size()]);
    }

}
