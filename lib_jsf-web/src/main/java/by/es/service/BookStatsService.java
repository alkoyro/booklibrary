package by.es.service;

import by.es.ejb.dao.BookDAO;
import by.es.ejb.dto.BestSellingBook;
import by.es.ejb.dto.MostProfitableBook;
import by.es.predefined.BookStats;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 09.02.12
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */

@Stateless
@Path("/bookstats/{statName}")
public class BookStatsService {

    private static final Logger log = Logger.getLogger(BookStatsService.class.getName());

    @EJB
    private BookDAO bookDAO;

    private String statName;

    public BookStatsService() {
        log.info("BookStatsService object created");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatJson(@PathParam("statName") String statName) {
        String json = null;
        try {
            BookStats bookStats = BookStats.valueOf(statName.toUpperCase());
            log.info("BooStatService called! statname = " + statName);
            switch (bookStats) {
                case BEST_SELLING_BOOKS:
                    json = getJson(bestSellingBooks());
                    break;
                case MOST_PROFITABLE_BOOKS:
                    json = getJson(mostProfitableBooks());
                    break;
                default: json = "Identifier is not implemented";
            }
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Incorrect stats identifier. Available identifiers: ");
            BookStats[] bs = BookStats.values();
            for (int i = 0; i < bs.length; i++) {
                sb.append(bs[i].name());
                if (i < (bs.length - 1)) {
                    sb.append(", ");
                }
            }
            json = sb.toString();
        }
        return json;
    }


    private ToJson bestSellingBooks() {
        List<BestSellingBook> bestSellingBooks = bookDAO.getBestSellingBooks(5);
        ToJson toJson = new ToJson();
        for (BestSellingBook b : bestSellingBooks) {
            toJson.labels.add(b.getBook().getTitle());
            toJson.values.add(Double.valueOf(b.getQuantity()));
        }
        return toJson;
    }

    private ToJson mostProfitableBooks() {
        List<MostProfitableBook> mostProfitableBooks = bookDAO.getMostProfitableBooks(5);
        ToJson toJson = new ToJson();
        for (MostProfitableBook b : mostProfitableBooks) {
            toJson.labels.add(b.getBook().getTitle());
            toJson.values.add(b.getProfit());
        }
        return toJson;
    }

    private String getJson(ToJson toJson) {
        Gson gson = new Gson();
        return gson.toJson(toJson);
    }


}
