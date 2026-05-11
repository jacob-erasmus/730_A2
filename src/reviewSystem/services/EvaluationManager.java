package reviewSystem.services;

import java.util.ArrayList;
import java.util.List;

import reviewSystem.models.EvaluationResult;
import reviewSystem.models.Reviewer;
import reviewSystem.models.Submission;

public class EvaluationManager {
    // thresholds not specified, so placeholders.
    private static final double ACCEPT_THRESHOLD = 7.0;
    private static final double REJECT_THRESHOLD = 3.0;
    // Diagram shows EvaluationManager -> NotificationService calls
    private NotificationService notificationService;

    public EvaluationManager(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * SubmissionController calls EvaluationManager startEvaluation()
     * Loop each reviewer, calls submitScore(score)
     * then calculateAverage(), checkConsensus(), applyRules()
     * returns EvaluationResult
     */
    public EvaluationResult startEvaluation(Submission submission) {
        List<Double> scores = new ArrayList<>();
        //loop for each reviewer
        for (Reviewer reviewer : submission.getAssignedReviewers()) {
            double score = reviewer.submitScore();
            scores.add(score);
        }
        double averageScore = calculateAverage(scores);
        boolean consensus = checkConsensus(scores);
        String outcome = applyRules(averageScore, consensus);

        // alt accepted
        if (outcome.equals("accepted")) {
            notificationService.notifyAcceptance(submission);
        // alt rejected
        } else if (outcome.equals("rejected")) { 
            notificationService.notifyRejection(submission);
        // alt revision
        } else { 
            notificationService.notifyRevision(submission);
        }
        return new EvaluationResult(averageScore, consensus, outcome, scores);
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
