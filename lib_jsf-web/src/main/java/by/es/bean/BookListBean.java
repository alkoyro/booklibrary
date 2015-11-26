package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.entity.Book;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@ApplicationScoped
public class BookListBean implements Serializable {

    private static final Logger log = Logger.getLogger(BookListBean.class.getName());
    @EJB
    private BookDAO bookDAO;
    private List<Book> bookList;
    private boolean dirty;
    
    public List<Book> getBookList() {
        checkListStatus();
        return bookList;
    }

    public Book findById(int bookId) throws InvalidDataException {
        Book book = null;
        for (Book b : getBookList()) {
            if (b.getId() == bookId) {
                book = b;
                break;
            }
        }
        if (book == null) {
            throw new InvalidDataException("noScuhBook.error", NextStepRule.PREVIOUS_PAGE);
        }
        return book;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void checkListStatus() {
        if (bookList == null || isDirty()) {
            setDirty(false);
            bookList = bookDAO.getBookList();
        }
    }
}
