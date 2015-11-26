package by;

/**
 * User: Alexey.Koyro
 */
public interface QueueDelegator<E> 
{
    boolean push(E element);
    E pop();
}
