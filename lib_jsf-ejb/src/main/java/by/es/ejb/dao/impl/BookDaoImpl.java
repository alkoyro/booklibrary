/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.ejb.dao.impl;

import by.es.ejb.dto.BestSellingBook;
import by.es.ejb.dto.MostProfitableBook;
import by.es.ejb.entity.predefined.BookStatus;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.entity.Book;
import by.es.performance.CheckPerfomance;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Stateless
@DeclareRoles(value = {"ADD_BOOK", "EDIT_BOOK", "ADD_TO_CART"})
public class BookDaoImpl implements BookDAO {

    private static final Logger log = Logger.getLogger(BookDaoImpl.class.getName());
    @PersistenceContext(unitName = "libraryPersistence")
    private EntityManager entityManager;

    @RolesAllowed({"EDIT_BOOK"})
    @Override
    public Book update(Book book) {
        return entityManager.merge(book);
    }

    @RolesAllowed(value = {"ADD_BOOK"})
    @Override
    public Book save(Book book) {
        return entityManager.merge(book);
    }

    @RolesAllowed({"ADD_TO_CART"})
    @Override
    public void remove(Book book) {
        book = entityManager.merge(book);
        entityManager.remove(book);
    }

    @Override
    public boolean updateBookQuantity(Long bookId, int dNumber) {
        int rowsAffected;
        StringBuilder ejbql = new StringBuilder();
        ejbql.append("UPDATE Book b SET b.numberOfCopy = (b.numberOfCopy + :dNumber) WHERE (b.id = :bookId)");
        if (dNumber < 0) {
            ejbql.append(" AND (b.numberOfCopy > 0)");
        }
        rowsAffected = entityManager.createQuery(ejbql.toString()).setParameter("bookId", bookId).setParameter("dNumber", dNumber).executeUpdate();
        return (rowsAffected > 0);
    }

    @Override
    public Book getBookById(int id) {
        return entityManager.find(Book.class, id);
    }

    @CheckPerfomance
    @Override
    public List<Book> getBookList() {
        String ejbql = "SELECT object(o) FROM Book AS o WHERE o.status IS NULL OR o.status = :status ORDER BY o.id DESC";
        Query query = entityManager.createQuery(ejbql);
        query.setParameter("status", BookStatus.NORMAL);
        return query.getResultList();
    }

    @CheckPerfomance
    @Override
    public List<Book> getDeletedBookList() {
        String ejbql = "SELECT object(o) FROM Book AS o WHERE o.status = :status ORDER BY o.id DESC";
        Query query = entityManager.createQuery(ejbql);
        query.setParameter("status", BookStatus.DELETED);
        return query.getResultList();
    }

    @CheckPerfomance
    @Override
    public Long getNumberOfBooks() {
        return (Long) entityManager.createQuery("SELECT count(o) FROM Book AS o").getSingleResult();
    }

    @CheckPerfomance
    @Override
    public List<BestSellingBook> getBestSellingBooks(int number) {
        Query query = entityManager.createNamedQuery("bestSellingBooks", Book.class);
        query.setMaxResults(number);
        List<Object[]> objectList = query.getResultList();
        List<BestSellingBook> result = new ArrayList<BestSellingBook>();
        if (objectList.size() > 0) {
            for (Object[] o : objectList) {
                BestSellingBook b = new BestSellingBook();
                try {
                    b.setBook((Book) o[0]);
                    b.setQuantity(((Long) o[1]).longValue());
                } catch (ClassCastException e) {
                    throw new RuntimeException("getBestSellingBoob cast exceplion: " + e.getMessage());
                }
                result.add(b);
            }
        }
        return result;
    }

    @CheckPerfomance
    @Override
    public List<MostProfitableBook> getMostProfitableBooks(int number) {
        Query query = entityManager.createNamedQuery("mostPofitableBooks", Book.class);
        query.setMaxResults(number);
        List<Object[]> objectList = query.getResultList();
        List<MostProfitableBook> result = new ArrayList<MostProfitableBook>();
        if (objectList.size() > 0) {
            for (Object[] o : objectList) {
                MostProfitableBook m = new MostProfitableBook();
                try {
                    m.setBook((Book) o[0]);
                    m.setProfit(((BigDecimal) o[1]).doubleValue());
                } catch (ClassCastException e) {
                    throw new RuntimeException("getMostProfitableBook cast exceplion: " + e.getMessage());
                }
                result.add(m);
            }
        }
        return result;
    }

    @CheckPerfomance
    @Override
    public List<Book> findByText(String text) {
        text = "%" + text + "%";
        String ejbql = "SELECT object(o) FROM Book AS o WHERE (o.status IS NULL OR o.status = :status) " +
                "AND (upper(o.title) LIKE upper(:searchText) " +
                "OR upper(o.author) LIKE upper(:searchText) " +
                "OR upper(o.description) LIKE upper(:searchText))";
        Query query = entityManager.createQuery(ejbql);
        query.setParameter("status", BookStatus.NORMAL);
        query.setParameter("searchText", text);
        return query.getResultList();
    }
}
