package by.es.interceptor;

import by.es.interceptor.bean.PerformanceStatBean;
import by.es.performance.Performance;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.faces.context.FacesContext;

/**
 * User: Alexey.Koyro
 */
@Aspect
public class PerformanceInspector
{
    private static final Logger LOGGER = Logger.getLogger(PerformanceInspector.class);

    @Pointcut("execution(@by.es.performance.CheckPerfomance * by.es..*.*(..))")
    private void callToPerformance() { }

//    @Around("callToPerformance()")
//    public Object doIntercept(ProceedingJoinPoint joinPoint) throws Throwable {
//        Long startTime = System.currentTimeMillis();
//        Object objectValue = joinPoint.proceed();
//        Long ellapsedTime = System.currentTimeMillis() - startTime;

//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        PerformanceStatBean performanceStatBean = facesContext.getApplication().
//                evaluateExpressionGet(facesContext, "#{performanceStatBean}", PerformanceStatBean.class);
//        performanceStatBean.addPerformance(new Performance(joinPoint.getSignature().
//                getDeclaringType().getName(), joinPoint.getSignature().getName()), ellapsedTime);
//
//        LOGGER.info("Method: " + joinPoint.getSignature().getName() +
//                "of class: " + joinPoint.getSignature().getDeclaringType() +
//                "execution time: " + ellapsedTime + " milliseconds");
//        return objectValue;
//    }
}
