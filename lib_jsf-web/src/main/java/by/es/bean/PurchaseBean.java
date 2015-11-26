package by.es.bean;

import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.entity.Purchase;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Aleksey.Yaroshenko
 */
@ManagedBean
@RequestScoped
public class PurchaseBean implements Serializable {

    private static final Logger log = Logger.getLogger(PurchaseBean.class.getName());
    @EJB
    private PurchaseDAO purchaseDAO;
    @Inject
    private UserBean userBean;

    private List<Purchase> purchaseUserList;

    private List<Purchase> purchaseList;

    public List<Purchase> getPurchaseUserList() {
        if (purchaseUserList == null) {
            purchaseUserList = purchaseDAO.findByUser(userBean.getUser());
        }
        return purchaseUserList;
    }

    public List<Purchase> getPurchaseList() {
        if (purchaseList == null) {
            purchaseList = purchaseDAO.getPurchaseList();
        }
        return purchaseList;
    }

    public void setPurchaseUserList(List<Purchase> purchaseUserList) {
        this.purchaseUserList = purchaseUserList;
    }


}
