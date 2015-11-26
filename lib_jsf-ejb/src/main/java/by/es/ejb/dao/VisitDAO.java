package by.es.ejb.dao;

import by.es.ejb.entity.Price;
import by.es.ejb.entity.Visit;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: Aleksey.Yaroshenko Date: 21.12.11 Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
public interface VisitDAO {
    Visit save(Visit visit);
    
    Long getUniqueVisitorCount(Date date);
}
