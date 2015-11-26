/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.ejb.dao.impl;

import by.es.ejb.dao.CommentDAO;
import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Comment;
import by.es.ejb.entity.Purchase;
import by.es.ejb.entity.User;
import by.es.ejb.entity.predefined.BookVersion;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Aleksey.Yaroshenko
 */

@Stateless
public class PurchaseDaoImpl implements PurchaseDAO{
    
    private static final Logger log = Logger.getLogger(PurchaseDaoImpl.class.getName());

    @PersistenceContext(unitName = "libraryPersistence")
    private EntityManager entityManager;


    @Override
    public List<Purchase> getPurchaseList() {
        String ejbql = "SELECT p FROM Purchase p ORDER BY p.purchaseDate DESC";
        Query query = entityManager.createQuery(ejbql);
        return query.getResultList();
    }

    @Override
    public List<Purchase> findByUser(User user) {
        String ejbql = "SELECT p FROM Purchase p WHERE p.user.id = :userId ORDER BY p.purchaseDate DESC";
        Query query = entityManager.createQuery(ejbql);
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }

    @Override
    public Long findCountByUser(User user) {
        Query query = entityManager.createNamedQuery("userPurchasesCount");
        query.setParameter("userId", user.getId());
        return (Long) query.getSingleResult();

    }

    @Override
    public Purchase save(Purchase purchase) {
        return entityManager.merge(purchase);
    }

    @Override
    public Long amountByPeriod(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("amountStats");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return (Long) query.getSingleResult();
    }

    @Override
    public BigDecimal profitByPeriod(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("profitStats");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return (BigDecimal)  query.getSingleResult();
    }

    @Override
    public boolean isExists(Long userId, Long bookId, BookVersion bookVersion) {
        String ejbql = "SELECT count(pe) FROM PurchaseElement pe WHERE pe.purchase.user.id = :userId AND pe.book.id = :bookId AND pe.bookVersion = :bookVersion ";
        Query query = entityManager.createQuery(ejbql);
        query.setParameter("userId", userId);
        query.setParameter("bookId", bookId);
        query.setParameter("bookVersion", bookVersion);
        Long count = (Long) query.getSingleResult();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExists(User user, Book book, BookVersion bookVersion) {
        return isExists(user.getId(), book.getId(), bookVersion);
    }
}
