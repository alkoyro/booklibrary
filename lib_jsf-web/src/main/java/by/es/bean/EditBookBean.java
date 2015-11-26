package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.ejb.entity.predefined.BookStatus;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Price;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class EditBookBean implements Serializable {

    private static final Logger log = Logger.getLogger(EditBookBean.class.getName());

    private static final String[] PDF_MIME_TYPES = {"application/pdf"};
    private static final String[] IMAGE_MIME_TYPES = {"image/jpeg", "image/png", "image/gif"};

    @Inject
    private BookListBean bookListBean;
    @Inject
    private FileUploadBean fileUploadBean;
    @EJB
    private BookDAO bookDAO;

    private UploadedFile uploadedImage;

    private UploadedFile uploadedEBook;

    private Book book;

    public void uploadImageFile() {
        if (isMIME(IMAGE_MIME_TYPES, uploadedImage)) {
            fileUploadBean.deleteImage(book.getId());
            fileUploadBean.uploadImage(uploadedImage, book.getId());
        } else {
            throw new InvalidDataException("imageUpload.error", NextStepRule.PREVIOUS_PAGE);
        }
        uploadedImage = null;
    }

    public void uploadEBookFile() {
        if (isMIME(PDF_MIME_TYPES, uploadedEBook)) {
            if ((uploadedEBook != null) && (uploadedEBook.getSize() > 0)) {
                if ((book.geteBookFileName() != null) && (!book.geteBookFileName().isEmpty())) {
                    deleteEBookFile();
                }
            }
            fileUploadBean.uploadEBook(uploadedEBook);
            book.seteBookFileName(uploadedEBook.getName());
            book = bookDAO.update(book);
        } else {
            throw new InvalidDataException("PDFUpload.error", NextStepRule.PREVIOUS_PAGE);
        }
        uploadedEBook = null;
    }

    public void updateBook() {
        book = bookDAO.update(book);
        bookListBean.setDirty(true);
    }

    public void deleteImageFile() {
        fileUploadBean.deleteImage(book.getId());
    }


    public void deleteEBookFile() {
        fileUploadBean.deleteEBook(book.geteBookFileName());
        book.seteBookFileName(null);
        book = bookDAO.update(book);
    }

    public void deleteBook() {
        deleteEBookFile();
        deleteImageFile();
        book.setStatus(BookStatus.DELETED);
        bookDAO.update(book);
        bookListBean.setDirty(true);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        if (book != null) {
            if (book.getPrice() == null) {
                book.setPrice(new Price());
            }
        }
        this.book = book;
    }

    public UploadedFile getUploadedImage() {
        return uploadedImage;
    }

    public void setUploadedImage(UploadedFile uploadedImage) {
        this.uploadedImage = uploadedImage;
    }

    public UploadedFile getUploadedEBook() {
        return uploadedEBook;
    }

    public void setUploadedEBook(UploadedFile uploadedEBook) {
        this.uploadedEBook = uploadedEBook;
    }

    /* private methods */

    private boolean isMIME(String[] types, UploadedFile file) {
        boolean result = false;
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        for (String type : types) {
            if (mimeType.equals(type)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
