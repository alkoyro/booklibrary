package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.PersistenceEntity;
import by.es.ejb.entity.User;
import by.es.mail.MailSender;
import by.es.realm.HashProvider;
import by.es.util.AppProps;
import by.es.util.AppURLCreator;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 10.02.12
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */

@Named
@RequestScoped
public class RestorePasswordBean {

    private static final Logger log = Logger.getLogger(RestorePasswordBean.class.getName());

    @EJB
    private UserDAO userDAO;

    private String email;
    private String password;
    private String login;
    private String token;


    /* Aleksey.Yaroshenko@effective-soft.com */
    public void sendRestorePasswordEmail() {
        log.info(AppURLCreator.getAppURL());
        User user = userDAO.findByEmail(email);
        if (user != null) {
            StringBuilder message = new StringBuilder();
            message.append(AppProps.get("password.restore.email.message"));
            message.append("\n");
            message.append(AppURLCreator.getAppURL());
            message.append('/');
            message.append("restorePassword.faces?login=");
            message.append(user.getLogin());
            message.append("&token=");
            message.append(HashProvider.hashValue(user.getPassword()));
            MailSender mailSender = new MailSender();
            mailSender.sendMail(email, MailSender.SUBJECT_PASSWORD_RESTORE, message.toString());
        } else {
            throw new InvalidDataException("email.invalid", NextStepRule.PREVIOUS_PAGE);
        }
    }

    public boolean isValidPage() {
        User user = userDAO.findUserByLogin(login);
        if ((user == null) || !HashProvider.hashValue(user.getPassword()).equals(token)) {
            try {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath());
            } catch (IOException e) {
                log.severe(e.getMessage());
            }
            
            return false;
        }
        return true;
    }

    public void changePassword() {
        User user = userDAO.findUserBy(login, token);
        if (user != null) {
            String newPassword = HashProvider.hashValue(password);
            user.setPassword(newPassword);
            userDAO.updateUser(user);
        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
