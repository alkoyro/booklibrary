package by.es.realm;

import org.apache.log4j.Logger;
import sun.nio.cs.UTF_32;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Alexey.Koyro
 * <p/>
 * util class to hash any value with MD5 algorithm
 */
public final class HashProvider
{
    private static final String HASH_ALGORITHM = "MD5";
    private static final Logger LOGGER = Logger.getLogger(HashProvider.class);

    public static String hashValue(String valueToHash)
    {
        String result = null;
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.reset();
            messageDigest.update(valueToHash.getBytes());

            byte[] hashValue = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : hashValue)
            {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }
            result = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            LOGGER.error("No such hashing algorithm", e);
        }

        return result;
    }
}
