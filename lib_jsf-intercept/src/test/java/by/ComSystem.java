package by;

/**
 * User: Alexey.Koyro
 */
public enum ComSystem
{
    WINDOWS(256),
    LINUX(128);

    private int req;

    private ComSystem(int req)
    {
        this.req = req;
    }
}
