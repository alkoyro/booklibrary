package by.es.ejb.entity;

import by.es.ejb.entity.predefined.BookVersion;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "amountStats", query = "SELECT SUM(pe.amount) FROM PurchaseElement pe WHERE pe.purchase.purchaseDate > :startDate AND pe.purchase.purchaseDate < :endDate"),
        @NamedQuery(name = "profitStats", query = "SELECT SUM(pe.cost * pe.amount) FROM PurchaseElement pe WHERE pe.purchase.purchaseDate > :startDate AND pe.purchase.purchaseDate < :endDate"),
        @NamedQuery(name = "userPurchasesCount", query = "SELECT SUM(pe.amount) FROM PurchaseElement pe WHERE pe.purchase.user.id = :userId")

})
@Entity
@Table(name = "PURCHASE")
public class Purchase extends PersistenceEntity {

    @OneToMany(targetEntity = PurchaseElement.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "PURCHASE_ID", referencedColumnName = "ID")
    private List<PurchaseElement> purchaseElementList;

    @Column(name = "PURCHASE_DATE", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public List<PurchaseElement> getPurchaseElementList() {
        return purchaseElementList;
    }

    public void setPurchaseElementList(List<PurchaseElement> purchaseElementList) {
        this.purchaseElementList = purchaseElementList;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
