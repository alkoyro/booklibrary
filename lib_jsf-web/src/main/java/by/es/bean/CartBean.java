/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.ejb.dao.PurchaseDAO;
import by.es.ejb.entity.*;
import by.es.ejb.entity.predefined.BookVersion;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.dao.UserDAO;
import by.es.util.CartElement;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@SessionScoped
public class CartBean implements Serializable, Cloneable {

    private static final Logger log = Logger.getLogger(CartBean.class.getName());
    @EJB
    private UserDAO userDAO;
    @EJB
    private BookDAO bookDAO;
    @EJB
    private PurchaseDAO purchaseDAO;
    @Inject
    private BookListBean bookListBean;
    @Inject
    private UserBean userBean;



    private List<CartElement> cart = new ArrayList<CartElement>();


    public int findBookAmount(Book book) {
        int amount = 0;
        for (CartElement ce : cart) {
            if (ce.getBook().equals(book)) {
                amount = ce.getAmount();
                break;
            }
        }
        return amount;
    }

    public double getFullPrice() {
        double fullPrice = 0;
        for (CartElement ce : cart) {
            if (ce.getBook().getPrice() != null) {
                fullPrice += ce.getAmount() * ce.getBook().getPrice().getCost();
            }
        }
        fullPrice = fullPrice * (1 - userBean.getUser().getDiscount() * 0.01);
        return fullPrice;
    }


    public void makeOrder() throws InvalidDataException {
        User user = userBean.getUser();
        if (user != null) {
            ListIterator<CartElement> li = cart.listIterator();
            Purchase purchase = new Purchase();
            ArrayList<PurchaseElement> purchaseElementList = new ArrayList<PurchaseElement>();
            purchase.setPurchaseElementList(purchaseElementList);
            purchase.setPurchaseDate(new Date());
            purchase.setUser(user);
            while (li.hasNext()) {
                CartElement cartElement = li.next();
                int amount = cartElement.getAmount();
                if (amount > cartElement.getBook().getNumberOfCopy()) {
                    amount = cartElement.getBook().getNumberOfCopy();
                }
                if (amount > 0) {
                    boolean exists = bookDAO.updateBookQuantity(cartElement.getBook().getId(), -amount);
                    if (exists) {
                        PurchaseElement purchaseElement = new PurchaseElement();
                        purchaseElement.setBook(cartElement.getBook());
                        purchaseElement.setBookVersion(BookVersion.PRINTED);
                        purchaseElement.setAmount(amount);
                        purchaseElement.setCost(purchaseElement.getBook().getPrice().getCost());
                        purchaseElement.setCost(purchaseElement.getCost() * (1 - (user.getDiscount() * 0.01)));
                        purchase.getPurchaseElementList().add(purchaseElement);
                        if (cartElement.getAmount() == amount) {
                            li.remove();
                        } else {
                            cartElement.setAmount(cartElement.getAmount() - amount);
                        }
                    } else {
                        throw new InvalidDataException("bookNotExist.error", NextStepRule.PREVIOUS_PAGE);
                    }
                }
            }
            purchaseDAO.save(purchase);
            bookListBean.setDirty(true);
        }
    }


    public void addToCart(Book book) throws InvalidDataException {
        if (cart == null) {
            cart = new ArrayList<CartElement>();
        }
        CartElement cartElement = null;
        if (containsBook(book)) {
            cartElement = getCartElement(book);
            if (cartElement.getAmount() < book.getNumberOfCopy()) {
                cartElement.setAmount(cartElement.getAmount() + 1);
            }
        } else {
            if (book != null) {
                if (book.getNumberOfCopy() > 0) {
                    cartElement = new CartElement();
                    cartElement.setBook(book);
                    if (book.getNumberOfCopy() > 0) {
                        cartElement.setAmount(1);
                    } else {
                        cartElement.setAmount(0);
                    }
                    cart.add(cartElement);
                }
            } else {
                throw new InvalidDataException("noXXXBook.text", NextStepRule.PREVIOUS_PAGE);
            }
        }
    }

    public void addToCartById(int bookId) throws InvalidDataException {
        Book book = bookListBean.findById(bookId);
        addToCart(book);
    }

    public void removeFromCartById(int bookId) {

        CartElement cartElement = getCartElementById(bookId);
        if (cartElement != null) {
            cart.remove(cartElement);
        }
    }

    public void removeFromCart(Book book) {

        CartElement cartElement = getCartElement(book);
        if (cartElement != null) {
            cart.remove(cartElement);
        }
    }

    public List<CartElement> getCart() {
        return cart;
    }

    public void setCart(List<CartElement> cart) {
        this.cart = cart;
    }

    public boolean containsBook(Book book) {
        boolean contains = false;
        for (CartElement ce : cart) {
            if (ce.getBook().equals(book)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    public boolean containsBookById(int bookId) {
        boolean contains = false;
        for (CartElement ce : cart) {
            if (ce.getBook().getId() == bookId) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    public CartElement getCartElement(Book book) {
        CartElement cartElement = null;
        for (CartElement ce : cart) {
            if (ce.getBook().equals(book)) {
                cartElement = ce;
                break;
            }
        }
        return cartElement;
    }

    public CartElement getCartElementById(int bookId) {
        CartElement cartElement = null;
        for (CartElement ce : cart) {
            if (ce.getBook().getId() == bookId) {
                cartElement = ce;
                break;
            }
        }
        return cartElement;
    }
}
