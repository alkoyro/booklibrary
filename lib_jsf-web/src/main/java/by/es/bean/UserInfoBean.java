/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@ManagedBean
@ViewScoped
public class UserInfoBean implements Serializable {

    private static final Logger log = Logger.getLogger(UserInfoBean.class.getName());
    @EJB
    private UserDAO userDAO;
    @EJB
    private PurchaseDAO purchaseDAO;
    private User user;

    private long userPurchaseCount;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void loadUserById(Long id) {
        User user = userDAO.loadUserBy(id);
        if (user != null) {
            this.user = user;
            userPurchaseCount = purchaseDAO.findCountByUser(user);
        }
    }


    public long getUserPurchaseCount() {
        return userPurchaseCount;
    }

    public void setUserPurchaseCount(long userPurchaseCount) {
        this.userPurchaseCount = userPurchaseCount;
    }
}
