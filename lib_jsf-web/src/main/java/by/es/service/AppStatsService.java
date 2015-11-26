package by.es.service;

import by.es.ejb.dao.BookDAO;
import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.dto.BestSellingBook;
import by.es.ejb.dto.MostProfitableBook;
import by.es.predefined.AppStats;
import by.es.predefined.BookStats;
import by.es.predefined.DateConstants;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@Path("/appstats/{statName}")
public class AppStatsService {

    private static final Logger log = Logger.getLogger(AppStatsService.class.getName());

    @EJB
    private PurchaseDAO purchaseDAO;

    private String statName;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");

    public AppStatsService() {
        log.info("AppStatsService object created");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatJson(@PathParam("statName") String statName) {
        String json = null;
        try {
            AppStats appStats = AppStats.valueOf(statName.toUpperCase());
            log.info("BooStatService called! statname = " + statName);
            switch (appStats) {
                case AMOUNT:
                    json = getJson(getAmountStats());
                    break;
                case PROFIT:
                    json = getJson(getProfitStats());
                    break;
                default: json = "Identifier is not implemented";
            }
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Incorrect stats identifier. Available identifiers: ");
            AppStats[] bs = AppStats.values();
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


    private ToJson getAmountStats() {
        Date date = new Date();
        long currentTimeMills = date.getTime();
        date.setTime(date.getTime() - DateConstants.MONTH_MILLS);
        ToJson toJson = new ToJson();
        while (date.getTime() < currentTimeMills) {
            Date nextDay = new Date(date.getTime() + DateConstants.DAY_MILLS);

            double value = 0;
            Long result = purchaseDAO.amountByPeriod(date, nextDay);
            if (result != null) {
                value = Double.valueOf(result);
            }
            toJson.values.add(value);
            date = nextDay;
            toJson.labels.add(simpleDateFormat.format(date));
        }
        return toJson;
    }

    private ToJson getProfitStats() {
        Date date = new Date();
        long currentTimeMills = date.getTime();
        date.setTime(date.getTime() - DateConstants.MONTH_MILLS);
        ToJson toJson = new ToJson();
        while (date.getTime() < currentTimeMills) {
            Date nextDay = new Date(date.getTime() + DateConstants.DAY_MILLS);

            double value = 0;
            BigDecimal result = purchaseDAO.profitByPeriod(date, nextDay);
            if (result != null) {
                value = result.doubleValue();
            }
            toJson.values.add(value);
            date = nextDay;
            toJson.labels.add(simpleDateFormat.format(date));
        }
        return toJson;
    }

    private String getJson(ToJson toJson) {
        Gson gson = new Gson();
        return gson.toJson(toJson);
    }


}
