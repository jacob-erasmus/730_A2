package reviewSystem.Evaluation;
import reviewSystem.Evaluation.Metrics;
import java.sql.SQLException;
import java.util.*;

import reviewSystem.Evaluation.DatabaseManager;

public class BenchmarkManager{
    public static final int WARMUP_RUNS = 10;
    public static final int BENCHMARK_RUNS = 100;

    // Baseline
    public static BenchmarkResults runBaseline() throws SQLException {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            DatabaseManager.resetScores(); 
            buildRunBaseline();
        }
        long[] times = new long[BENCHMARK_RUNS];
        Map<String, Integer> lastCounts = new LinkedHashMap<>();
        int lastTotal = 0;
        Metrics metric = Metrics.getInstance();
        for (int i = 0; i < BENCHMARK_RUNS; i++) {
            DatabaseManager.resetScores(); 
            metric.startRun();
            buildRunBaseline();
            times[i] = metric.stopRun();
            if (i == BENCHMARK_RUNS - 1) {
                // Capture counts from the last run
                lastCounts = new LinkedHashMap<>(metric.getSnapshot());
                lastTotal = metric.getTotalCount();
            }
        }
        return new BenchmarkResults("Baseline", times, lastCounts, lastTotal);
    }

    // Optimised
    public static BenchmarkResults runOptimised() throws SQLException {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            DatabaseManager.resetScores(); 
            buildRunOptimised();
        }
        long[] times = new long[BENCHMARK_RUNS];
        Map<String, Integer> lastCounts = new LinkedHashMap<>();
        int lastTotal = 0;
        Metrics metric = Metrics.getInstance();
        for (int i = 0; i < BENCHMARK_RUNS; i++) {
            DatabaseManager.resetScores(); 
            metric.startRun();
            buildRunOptimised();
            times[i] = metric.stopRun();
            if (i == BENCHMARK_RUNS - 1) {
                // Capture counts from the last run
                lastCounts = new LinkedHashMap<>(metric.getSnapshot());
                lastTotal = metric.getTotalCount();
            }
        }
        return new BenchmarkResults("Optimised", times, lastCounts, lastTotal);
    }

    // baseline sequencing
    private static void buildRunBaseline(){
        reviewSystem.Baseline.data.Database database = new reviewSystem.Baseline.data.Database();
        reviewSystem.Baseline.services.Validator validator = new reviewSystem.Baseline.services.Validator();
        reviewSystem.Baseline.services.ReviewerManager reviewerManager = new reviewSystem.Baseline.services.ReviewerManager(database);
        reviewSystem.Baseline.actors.Researcher researcher = new reviewSystem.Baseline.actors.Researcher(null);
        reviewSystem.Baseline.services.NotificationService notificationService = new reviewSystem.Baseline.services.NotificationService(researcher);
        reviewSystem.Baseline.services.EvaluationManager evaluationManager = new reviewSystem.Baseline.services.EvaluationManager(notificationService, database);
        reviewSystem.Baseline.controllers.SubmissionController submissionController = new reviewSystem.Baseline.controllers.SubmissionController(validator, database, reviewerManager, evaluationManager);
        reviewSystem.Baseline.ui.UI ui = new reviewSystem.Baseline.ui.UI(submissionController);
        researcher.setUI(ui);
        researcher.submitResearchOutput(new Object());
    }

    // optimised sequencing
    private static void buildRunOptimised(){
        reviewSystem.Optimised.data.Database database = new reviewSystem.Optimised.data.Database();
        reviewSystem.Optimised.services.Validator validator = new reviewSystem.Optimised.services.Validator();
        reviewSystem.Optimised.services.ReviewerManager reviewerManager = new reviewSystem.Optimised.services.ReviewerManager(database);
        reviewSystem.Optimised.services.NotificationService notificationService = new reviewSystem.Optimised.services.NotificationService();
        reviewSystem.Optimised.services.EvaluationManager evaluationManager = new reviewSystem.Optimised.services.EvaluationManager(database);
        reviewSystem.Optimised.controllers.SubmissionController submissionController = new reviewSystem.Optimised.controllers.SubmissionController(validator, database, reviewerManager, evaluationManager, notificationService);
        reviewSystem.Optimised.ui.UI ui = new reviewSystem.Optimised.ui.UI(submissionController);
        reviewSystem.Optimised.actors.Researcher researcher = new reviewSystem.Optimised.actors.Researcher(ui);
        researcher.submitResearchOutput(new Object());
    }
}