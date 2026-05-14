package reviewSystem.Optimised.services;

import java.util.ArrayList;
import java.util.List;

import reviewSystem.Optimised.data.Database;
import reviewSystem.Optimised.models.Reviewer;
import reviewSystem.Optimised.models.Submission;

/**
 * Tracable interactions:
 * SubmissionController calls EvaluationManager evaluate(submission)
 * [loop each reviewer] 
 * EvaluationManager -> Reviewer submitScore()
 * EvaluationManager -> EvaluationManager computeOutcome(scores)
 * EvaluationManager -> Database saveScores(scores)
 * EvalationManager -> SubmissionController outcome
 * 
 * Optimisation:
 * NotifcationService dependency removed, as not part of the evaluation sequence.
 * startEvaluation() replaced with evaluate() which returns outcome.
 * calculateAverage(), checkConsensus() and applyRules() replaced with computeOutcome().
 * saveScore() that was called in loop, replaced by saveScores() after the loop completion.
 * Circular dependency removed.
 */
public class EvaluationManager {
    // thresholds
    private static final double ACCEPT_THRESHOLD = 7.0;
    private static final double REJECT_THRESHOLD = 3.0;

    private Database database;

    public EvaluationManager(Database database) {
        this.database = database;
    }

    /**
     * SubmissionController calls EvaluationManager evaluate(submission)
     * returns: outcome of accepted, rejected, revision.
     * Optimisation: changed from startEvaluation() that was tighly coupled with NotificationService. Now SubmissionController recieves result and decides.
     */
    public String evaluate(Submission submission) {
        List<Double> scores = new ArrayList<>();
        //loop for each reviewer
        // EvaluationManager -> Reviewer submitScore()
        for (Reviewer reviewer : submission.getAssignedReviewers()) {
            double score = reviewer.submitScore();
            scores.add(score);
        }
        String outcome = computeOutcome(scores);
        database.saveScore(scores);
        return outcome;
    }

    // EvaluationManager self calls computeOutcome(scores)
    // Replaces calculateAverage(), checkConsensus() and applyRules() with a single method to compute the outcome.
    // returns accepted, rejected or revision.
    public String computeOutcome(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return "rejected"; 
        }
        // compute average
        double total = 0.0;
        for (double score : scores) {
            total += score;
        }
        double average = total/scores.size();
        // check consensus
        boolean consensus = true; // Placeholder for consensus logic
        // apply rules
        if (average >= ACCEPT_THRESHOLD && consensus) {
            return "accepted";
        } else if (average < REJECT_THRESHOLD) {
            return "rejected";
        } else {
            return "revision";
        }
    }
    
}
