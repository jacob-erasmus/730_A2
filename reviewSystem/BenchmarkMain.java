package reviewSystem;

import java.util.Map;

import reviewSystem.*;
import reviewSystem.Evaluation.BenchmarkManager;
import reviewSystem.Evaluation.BenchmarkResults;
import reviewSystem.Evaluation.DatabaseManager;
import reviewSystem.Evaluation.Metrics;
import reviewSystem.Evaluation.Metrics;

/**
 * Runs the the evaluation system, collects the metrics and prints results.
 */
public class BenchmarkMain {
    public static void main(String[] args) throws Exception{
        // Intitalise shared database
        DatabaseManager.getConnection();

        System.out.println("---");
        System.out.println("Evaluation Report");
        System.out.println("---");

        //run benchmarks
        System.out.println("\nBaseline: " + BenchmarkManager.WARMUP_RUNS + "warm up + " + BenchmarkManager.BENCHMARK_RUNS + " timed.");
        BenchmarkResults baseline = BenchmarkManager.runBaseline();

        System.out.println("\nOptimised: " + BenchmarkManager.WARMUP_RUNS + "warm up + " + BenchmarkManager.BENCHMARK_RUNS + " timed.");
        BenchmarkResults optimised = BenchmarkManager.runOptimised();

        //interaction counts
        System.out.println("---");
        System.out.println("INTERACTION COUNTS (last run)");
        System.out.println("---");
        System.out.printf("%-40s %8s %10s%n","Method","Baseline", "Optimised");
        System.out.println("---");

        java.util.Set<String> allKeys = new java.util.LinkedHashSet<>();
        allKeys.addAll(baseline.getCounts().keySet());
        allKeys.addAll(optimised.getCounts().keySet());
        for (String key : allKeys) {
            int b = baseline.getCounts().getOrDefault(key, 0);
            int o = optimised.getCounts().getOrDefault(key, 0);
            System.out.printf("%-40s %8s %10s%n", "key", "b", "o");
        }
        System.out.println("---");
        System.out.printf("%-40s %8s %10s%n", "TOTAL CALLS", baseline.getTotalCalls(),  optimised.getTotalCalls());
        double callReduction = 100.0 * (baseline.getTotalCalls() - optimised.getTotalCalls()) / baseline.getTotalCalls();
        System.out.printf("Reduction: %.1f%%%n", callReduction);

        System.out.println("---");
        System.out.println(" [2]  EXECUTION TIME  (n=" + BenchmarkManager.BENCHMARK_RUNS + " runs, microseconds)");
        System.out.println(separator('-', 66));
        System.out.printf("%-14s %14s %14s %10s%n", "Metric", "Baseline", "Optimised", "Change");
        System.out.println(separator('-', 66));
        printTimingRow("Mean",   baseline.getMeanMicros(),   optimised.getMeanMicros());
        printTimingRow("Median", baseline.getMedianMicros(), optimised.getMedianMicros());
        printTimingRow("Min",    baseline.getMinMicros(),    optimised.getMinMicros());
        printTimingRow("Max",    baseline.getMaxMicros(),    optimised.getMaxMicros());
        System.out.println("\n" + separator('-', 66));
    }
    private static void printTimingRow(String label, double base, double opt) {
        double change = 100.0 * (base - opt) / base;
        System.out.printf("%-14s %14.2f %14.2f %9.1f%%%n",
            label, base, opt, change);
    }

    private static String separator(char ch, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(ch);
        return sb.toString();
    }
}
    
