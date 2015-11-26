package by.es.ejb.dao;

import by.es.ejb.entity.Book;
import by.es.ejb.entity.Purchase;
import by.es.ejb.entity.User;
import by.es.ejb.entity.predefined.BookVersion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Aleksey.Yaroshenko Date: 22.12.11 Time: 11:08
 * To change this template use File | Settings | File Templates.
 */
public interface PurchaseDAO {

    List<Purchase> getPurchaseList();

    List<Purchase> findByUser(User user);
    
    Long findCountByUser(User user);

    Purchase save(Purchase purchase);
    
    Long amountByPeriod(Date startDate, Date endDate);

    BigDecimal profitByPeriod(Date startDate, Date endDate);

    boolean isExists(Long userId, Long bookId, BookVersion bookVersion);
    
    boolean isExists(User user, Book book, BookVersion bookVersion);

}
