package by.es.auth.exception.handler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Created by Alexey.Koyro
 */
public class ExceptionHandlerFactoryImpl extends ExceptionHandlerFactory
{
    private DefaultExceptionHandler defaultExceptionHandler;

    @Override
    public ExceptionHandler getExceptionHandler()
    {
        if(defaultExceptionHandler == null)
        {
            defaultExceptionHandler = new DefaultExceptionHandler();
        }
        return defaultExceptionHandler;
    }
}
