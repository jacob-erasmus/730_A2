package reviewSystem.Evaluation;
import reviewSystem.Evaluation.Metrics;
import java.util.*;

public class BenchmarkResults {
    private final String systemName;
    private final long[] timesNano;
    private final Map<String, Integer> counts;
    private final int totalCalls;

    public BenchmarkResults(String systemName, long[] timesNano, Map<String, Integer> counts, int totalCalls) {
        this.systemName = systemName;
        this.timesNano = Arrays.copyOf(timesNano, timesNano.length);
        this.counts = Collections.unmodifiableMap(new LinkedHashMap<>(counts));
        this.totalCalls = totalCalls;
    }

    public String getSystemName() {
        return systemName;
    }

    public int getTotalCalls() {
        return totalCalls;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public int getRunCount() {
        return timesNano.length;
    }

    public double getMeanMicros() {
        long sum = 0;
        for (long time : timesNano) {
            sum += time;
        }
        return (sum / (double) timesNano.length) / 1_000.0;
    }

    public double getMedianMicros() {
        long[] sorted = Arrays.copyOf(timesNano, timesNano.length);
        Arrays.sort(sorted);
        int mid = sorted.length / 2;
        long med = sorted.length % 2 == 0
            ? (sorted[mid - 1] + sorted[mid]) / 2 : sorted[mid];
        return med/1_000.0;
    }
    
    public double getMinMicros() {
        long min = Long.MAX_VALUE;
        for (long time : timesNano) {
            if (time < min) {
                min = time;
            }
        }
        return min / 1_000.0;
    }

    public double getMaxMicros() {
        long max = Long.MIN_VALUE;
        for (long time : timesNano) {
            if (time > max) {
                max = time;
            }
        }
        return max / 1_000.0;
    }
}