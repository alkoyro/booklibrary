package by.es.auth.exception.handler;

import by.es.auth.exception.ExceptionDetailBean;
import by.es.auth.exception.InvalidDataException;
import by.es.navigation.LibraryNavigationHandler;
import org.apache.myfaces.shared_tomahawk.context.ExceptionHandlerImpl;

import javax.ejb.EJBAccessException;
import javax.faces.FacesException;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Alexey.Koyro
 */
public class DefaultExceptionHandler extends ExceptionHandlerImpl
{
    public static String PARAM_EXCEPTION = "result";
    public static String OUTCOME_NAVIGATION_RULE = "exception";

    /**
     * using faces-config for navigation-rule when exception is thrown to determine to view url
     * and append parameters
     */
    @Override
    public void handle() throws FacesException
    {
        Iterator<ExceptionQueuedEvent> unhandled = getUnhandledExceptionQueuedEvents().iterator();

        FacesException toThrow = null;

        while (unhandled.hasNext())
        {
            ExceptionQueuedEvent event = unhandled.next();
            try
            {
                ExceptionQueuedEventContext eventContext = event.getContext();

                Throwable exception = eventContext.getException();

                if (!shouldSkip(exception))
                {
                    toThrow = wrap(getRethrownException(exception));
                    break;
                }
                else
                {
                    FacesContext context = FacesContext.getCurrentInstance();

                    ExceptionParameterProvider exceptionParameterProvider = new ExceptionParameterProvider();

                    context.getExternalContext().getSessionMap().put(ExceptionDetailBean.VISITED_PAGE_KEY,
                            ((HttpServletRequest) context.getExternalContext().getRequest()).
                                    getHeader("referer"));

                    Throwable cause = exception.getCause();

                    LibraryNavigationHandler navigationHandler = (LibraryNavigationHandler) context.
                            getApplication().getNavigationHandler();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put(PARAM_EXCEPTION, exceptionParameterProvider.getParameter(cause));

                    if (cause instanceof InvalidDataException)
                    {
                        switch (((InvalidDataException) cause).getNextStepRule())
                        {
                            case PREVIOUS_PAGE:
                                break;
                            case RETURN_TO_MAIN:
                                context.getExternalContext().getSessionMap().
                                        remove(ExceptionDetailBean.VISITED_PAGE_KEY);
                                break;
                            default:
                                break;
                        }
                    }

                    navigationHandler.redirectTo(OUTCOME_NAVIGATION_RULE, context, params);
                }
            }
            catch (Throwable t)
            {
                throw new FacesException("Could not perform the algorithm to handle the Exception", t);
            }
            finally
            {
                unhandled.remove();
            }
        }

        if (toThrow != null)
        {
            throw toThrow;
        }
    }

    @Override
    protected boolean shouldSkip(Throwable exception)
    {
        return exception instanceof AbortProcessingException;
    }

    private static class ExceptionParameterProvider
    {
        public static final String DEFAULT_EXCEPTION_PARAMETER = "defaultMessage.error";
        private static Map<Class, String> resourceBuffer = new HashMap<Class, String>();

        static
        {
            resourceBuffer.put(EJBAccessException.class, "accessDenied.error");
        }

        public String getParameter(Throwable exception)
        {
            if (resourceBuffer.containsKey(exception.getClass()))
            {
                return resourceBuffer.get(exception.getClass());
            }
            else if (exception instanceof InvalidDataException)
            {
                return ((InvalidDataException) exception).getResourceKey();
            }
            else
            {
                return DEFAULT_EXCEPTION_PARAMETER;
            }
        }
    }
}