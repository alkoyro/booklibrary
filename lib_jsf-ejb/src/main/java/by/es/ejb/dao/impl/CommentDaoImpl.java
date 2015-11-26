/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.ejb.dao.impl;

import by.es.ejb.dao.CommentDAO;
import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Comment;
import by.es.ejb.entity.User;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Aleksey.Yaroshenko
 */
@Stateless
public class CommentDaoImpl implements CommentDAO{

    @PersistenceContext(unitName = "libraryPersistence")
    private EntityManager entityManager;


    @Override
    public List<Comment> findByBook(Book book) {
        
        String ejbql = "SELECT object(o) FROM Comment AS o WHERE o.book.id = :bookId";
        Query query = entityManager.createQuery(ejbql);
        query.setParameter("bookId", book.getId());
        return query.getResultList();
    }

    @Override
    public List<Comment> findByUser(User user) {
        String ejbql = "SELECT object(o) FROM Comment AS o WHERE o.user.id = :userId";
        Query query = entityManager.createQuery(ejbql);
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public void removeComment(Comment comment) {
        comment = entityManager.merge(comment);
        entityManager.remove(comment);
    }
}
