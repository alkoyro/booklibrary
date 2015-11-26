package by.es.performance;


/**
 * User: Alexey.Koyro
 */
public class Performance {
    private String className;
    private String methodName;

    public Performance() {
    }

    public Performance(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
