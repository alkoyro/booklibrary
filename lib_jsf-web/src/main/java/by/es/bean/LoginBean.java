package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.ejb.entity.User;
import by.es.navigation.LibraryNavigationHandler;
import by.es.realm.HashProvider;
import by.es.realm.entity.LibraryPrincipal;
import by.es.util.AppURLCreator;
import com.sun.enterprise.security.web.integration.WebPrincipal;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexey.Koyro
 */
@Named
@RequestScoped
public class LoginBean
{
    public static final String REQUESTED_PATH = "javax.servlet.forward.servlet_path";
    public static final String OUTCOME_NAVIGATION_RULE = "loginError";
    public static final String PARAM_FAIL_LOGIN = "result";
    public static final String FAILURE_RESULT = "failure";
    public static final String REQUESTED_PAGE_KEY = "requestedPage";
    public static final Integer MAX_REMEMBER_ME_COOKIE_LIFE_TIME = 60 * 60 * 24 * 14; //sec*min*hours*days
    public static final String DEFAULT_REMEMBER_ME_COOKIE_NAME = "rememberMe";
    public static final String OUTCOME_TO_MAIN = "main";

    @Inject
    private UserBean userBean;

    private String login;
    private String password;

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void performLogin()
    {
        FacesContext context = FacesContext.getCurrentInstance();

        try
        {
            login(login, password, context);
            redirectToRequestedPage(context);
        }
        catch (ServletException e)
        {
            redirectToLoginPage(context);
        }
    }

    public void preLogin(String param)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.
                getExternalContext().getRequest();

        WebPrincipal webPrincipal = (WebPrincipal) httpServletRequest.getUserPrincipal();
        if (webPrincipal == null && !param.equals(FAILURE_RESULT))
        {
            String requestedPage;

            if (context.getExternalContext().getRequestMap().
                    get(REQUESTED_PATH) != null)
            {
                requestedPage = httpServletRequest.getContextPath() +
                        context.getExternalContext().getRequestMap().get(REQUESTED_PATH);
            }
            else
            {
                requestedPage = httpServletRequest.getRequestURL().toString();
            }
            boolean isFirstParameter = true;
            if (requestedPage.contains("?"))
            {
                isFirstParameter = false;
            }
            requestedPage = requestedPage + AppURLCreator.getParameterString(httpServletRequest.
                    getParameterMap(), isFirstParameter);

            httpServletRequest.getSession().
                    setAttribute(LoginBean.REQUESTED_PAGE_KEY, requestedPage);

            if (loginWithCookie(context))
            {
                redirectToRequestedPage(context);
            }
        }
        else if (!param.equals(FAILURE_RESULT))
        {    //case when user have login page in bookmarks
            LibraryNavigationHandler navigationHandler = (LibraryNavigationHandler) context.
                    getApplication().getNavigationHandler();
            navigationHandler.redirectTo(OUTCOME_TO_MAIN, context,
                    Collections.<String, String>emptyMap());
        }

    }

    private boolean loginWithCookie(FacesContext context)
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.
                getExternalContext().getRequest();

        if (httpServletRequest.getCookies() != null)
        {
            for (Cookie cookie : httpServletRequest.getCookies())
            {
                if (cookie.getName().equals(DEFAULT_REMEMBER_ME_COOKIE_NAME))
                {
                    try
                    {
                        login(httpServletRequest.getRemoteHost(), cookie.getValue(), context);
                        return true;
                    }
                    catch (ServletException e)
                    {

                    }
                }
            }
        }
        return false;
    }

    private void login(String username, String password, FacesContext context) throws ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.
                getExternalContext().getRequest();
        HttpServletResponse servletResponse = (HttpServletResponse) context.
                getExternalContext().getResponse();

        httpServletRequest.login(username, password);
        addUser(httpServletRequest, servletResponse);
        switch (userBean.getUser().getUserStatus())
        {
            case NORMAL:
                break;
            case BLOCK:
                throw new InvalidDataException("userBlocked.error", NextStepRule.RETURN_TO_MAIN);
            default:
                break;
        }

    }

    private void redirectToRequestedPage(FacesContext context)
    {
        try
        {
            context.getExternalContext().redirect((String) context.getExternalContext().
                    getSessionMap().remove(REQUESTED_PAGE_KEY));
        }
        catch (IOException e)
        {
            throw new InvalidDataException("noSuchPageFound.error", NextStepRule.PREVIOUS_PAGE);
        }
    }

    /**
     * redirects in case of unsuccessful login procedure
     *
     * @param context
     */
    private void redirectToLoginPage(FacesContext context)
    {
        LibraryNavigationHandler navigationHandler = (LibraryNavigationHandler) context.
                getApplication().getNavigationHandler();

        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_FAIL_LOGIN, FAILURE_RESULT);
        navigationHandler.redirectTo(OUTCOME_NAVIGATION_RULE, context, params);
    }

    /**
     * installs cookie and updates userBean
     *
     * @param httpRequest
     * @param servletResponse
     */
    private void addUser(HttpServletRequest httpRequest, ServletResponse servletResponse)
    {
        WebPrincipal webPrincipal = (WebPrincipal) httpRequest.getUserPrincipal();
        for (Object principal : webPrincipal.getSecurityContext().getPrincipalSet())
        {
            if (principal instanceof LibraryPrincipal)
            {
                User user = userBean.loadUserBy(((LibraryPrincipal) principal).getLogin(),
                        ((LibraryPrincipal) principal).getPassword());
                String cookieValue = HashProvider.hashValue(((LibraryPrincipal) principal).getLogin() +
                        httpRequest.getRemoteHost());

                String userUuid = HashProvider.hashValue(httpRequest.getRemoteHost() + cookieValue);

                Cookie cookie = new Cookie(DEFAULT_REMEMBER_ME_COOKIE_NAME, cookieValue);
                cookie.setMaxAge(MAX_REMEMBER_ME_COOKIE_LIFE_TIME);
                cookie.setPath(httpRequest.getContextPath() + "/");
                ((HttpServletResponse) servletResponse).addCookie(cookie);

                user.setUuid(userUuid);
                userBean.update(user);
                userBean.setUser(user);
            }
        }
    }

}
