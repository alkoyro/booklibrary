/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.es.bean;

import by.es.auth.exception.InvalidDataException;
import by.es.ejb.dao.BookDAO;
import by.es.ejb.entity.Book;
import by.es.ejb.entity.Price;
import by.es.tags.ResourceMessageProvider;
import by.es.util.AppURLCreator;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@SessionScoped
public class BackURLBean implements Serializable {
    private String backURL;
    private boolean samePage;

    private final String appURL = AppURLCreator.getAppURL();
    
    private static final Logger log = Logger.getLogger(BackURLBean.class.getName());



    public String getBackURL() {
        return backURL;
    }

    public void setBackURL(String backURL) {
        if ((this.backURL == null) || (!this.backURL.equals(backURL)) && (backURL.startsWith(appURL)) && (!samePage) ) {
            this.backURL = backURL;
        }
        samePage = false;
    }


    public boolean isSamePage() {
        return samePage;
    }

    public void setSamePage(boolean samePage) {
        this.samePage = samePage;
    }
}
