package by.es.ejb.dao;

import by.es.ejb.entity.Book;
import by.es.ejb.entity.Comment;
import by.es.ejb.entity.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CommentDAO {

    List<Comment> findByBook(Book book);

    List<Comment> findByUser(User user);
    
    Comment save(Comment comment);

    void removeComment(Comment comment);

}
