package by.es.ejb.dao;

import by.es.ejb.entity.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserDAO
{

    User loadUserBy(Long id);

    List<User> getUserList();

    boolean isUserExists(String login);

    /**
     * finds user by login and password hash
     * @param login
     * @param password hash value
     * @return
     */
    User findUserBy(String login, String password);

    User findUserByLogin(String login);

    User findByEmail(String email);

    void addUser(User user);

    User updateUser(User user);
}
