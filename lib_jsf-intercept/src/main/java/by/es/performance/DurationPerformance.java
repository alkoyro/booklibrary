package by.es.performance;

/**
 * User: Alexey.Koyro
 */
public class DurationPerformance extends Performance{
    private Long duration;

    public DurationPerformance() {
    }

    public DurationPerformance(String className, String methodName) {
        super(className, methodName);
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
