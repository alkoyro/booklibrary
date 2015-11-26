package by.es.interceptor.bean;


import by.es.performance.DurationPerformance;
import by.es.performance.Performance;
import by.es.performance.QuantityPerformance;
import by.es.performance.predefined.PerformanceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Alexey.Koyro
 */

/**
 * managed bean with application scope. Defined in faces-config
 */
public class PerformanceStatBean {
    private Map<Performance, Long> quantityPerformanceMap = new HashMap<Performance, Long>();
    private List<DurationPerformance> durationPerformances = new ArrayList<DurationPerformance>();

    public void addPerformance(Performance performance, Long duration) {
        addDurationPerformance(performance, duration);
        addQuantityPerformance(performance);
    }

    private void addDurationPerformance(Performance performance, Long duration) {
        DurationPerformance durationPerformance = new DurationPerformance();
        durationPerformance.setClassName(performance.getClassName());
        durationPerformance.setMethodName(performance.getMethodName());
        durationPerformance.setDuration(duration);
        durationPerformances.add(durationPerformance);
    }

    public PerformanceType[] getPerformanceTypes() {
        return PerformanceType.values();
    }

    private void addQuantityPerformance(Performance performance) {
        if (quantityPerformanceMap.containsKey(performance)) {
            Long quantity = quantityPerformanceMap.remove(performance);
            quantity = quantity + 1;
            quantityPerformanceMap.put(performance, quantity);
        } else {
            quantityPerformanceMap.put(performance, 1l);
        }
    }

    public List<QuantityPerformance> getQuantityPerformances() {
        List<QuantityPerformance> quantityPerformances = new ArrayList<QuantityPerformance>();
        for (Performance performance : quantityPerformanceMap.keySet()) {
            QuantityPerformance quantityPerformance = new QuantityPerformance();
            quantityPerformance.setClassName(performance.getClassName());
            quantityPerformance.setMethodName(performance.getMethodName());
            quantityPerformance.setQuantity(quantityPerformanceMap.get(performance));
            quantityPerformances.add(quantityPerformance);
        }
        return quantityPerformances;
    }

    public List<DurationPerformance> getDurationPerformances() {
        return durationPerformances;
    }
}
