package by.es.performance.report;

import by.es.interceptor.bean.PerformanceStatBean;
import by.es.performance.DurationPerformance;
import by.es.performance.QuantityPerformance;
import by.es.performance.predefined.PerformanceType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.servlet.ServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * User: Alexey.Koyro
 */
public final class ReportProvider {
    public static final String QUANTITY_PERFORMANCE_REPORT_LOCATION = "by/es/performance/report/quantityPerformance.jasper";
    public static final String DURATION_PERFORMANCE_REPORT_LOCATION = "by/es/performance/report/durationPerformance.jasper";
    public static final String REPORT_LOCALE_PROPERTY = "REPORT_LOCALE";
    public static final String REPORT_RESOURCE_BUNDLE_PROPERTY = "REPORT_RESOURCE_BUNDLE";
    public static final String QUANTITY_REPORT_RESOURCE_BUNDLE_PATH = "by.es.performance.report.quantityPerformance";
    public static final String DURATION_REPORT_RESOURCE_BUNDLE_PATH = "by.es.performance.report.durationPerformance";

    private ReportProvider() {
    }

    private static final ReportProvider REPORT_PROVIDER = new ReportProvider();

    public static ReportProvider getInstance() {
        return REPORT_PROVIDER;
    }

    private JasperPrint loadQuantityPerformanceReport(List<QuantityPerformance> quantityPerformances, Locale locale) throws JRException {
        JasperReport jasperReport = (JasperReport) JRLoader.
                loadObject(ReportProvider.class.getClassLoader().getResource(QUANTITY_PERFORMANCE_REPORT_LOCATION));

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(REPORT_LOCALE_PROPERTY, locale);
        parameters.put(REPORT_RESOURCE_BUNDLE_PROPERTY, ResourceBundle.getBundle(QUANTITY_REPORT_RESOURCE_BUNDLE_PATH, locale));

        return JasperFillManager.fillReport(jasperReport, parameters,
                new JRBeanCollectionDataSource(quantityPerformances));
    }
    
    private JasperPrint loadDurationPerformanceReport(List<DurationPerformance> durationPerformances, Locale locale) throws JRException {
        JasperReport jasperReport = (JasperReport) JRLoader.
                loadObject(ReportProvider.class.getClassLoader().getResource(DURATION_PERFORMANCE_REPORT_LOCATION));

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(REPORT_LOCALE_PROPERTY, locale);
        parameters.put(REPORT_RESOURCE_BUNDLE_PROPERTY, ResourceBundle.getBundle(DURATION_REPORT_RESOURCE_BUNDLE_PATH, locale));

        return JasperFillManager.fillReport(jasperReport, parameters,
                new JRBeanCollectionDataSource(durationPerformances));
    }

    /**
     * loads report into outputStream
     *
     * @param performanceType
     * @param target
     * @throws JRException
     */
    public void loadReport(PerformanceType performanceType, ByteArrayOutputStream target, ServletRequest servletRequest) throws JRException {
        PerformanceStatBean performanceStatBean = (PerformanceStatBean) servletRequest.getServletContext().
                getAttribute("performanceStatBean");
        JasperPrint reportPrint = null;
        switch (performanceType) {
            case QUANTITY:
                reportPrint = loadQuantityPerformanceReport(performanceStatBean.getQuantityPerformances(),
                        servletRequest.getLocale());
                break;
            case DURATION:
                reportPrint = loadDurationPerformanceReport(performanceStatBean.getDurationPerformances(),
                        servletRequest.getLocale());
                break;
            default:
                throw new IllegalArgumentException();
        }

        JasperExportManager.exportReportToPdfStream(reportPrint, target);
    }
}
