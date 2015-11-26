package by.es.bean;

import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Aleksey.Yaroshenko
 */
@Named
@ApplicationScoped
public class UserListBean implements Serializable {

    private static final Logger log = Logger.getLogger(UserListBean.class.getName());
    @EJB
    private UserDAO userDAO;
    private List<User> userList;
    private boolean dirty;

    public List<User> getUserList() {
        fixDirty();
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void fixDirty() {
        if ((userList == null) || (isDirty())) {
            setDirty(false);
            userList = userDAO.getUserList();
        }
    }
}
