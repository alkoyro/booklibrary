package by.es.ejb.dao;

import by.es.ejb.entity.Price;

/**
 * Created by IntelliJ IDEA. User: Aleksey.Yaroshenko Date: 21.12.11 Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
public interface PriceDAO {

    boolean addPrice(Price price);

    Price getPrice(int priceId);

    boolean updatePrice(Price price, int priceId);

    boolean deletePrice(int priceId);
}
