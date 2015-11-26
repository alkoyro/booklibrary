package by.es.realm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alexey.Koyro
 */
public class HashProviderTest
{
    @Test
    public void testHash()
    {
        String result1 = HashProvider.hashValue("password");
        String result2 = HashProvider.hashValue("password");

        Assert.assertEquals(result1, result2);
    }
}
