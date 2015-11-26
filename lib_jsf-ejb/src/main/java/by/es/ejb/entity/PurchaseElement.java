package by.es.ejb.entity;

import by.es.ejb.entity.predefined.BookVersion;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PURCHASE_ELEMENT")
public class PurchaseElement extends PersistenceEntity {

    @OneToOne(targetEntity = Book.class)
    private Book book;

    @Column(name = "VERSION", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookVersion bookVersion;

    @Column(name = "AMOUNT", nullable = false)
    private int amount;

    @Column(name = "COST")
    private double cost;

    @ManyToOne(targetEntity = Purchase.class, cascade = CascadeType.ALL)
    private Purchase purchase;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookVersion getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(BookVersion bookVersion) {
        this.bookVersion = bookVersion;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
