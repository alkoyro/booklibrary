/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.ejb.dao.BookDAO;
import by.es.ejb.entity.Book;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


/**
 * @author Aleksey.Yaroshenko
 */
@Named
@RequestScoped
public class SearchResultBean implements Serializable
{
    @EJB
    private BookDAO bookDAO;

    public List<Book> findBooksBy(String searchKey)
    {
        return bookDAO.findByText(searchKey);
    }

}
