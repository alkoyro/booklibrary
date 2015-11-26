/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.ejb.dao.BookDAO;
import by.es.ejb.dto.BestSellingBook;
import by.es.ejb.dto.MostProfitableBook;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class BookStatBean implements Serializable {

    private static final Logger log = Logger.getLogger(BookStatBean.class.getName());
    private static final int DEFAULT_STATS_QUANTITY = 5;
    @EJB
    private BookDAO bookDAO;

    private List<BestSellingBook> bestSellingBookList;
    private List<MostProfitableBook> mostProfitableBooks;

    /* JSON objects */
    private String bestSellingBooksJsonLabel;
    private String bestSellingBooksJsonValue;
    private String mostProfitableBooksJsonLabel;
    private String mostProfitableBooksJsonValue;

    /**
     *
     * @return List of BestSellingBook objects
     */
    public List<BestSellingBook> bestSellingBooks() {
        return bestSellingBooks(DEFAULT_STATS_QUANTITY);
    }

    /**
     *
     * @return List of MostProfitableBook objects
     */
    public List<MostProfitableBook> mostProfitableBooks() {
        return mostProfitableBooks(DEFAULT_STATS_QUANTITY);
    }

    /**
     *
     * @return JSON object with array of List<BestCellingBook> titles
     */
    public String getBestSellingBooksJsonLabel() {
        if (bestSellingBooksJsonLabel == null) {
            bestSellingBooksJson(bestSellingBooks());
        }
        return bestSellingBooksJsonLabel;
    }

    /**
     *
     * @return JSON object with array of List<BestCellingBook> quantities
     */
    public String getBestSellingBooksJsonValue() {
        if (bestSellingBooksJsonValue == null) {
            bestSellingBooksJson(bestSellingBooks());
        }
        return bestSellingBooksJsonValue;
    }

    /**
     *
     * @return JSON object with array of List<MostProfitableBook> titles
     */
    public String getMostProfitableBooksJsonLabel() {
        if (mostProfitableBooksJsonLabel == null) {
            mostProfitableBooksJson(mostProfitableBooks());
        }
        return mostProfitableBooksJsonLabel;
    }

    /**
     *
     * @return JSON object with array of List<MostProfitableBook> profits
     */
    public String getMostProfitableBooksJsonValue() {
        if (mostProfitableBooksJsonValue == null) {
            mostProfitableBooksJson(mostProfitableBooks());
        }
        return mostProfitableBooksJsonValue;
    }


    private List<BestSellingBook> bestSellingBooks(int number) {
        if (bestSellingBookList == null) {
            bestSellingBookList = bookDAO.getBestSellingBooks(number);
        }
        return bestSellingBookList;
    }


    private List<MostProfitableBook> mostProfitableBooks(int number) {
        if (mostProfitableBooks == null) {
            mostProfitableBooks = bookDAO.getMostProfitableBooks(number);
        }
        return mostProfitableBooks;
    }
    
    private void bestSellingBooksJson(List<BestSellingBook> list) {
        List<String> label = new ArrayList<String>();
        List<Long> value = new ArrayList<Long>();
        for (BestSellingBook b : list) {
            label.add(b.getBook().getTitle());
            value.add(b.getQuantity());
        }
        Gson gson = new Gson();
        bestSellingBooksJsonLabel = gson.toJson(label);
        bestSellingBooksJsonValue = gson.toJson(value);
    }

    private void mostProfitableBooksJson(List<MostProfitableBook> list) {
        List<String> label = new ArrayList<String>();
        List<Double> value = new ArrayList<Double>();
        for (MostProfitableBook m : list) {
            label.add(m.getBook().getTitle());
            value.add(m.getProfit());
        }
        Gson gson = new Gson();
        mostProfitableBooksJsonLabel = gson.toJson(label);
        mostProfitableBooksJsonValue = gson.toJson(value);
    }


}
