package by.es.auth.exception;

import by.es.auth.exception.predefined.NextStepRule;

/**
 * Created by Alexey.Koyro
 *
 * used to inform user input is illegal
 */

public class InvalidDataException extends RuntimeException
{
    private String resourceKey;
    private NextStepRule nextStepRule;

    public InvalidDataException(String resourceKey, NextStepRule nextStepRule)
    {
        this.resourceKey = resourceKey;
        this.nextStepRule = nextStepRule;
    }

    public String getResourceKey()
    {
        return resourceKey;
    }

    public NextStepRule getNextStepRule()
    {
        return nextStepRule;
    }
}
