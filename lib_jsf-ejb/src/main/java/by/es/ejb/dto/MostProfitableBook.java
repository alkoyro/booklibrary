package by.es.ejb.dto;

import by.es.ejb.entity.Book;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 07.02.12
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class MostProfitableBook {
    
    private Book book;
    private double profit;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
