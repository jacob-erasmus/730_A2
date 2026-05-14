package reviewSystem.Baseline.models;

import java.util.concurrent.ThreadLocalRandom;

import reviewSystem.Baseline.models.Submission;
import reviewSystem.Evaluation.Metrics;
/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * [loop - assign reviewers] SubmissionController calls Reviewer assignReview()
 * [loop - each reviewr] Reviwer calls EvaluationManager submitScore(score)
 * Also self calls: 
 * reviewerManager calls ReviewerManager filterConflicts(reviewerList)
 * reviewerManager calls ReviewerManager checkWorkload(reviewerList)
 */
public class Reviewer {
    private boolean hasConflict;
    private int currentWorkload;
    private Submission assignedSubmission;

    public Reviewer(boolean hasConflict, int currentWorkload) {
        this.hasConflict = hasConflict;
        this.currentWorkload = currentWorkload;
        this.assignedSubmission = null;
    }
    // SubmissionController calls Reviewer assignReview()
    public void assignReview(Submission submission) {
        this.assignedSubmission = submission;
        this.currentWorkload++;
    }

    // Reviewer calls EvaluationManager submitScore(score)
    public double submitScore() {
        Metrics.getInstance().record("Reviewer.submitScore");
        // Returns a random score 0–10 so applyRules/computeOutcome exercises all branches
        return ThreadLocalRandom.current().nextDouble(0.0, 10.0);
    }
    // Used by ReviewerManager filterConflicts()
    public boolean hasConflict() {
        return hasConflict;
    }
    // Used by ReviewerManager checkWorkload()
    public int getCurrentWorkload() {
        return currentWorkload;
    }
    
    public Submission getAssignedSubmission() {
        return assignedSubmission;
    }


}
