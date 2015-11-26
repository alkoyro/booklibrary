package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.predefined.BookStatus;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@ManagedBean
@ViewScoped
public class DeletedBookListBean implements Serializable {

    private static final Logger log = Logger.getLogger(DeletedBookListBean.class.getName());
    @EJB
    private BookDAO bookDAO;
    @Inject
    private BookListBean bookListBean;
    private List<Book> deletedBookList;
    private boolean dirty;
    
    public List<Book> getDeletedBookList() {
        checkListStatus();
        return deletedBookList;
    }

    public void restoreBook(Book book) {
        if (book.getStatus().equals(BookStatus.DELETED)) {
            book.setStatus(BookStatus.NORMAL);
            bookDAO.update(book);
            setDirty(true);
            bookListBean.setDirty(true);
        }
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void checkListStatus() {
        if (deletedBookList == null || isDirty()) {
            setDirty(false);
            deletedBookList = bookDAO.getDeletedBookList();
        }
    }
}
