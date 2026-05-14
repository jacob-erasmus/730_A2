package reviewSystem.Evaluation;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Call counter and timing function.
 */
public class Metrics {
    private static final Metrics INSTANCE = new Metrics();
    private final LinkedHashMap<String, Integer> counts = new LinkedHashMap<>();
    private long runStartNanoTime = 0;

    private Metrics() {}

    public static Metrics getInstance() {
        return INSTANCE;
    }
    
    public void startRun() {
        counts.clear();
        runStartNanoTime = System.nanoTime();
    }

    public long stopRun() {
        return System.nanoTime() - runStartNanoTime;
    }

    public void record(String method){
        counts.merge(method, 1, Integer::sum);
    }

    public int getCount(String method) {
        return counts.getOrDefault(method, 0);
    }

    public int getTotalCount() {
        return counts.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Map<String, Integer> getSnapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(counts));
    }
}