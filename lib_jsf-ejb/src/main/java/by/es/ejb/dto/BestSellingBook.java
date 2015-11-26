package by.es.ejb.dto;

import by.es.ejb.entity.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 07.02.12
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class BestSellingBook {
    
    private Book book;
    private long quantity;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
