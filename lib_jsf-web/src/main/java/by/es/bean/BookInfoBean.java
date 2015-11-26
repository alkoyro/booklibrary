/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.dao.CommentDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Comment;
import by.es.ejb.entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class BookInfoBean implements Serializable {

    private static final Logger log = Logger.getLogger(BookInfoBean.class.getName());

    @Inject
    private BookListBean bookListBean;

    private Book book;


    public void findBookById(int bookId) throws InvalidDataException
    {
        if (bookId > 0) {
            book = bookListBean.findById(bookId);
        }
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
