package reviewSystem.models;

import java.util.List;

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
        double score = 0.0; // Placeholder, diagram doesn't specify scoring logic, only submission of score.
        return score;
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
