package by.es.auth.exception;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Alexey.Koyro
 *
 * contains last visited view
 */
@Named
@ManagedBean
@RequestScoped
public class ExceptionDetailBean
{
    public static final String VISITED_PAGE_KEY = "lastVisitedPage";
    public static final String REDIRECT_SUFFIX = "?faces-redirect=true";

    public void redirectOnClick()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        String navigationUrl = null;
        if(context.getExternalContext().getSessionMap().containsKey(VISITED_PAGE_KEY))
        {
            navigationUrl = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().remove(VISITED_PAGE_KEY);
            navigationUrl += REDIRECT_SUFFIX;
        }
        else
        {
            navigationUrl = "main";
        }
        if (navigationUrl.contains(((HttpServletRequest)context.getExternalContext().getRequest()).getContextPath()))
        {
            int lastIndex = navigationUrl.lastIndexOf(((HttpServletRequest)context.getExternalContext().
                    getRequest()).getContextPath());
            navigationUrl = navigationUrl.substring(lastIndex +
                    ((HttpServletRequest)context.getExternalContext().getRequest()).getContextPath().length(),
                    navigationUrl.length());
        }
        context.getApplication().getNavigationHandler().handleNavigation(context, null, navigationUrl);
    }
}
