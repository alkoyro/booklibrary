package by.es.navigation;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexey.Koyro
 */
public class LibraryNavigationHandler extends ConfigurableNavigationHandler
{
    public static final String REDIRECT_SUFFIX = "?faces-redirect=true";
    private NavigationHandler parent;

    public LibraryNavigationHandler(NavigationHandler parent)
    {
        this.parent = parent;
    }

    @Override
    public NavigationCase getNavigationCase(FacesContext facesContext, String fromAction, String outcome)
    {
        if (parent instanceof ConfigurableNavigationHandler)
        {
            return ((ConfigurableNavigationHandler) parent).getNavigationCase(facesContext, fromAction, outcome);
        }
        return null;
    }

    @Override
    public Map<String, Set<NavigationCase>> getNavigationCases()
    {
        if (parent instanceof ConfigurableNavigationHandler)
        {
            return ((ConfigurableNavigationHandler) parent).getNavigationCases();
        }
        return null;
    }

    @Override
    public void handleNavigation(FacesContext facesContext, String from, String outcome)
    {
        parent.handleNavigation(facesContext, from, outcome);
    }

    public void redirectTo(String outcomeNavigationRule, FacesContext facesContext,
                           Map<String, String> params)
    {
        NavigationCase navigationCase = getNavigationCase(facesContext, null, outcomeNavigationRule);
        String navigationUrl = navigationCase.getToViewId(facesContext);
        //applying redirect
        navigationUrl += REDIRECT_SUFFIX;
        for (String paramKey : params.keySet())
        {
            navigationUrl += '&';
            navigationUrl += paramKey;
            navigationUrl += '=';
            navigationUrl += params.get(paramKey);
        }
        handleNavigation(facesContext, null, navigationUrl);
    }
}
