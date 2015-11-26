package by.es.ejb.entity;

import by.es.ejb.entity.predefined.BookStatus;
import by.es.ejb.entity.predefined.PurchaseType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "bestSellingBooks",
                query = "SELECT p.book, SUM(p.amount) FROM PurchaseElement as p GROUP BY p.book ORDER BY SUM(p.amount) DESC "),
        @NamedQuery(name = "mostPofitableBooks",
                query = "SELECT p.book, SUM(p.cost * p.amount) FROM PurchaseElement as p GROUP BY p.book ORDER BY SUM(p.cost * p.amount) DESC")
})

@Entity
@Table(name = "BOOK")
public class Book extends PersistenceEntity {

    @Column(name = "TITLE", length = 255, nullable = false)
    private String title;

    @Column(name = "AUTHOR", length = 255, nullable = false)
    private String author;

    @Column(name = "DESCRIPTION",length = 1023)
    private String description;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column(name = "EBOOK_FILENAME", length = 255)
    private String eBookFileName;

    @Column(name = "PURCHASE_TYPE")
    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;

    @Column(name = "AMOUNT", nullable = false)
    private int numberOfCopy;

    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String geteBookFileName() {
        return eBookFileName;
    }

    public void seteBookFileName(String eBookFileName) {
        this.eBookFileName = eBookFileName;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }


    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
    
    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public int getNumberOfCopy() {
        return numberOfCopy;
    }

    public void setNumberOfCopy(int numberOfCopy) {
        this.numberOfCopy = numberOfCopy;
    }

}
