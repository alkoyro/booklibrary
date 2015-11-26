package by.es.util;

import by.es.ejb.entity.Book;


public class CartElement {

    private Book book;

    private int amount;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount > book.getNumberOfCopy())
        {
            amount = book.getNumberOfCopy();
        }
        this.amount = amount;
    }
}
