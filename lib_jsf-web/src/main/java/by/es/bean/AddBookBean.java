/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Price;
import by.es.tags.ResourceMessageProvider;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@RequestScoped
public class AddBookBean {

    public static final String ADD_BOOK_BUNDLE_NAME = "addBookBundle";
    public static final String ERROR_KEY_NAME = "error.icon";

    private static final Logger log = Logger.getLogger(AddBookBean.class.getName());

    @Inject
    private BookListBean bookListBean;
    @Inject
    private FileUploadBean fileUploadBean;
    @Inject
    EditBookBean editBookBean;
    @EJB
    private BookDAO bookDAO;
    private Book book;
    private UIComponent errorsPanel;
    private UploadedFile uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public AddBookBean() {
        book = new Book();
        Price price = new Price();
        book.setPrice(price);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
    }

    public void addBook() {
        List<Book> bookList = bookListBean.getBookList();
        for (Book b : bookList) {
            if ((b.getAuthor().equalsIgnoreCase(book.getAuthor()))
                    || (b.getTitle().equalsIgnoreCase(book.getTitle()))) {
                throw new InvalidDataException("bookExist.error", NextStepRule.PREVIOUS_PAGE);
            }
        }
        book = bookDAO.save(book); /* Saving book to database and obtaining book ID */
        log.info("Book saved, ID = " + book.getId());
        bookListBean.setDirty(true);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String url = "editbook.faces?bookId=" + book.getId();
        try {
            context.redirect(url);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    /**
     * validates components if on emptiness
     *
     * @param context
     * @param toValidate
     * @param value
     */
    public void validateIsEmpty(FacesContext context, UIComponent toValidate, Object value) {
        if (value.equals("")) {
            String errorMessage = ResourceMessageProvider.getMessage(toValidate.getId() + ".text", ADD_BOOK_BUNDLE_NAME);
            ((UIInput) toValidate).setValid(false);
            addWarningMessage(errorMessage);
        }
    }

    private void addWarningMessage(String message) {
        UIOutput errorMessage = new UIOutput();
        errorMessage.setValue(message);
        UIGraphic errorIcon = new UIGraphic();
        errorIcon.setRendererType("javax.faces.Image");
        errorIcon.setValue(ResourceMessageProvider.getIcon(ERROR_KEY_NAME, ADD_BOOK_BUNDLE_NAME));
        errorsPanel.getChildren().add(errorIcon);
        errorsPanel.getChildren().add(errorMessage);
    }

    public void clearWarnings() {
        errorsPanel.getChildren().clear();
    }

    public UIComponent getErrorsPanel() {
        return errorsPanel;
    }

    public void setErrorsPanel(UIComponent errorsPanel) {
        this.errorsPanel = errorsPanel;
    }
}
