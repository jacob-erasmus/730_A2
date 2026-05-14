package reviewSystem.Optimised.models;
import java.util.concurrent.ThreadLocalRandom;
import reviewSystem.Evaluation.Metrics;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * [loop - assign reviewers] ReviewerManager calls Reviewer assignReview()
 * [loop - each reviewr] EvaluationManager calls Reviewer submitScore()
 * Fields hasConflict and currentWorkload are used by Database.fetchEligibleReviewers() for filtering,
 * replacing filterConflicts() and checkWorkload() from baseline.
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
    // [loop - assign reviewers] ReviewerManager calls Reviewer assignReview()
    public void assignReview(Submission submission) {
        Metrics mc = Metrics.getInstance();
        mc.record("Reviewer.assignReview");
        this.assignedSubmission = submission;
        this.currentWorkload++;
    }

    // [loop - each reviewer] EvaluationManager calls Reviewer submitScore()
    public double submitScore() {
        Metrics mc = Metrics.getInstance();
        mc.record("Reviewer.submitScore");
        Metrics.getInstance().record("Reviewer.submitScore");
        // Returns a random score 0–10 so applyRules/computeOutcome exercises all branches
        return ThreadLocalRandom.current().nextDouble(0.0, 10.0);
    }

    public boolean hasConflict() {
        Metrics mc = Metrics.getInstance();
        mc.record("Reviewer.hasConflict");
        return hasConflict;
    }

    public int getCurrentWorkload() {
        Metrics mc = Metrics.getInstance();
        mc.record("Reviewer.getCurrentWorkload");
        return currentWorkload;
    }
    
    public Submission getAssignedSubmission() {
        Metrics mc = Metrics.getInstance();
        mc.record("Reviewer.getAssignedSubmission");
        return assignedSubmission;
    }


}