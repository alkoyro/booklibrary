/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.ejb.dao.impl;

import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.User;
import by.es.performance.CheckPerfomance;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@DeclareRoles({"VISIT_USER_AREA","EDIT_USER","EDIT_MANAGER"})
@Stateless
public class UserDaoImpl implements UserDAO {

    private static final Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    @PersistenceContext(unitName = "libraryPersistence")
    private EntityManager entityManager;

    @CheckPerfomance
    @RolesAllowed({"VISIT_USER_AREA", "EDIT_USER","EDIT_MANAGER"})
    @Override
    public User loadUserBy(Long id)
    {
        return entityManager.find(User.class, id);
    }

    @CheckPerfomance
    @Override
    public List<User> getUserList() {
        return entityManager.createQuery("SELECT object(o) FROM User AS o ORDER BY o.login DESC").getResultList();
    }

    @Override
    public boolean isUserExists(String login) {
        Query query = entityManager.createNamedQuery("findUserCountByLogin", User.class);
        query.setParameter("login", login);
        Long count = (Long) query.getSingleResult();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @CheckPerfomance
    @Override
    public User findUserBy(String login, String password) {
        Query query = entityManager.createNamedQuery("findUserByLoginAndPassword", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            log.info("Login unsuccessful, user login: " + login);
        }
        return user;
    }

    @Override
    public User findUserByLogin(String login) {
        Query query = entityManager.createNamedQuery("findUserByLogin", User.class);
        query.setParameter("login", login);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            log.info("User with login '" + login + "' it not exists");
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        Query query = entityManager.createNamedQuery("findUserByEmail", User.class);
        query.setParameter("email", email);
        User user = null;
        List<User> list = null;
        list =  query.getResultList();
        if (list != null && list.size() > 0) {
            user = list.get(0);
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User updateUser(User user) {
        return entityManager.merge(user);
    }
}
