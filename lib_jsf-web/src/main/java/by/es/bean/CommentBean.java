/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.ejb.dao.CommentDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Comment;
import by.es.ejb.entity.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class CommentBean implements Serializable {

    private static final Logger log = Logger.getLogger(CommentBean.class.getName());

    @Inject
    private BookListBean bookListBean;
    @Inject
    private UserBean userBean;
    @EJB
    private CommentDAO commentDAO;

    private Comment editComment;

    private Book book;

    private List<Comment> commentList;

    private String commentText;

    public void findBookComents(Book book) {
        this.book = book;
        commentList = commentDAO.findByBook(book);
        commentText = null;
    }

    public void findUserComents(User user) {
        commentList = commentDAO.findByUser(user);
    }

    public void addComment() {
        User user = userBean.getUser();
        if ((user != null) &&
                (book != null) &&
                (commentText != null) &&
                (!commentText.trim().isEmpty())) {
            Comment comment = new Comment();
            comment.setBook(book);
            comment.setUser(user);
            comment.setCommentDate(new Date());
            comment.setText(commentText);
            comment = commentDAO.save(comment);
        }

    }

    public void deleteComment(Comment comment) {
        commentDAO.removeComment(comment);
    }

    public void updateComment() {
        if (!editComment.getText().trim().isEmpty()) {
            commentDAO.save(editComment);
        }
        editComment = null;
    }

    public void cancelEditComment() {
        editComment = null;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Comment getEditComment() {
        return editComment;
    }

    public void setEditComment(Comment editComment) {
        this.editComment = editComment;
    }
}
