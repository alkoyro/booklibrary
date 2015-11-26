package by.es.ejb.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "BOOK_COMMENT")
public class Comment extends PersistenceEntity {

    @Column(name = "TEXT", length = 1023)
    private String text;

    @Column(name = "COMMENT_DATE", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date commentDate;

    @ManyToOne(targetEntity = Book.class)
    private Book book;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }





}
