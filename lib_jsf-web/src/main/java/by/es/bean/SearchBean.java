/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.navigation.LibraryNavigationHandler;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.NavigationCase;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Aleksey.Yaroshenko
 */
@ManagedBean
@RequestScoped
public class SearchBean implements Serializable
{
    public static final String RESULT_KEY_PARAM = "searchKey";
    public static final String OUTCOME_NAVIGATION_RULE = "search";

    private String searchkey;

    public String getSearchkey()
    {
        return searchkey;
    }

    public void setSearchkey(String searchkey)
    {
        this.searchkey = searchkey;
    }

    public void search()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        LibraryNavigationHandler navigationHandler = (LibraryNavigationHandler) context.
                getApplication().getNavigationHandler();

        Map<String, String> params = new HashMap<String, String>();
        params.put(RESULT_KEY_PARAM, searchkey != null ? searchkey : "");
        navigationHandler.redirectTo(OUTCOME_NAVIGATION_RULE, context, params);
    }
}
