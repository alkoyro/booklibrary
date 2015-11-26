/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.ejb.dao.BookDAO;
import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@SessionScoped
public class UserBean implements Serializable
{

    private static final Logger log = Logger.getLogger(UserBean.class.getName());
    @EJB
    private UserDAO userDAO;
    @EJB
    private BookDAO bookDAO;
    private User user;
    @Inject
    private BookListBean bookListBean;

    public void logoff()
    {
        invalidateUserUUID();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        removeCookie();
    }

    private void invalidateUserUUID()
    {
        user.setUuid(null);
        userDAO.updateUser(user);
    }

    private void removeCookie()
    {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().
                getExternalContext().getRequest();
        HttpServletResponse servletResponse = (HttpServletResponse) FacesContext.
                getCurrentInstance().getExternalContext().getResponse();
        for (Cookie cookie : httpRequest.getCookies())
        {
            if (cookie.getName().equals(LoginBean.DEFAULT_REMEMBER_ME_COOKIE_NAME))
            {
                Cookie clearCookie = new Cookie(LoginBean.DEFAULT_REMEMBER_ME_COOKIE_NAME, null);
                clearCookie.setMaxAge(0);
                servletResponse.addCookie(clearCookie);
            }
        }

    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User loadUserBy(String login, String password)
    {
        return userDAO.findUserBy(login, password);
    }

    public void update(User user)
    {
        userDAO.updateUser(user);
    }
}
