/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.ejb.dao.impl;

import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.dao.VisitDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Purchase;
import by.es.ejb.entity.User;
import by.es.ejb.entity.Visit;
import by.es.ejb.entity.predefined.BookVersion;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Aleksey.Yaroshenko
 */

@Stateless
public class VisitDaoImpl implements VisitDAO{
    
    private static final Logger log = Logger.getLogger(VisitDaoImpl.class.getName());

    @PersistenceContext(unitName = "libraryPersistence")
    private EntityManager entityManager;


    @Override
    public Visit save(Visit visit) {
        return entityManager.merge(visit);
    }

    @Override
    public Long getUniqueVisitorCount(Date date) {
        Query query = entityManager.createNamedQuery("uniqueVisitorCount");
        query.setParameter("date", date);
        return (Long) query.getSingleResult();
    }


}
