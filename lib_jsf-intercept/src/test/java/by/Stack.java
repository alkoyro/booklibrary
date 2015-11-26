package by;

/**
 * User: Alexey.Koyro
 */
public class Stack <E>
{
    private E[] elements;
    private int cursor = 0;

    @SuppressWarnings("unchecked")
    public Stack() 
    {
        elements = (E[]) new Object[4];
    }

    public int size()
    {
        return cursor;
    }
    
    public void push(E e)
    {
        elements[cursor] = e;
        cursor+=1;
    }
    
    public E pop()
    {
        cursor-=1;
        E e = elements[cursor];
        elements[cursor]=null;
        return e;       
    }
    
    public void reverseStack()
    {
        moveStackElements();
    }

    private void moveStackElements()
    {
        if (cursor != 0)
        {
            E elem = pop();
            moveStackElements();
            elements[elements.length-cursor-1] = elem;
            cursor++;
        }
    }
    
    public Stack<E> reverse()
    {
        Stack<E> reversedStack = new Stack<E>();
        reverseElementsTo(reversedStack,0);
        return reversedStack;
    }

    private void reverseElementsTo(Stack<E> reversedStack, int position) 
    {
        if (position != elements.length-1)
        {
            reverseElementsTo(reversedStack, ++position);
            reversedStack.push(elements[--position]);
        }
        else
        {
            reversedStack.push(elements[position]);
        }
    }
}
