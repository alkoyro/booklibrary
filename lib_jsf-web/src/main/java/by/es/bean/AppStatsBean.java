package by.es.bean;

import by.es.ejb.dao.VisitDAO;
import by.es.ejb.entity.Visit;
import by.es.predefined.DateConstants;
import by.es.util.AppProps;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author Aleksey.Yaroshenko
 */
@Named
@ApplicationScoped
public class AppStatsBean implements Serializable {



    private static final Logger log = Logger.getLogger(AppStatsBean.class.getName());
    @EJB
    private VisitDAO visitDAO;

    public void checkVisit(String hostIP) {
        Visit visit = new Visit();
        visit.setHostIP(hostIP);
        visit.setLastLoginDate(new Date());
        visitDAO.save(visit);
        
    }

    public long getUniqueVisitsDay() {
       return getUniqueVisitCount(DateConstants.DAY_MILLS);
    }

    public long getUniqueVisitsWeek() {
        return getUniqueVisitCount(DateConstants.WEEK_MILLS);
    }

    public long getUniqueVisitsMonth() {
        return getUniqueVisitCount(DateConstants.MONTH_MILLS);
    }

    public String getProps() {
        return AppProps.get("xxx");
    }

    
    private long getUniqueVisitCount(long mills) {
        Date date = new Date();
        date.setTime(date.getTime() - mills);
        return visitDAO.getUniqueVisitorCount(date);
    }


}
