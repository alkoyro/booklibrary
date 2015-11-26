/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.dao.UserDAO;
import by.es.ejb.entity.*;
import by.es.ejb.entity.predefined.BookVersion;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@SessionScoped
public class EbookBean implements Serializable {

    private static final Logger log = Logger.getLogger(EbookBean.class.getName());
    @EJB
    private UserDAO userDAO;
    @EJB
    private BookDAO bookDAO;
    @EJB
    private PurchaseDAO purchaseDAO;
    @Inject
    private UserBean userBean;
    @Inject
    private BookListBean bookListBean;

    private Book book;

    public boolean isEbookBuyed() {
        return purchaseDAO.isExists(userBean.getUser(), book, BookVersion.EBOOK);
    }

    public void setBookObject(int bookId) throws InvalidDataException {
        if (bookId > 0) {
            book = bookListBean.findById(bookId);
            if (book == null) {
                throw new InvalidDataException("bookNotExist.error", NextStepRule.PREVIOUS_PAGE);
            }
        }
    }

    public void downloadEbook() {
        if (isEbookBuyed()) {

            FacesContext ctx = FacesContext.getCurrentInstance();
            ExternalContext ectx = ctx.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
            HttpServletResponse response = (HttpServletResponse) ectx.getResponse();

            request.setAttribute("userId", userBean.getUser().getId());
            request.setAttribute("bookId", book.getId());
            String url = "/files/" + book.geteBookFileName();
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                log.severe(e.getMessage());
            } catch (IOException e) {
                log.severe(e.getMessage());
            }
            ctx.responseComplete();

        }
    }

    public void buyEbook() throws InvalidDataException {
        User user = userBean.getUser();
        if ((user != null) && (book != null) && (book.geteBookFileName() != null) && (!book.geteBookFileName().isEmpty())) {
            if (!isEbookBuyed()) {
                Purchase purchase = new Purchase();
                ArrayList<PurchaseElement> purchaseElementList = new ArrayList<PurchaseElement>();
                purchase.setPurchaseElementList(purchaseElementList);
                purchase.setPurchaseDate(new Date());
                purchase.setUser(user);
                PurchaseElement purchaseElement = new PurchaseElement();
                purchaseElement.setBook(book);
                purchaseElement.setBookVersion(BookVersion.EBOOK);
                purchaseElement.setCost(purchaseElement.getBook().getPrice().getCostEbook());
                purchaseElement.setCost(purchaseElement.getCost() * (1 - (user.getDiscount() * 0.01)));
                purchase.getPurchaseElementList().add(purchaseElement);
                purchaseDAO.save(purchase);
            }
        } else {
            throw new InvalidDataException("bookNotExist.error", NextStepRule.PREVIOUS_PAGE);
        }
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
