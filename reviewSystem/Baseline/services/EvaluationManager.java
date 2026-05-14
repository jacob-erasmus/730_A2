package reviewSystem.Baseline.services;

import java.util.ArrayList;
import java.util.List;

import reviewSystem.Baseline.data.Database;
import reviewSystem.Baseline.models.Reviewer;
import reviewSystem.Baseline.models.Submission;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * SubmissionController calls EvaluationManager startEvaluation()
 * [loop each reviewer] 
 * Reviewer -> EvaluationManager submitScore()
 * EvaluationManager -> Database saveScore(score)
 * EvaluationManager self calls calculateAverage()
 * EvaluationManager self calls checkConsensus()
 * EvaluationManager self calls applyRules()
 * [alt accepted] EvaluationManager calls NoficationService notifyAcceptance()
 * [alt rejected] EvaluationManager calls NotificationService notifyRejection()
 * [alt revision] EvaluationManager calls NotificationService notifyRevision()
 */
public class EvaluationManager {
    // thresholds not specified, so placeholders.
    private static final double ACCEPT_THRESHOLD = 7.0;
    private static final double REJECT_THRESHOLD = 3.0;
    // Diagram shows EvaluationManager -> NotificationService calls
    private NotificationService notificationService;
    private Database database;

    public EvaluationManager(NotificationService notificationService, Database database) {
        this.notificationService = notificationService;
        this.database = database;
    }

    /**
     * SubmissionController calls EvaluationManager startEvaluation()
     * The full evaluation sequence:
     * [loop - each reviewer] calls submitScore(score)
     * calculateAverage()
     * checkConsensus()
     * applyRules()
     * [alt] notifyAcceptance() / notifyRejection() / notifyRevision()
     */
    public void startEvaluation(Submission submission) {
        List<Double> scores = new ArrayList<>();
        //loop for each reviewer
        // Reviewer -> EvaluationManager submitScore()
        for (Reviewer reviewer : submission.getAssignedReviewers()) {
            double score = reviewer.submitScore();
            // EvaluationManager -> Database saveScore(score)
            database.saveScore(score);
        }
        // EvaluationManager self calls calculateAverage()
        double averageScore = calculateAverage(scores);
        // EvaluationManager self calls checkConsensus()
        boolean consensus = checkConsensus(scores);
        // EvaluationManager self calls applyRules()
        String outcome = applyRules(averageScore, consensus);

        // alt accepted
        // EvaluationManager calls NoficationService notifyAcceptance()
        if (outcome.equals("accepted")) {
            notificationService.notifyAcceptance(submission);
        // alt rejected
        // EvaluationManager calls NotificationService notifyRejection()
        } else if (outcome.equals("rejected")) { 
            notificationService.notifyRejection(submission);
        // alt revision
        // EvaluationManager calls NotificationService notifyRevision()
        } else { 
            notificationService.notifyRevision(submission);
        }
    }

    // EvaluationManager self calls calculateAverage(scores)
    // Recieves scors collected in loop - each reviewer
    // Returns the average to be used in applyRules().
    public double calculateAverage(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double score : scores) {
            sum += score;
        }
        return sum / scores.size();
    }
    // EvaluationManager self calls checkConsensus(scores)
    // Recieves scores collected in loop - each reviewer
    // Returns boolean consensus to be used in applyRules().
    // Logic not specified in diagram, so placeholder that always returns true.
    public boolean checkConsensus(List<Double> scores) {
        return true;
    }
    // EvaluationManager self calls applyRules(averageScore, consensus)
    // Outcome of alt block of accepted, rejected, revision.
    public String applyRules(double averageScore, boolean consensus) {
        if (consensus && averageScore >= ACCEPT_THRESHOLD) { 
            return "accepted";
        } else if (consensus && averageScore < REJECT_THRESHOLD) {  
            return "rejected";
        } else {
            return "revision";
        }
    }
    
}
