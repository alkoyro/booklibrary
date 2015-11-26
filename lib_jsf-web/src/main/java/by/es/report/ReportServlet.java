package by.es.report;

import by.es.auth.exception.InvalidDataException;
import by.es.auth.exception.predefined.NextStepRule;
import by.es.performance.predefined.PerformanceType;
import by.es.performance.report.ReportProvider;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * User: Alexey.Koyro
 */
public class ReportServlet extends GenericServlet {
    private static final Logger logger = Logger.getLogger(ReportServlet.class.getName());
    
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        PerformanceType performanceType = PerformanceType.valueOf(servletRequest.
                getParameter("performance"));

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        servletResponse.setContentType("application/pdf");

        ServletOutputStream servletOutputStream = servletResponse.getOutputStream();
        try {
            ReportProvider.getInstance().loadReport(performanceType, target, servletRequest);
        } catch (JRException e) {
            logger.severe(e.getMessage());
            throw new InvalidDataException("noSuchReportFound.error", NextStepRule.RETURN_TO_MAIN);
        }

        servletResponse.setContentLength(target.size());
        target.writeTo(servletOutputStream);

        servletOutputStream.flush();
        servletOutputStream.close();
        target.close();
    }
}
