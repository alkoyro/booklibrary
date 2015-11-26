package by;

/**
 * User: Alexey.Koyro
 */
public class TestStack
{
    public static void main(String[] args)
    {
        Stack<String> stack = new Stack<String>();
        stack.push("one");
        stack.push("two");
        stack.push("three");
        stack.push("four");
        Stack<String> reversed = stack.reverse();
//        while (reversed.size() > 0)
        {
//            System.out.println(reversed.pop());
        }

        stack.reverseStack();
        while (stack.size() > 0)
        {
            System.out.println(stack.pop());
        }
    }
}
