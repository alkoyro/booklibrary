package by;

import java.io.Serializable;

/**
 * User: Alexey.Koyro
 */
public class ReverseString implements Serializable
{
    public String reverse(String source, int fromIndex)
    {
        if(fromIndex != source.length())
        {
            String result = reverse(source, fromIndex+=1);
            return result+=source.charAt(fromIndex-1);
        }

        return "";
    }

    public static void main(String[] args)
    {
        ReverseString reverseString = new ReverseString();
        String result = reverseString.reverse("abc", 0);
        System.out.println(result);
    }



}
