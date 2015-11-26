package by;

import java.util.LinkedList;
import java.util.Queue;

/**
 * User: Alexey.Koyro
 */
public class DefendedQueue<E> implements QueueDelegator<E>{
    private final Queue<E> elements = new LinkedList<E>();


    @Override
    public boolean push(E element) 
    {
        synchronized (elements)
        {
            boolean result = elements.add(element);
            for (int i = 0;i<elements.size(); i++)
            {
                elements.notify();
            }
            return result;
        }
    }

    @Override
    public E pop() 
    {
        synchronized (elements)
        {
            while (elements.isEmpty())
            {
                try
                {
                    elements.wait();
                }
                catch (InterruptedException e) { }
            }
        }
        return elements.remove();
    }
}
