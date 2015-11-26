package by.es.filter;

import by.es.bean.AppStatsBean;

import javax.inject.Inject;
import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Aleksey.Yaroshenko
 * Date: 13.02.12
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class VisitFilter implements Filter {

    @Inject
    private AppStatsBean appStatsBean;

    private static final Logger log = Logger.getLogger(VisitFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String hostIP = servletRequest.getRemoteHost();
        appStatsBean.checkVisit(hostIP);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
