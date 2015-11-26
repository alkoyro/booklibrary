package by.es.performance;

/**
 * User: Alexey.Koyro
 */
public class QuantityPerformance extends Performance{
    private Long quantity;
    
    public QuantityPerformance() {
    }

    public QuantityPerformance(String className, String methodName) {
        super(className, methodName);
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
