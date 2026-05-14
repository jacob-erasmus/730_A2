package reviewSystemO.models;

import reviewSystem.models.Submission;

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
        this.assignedSubmission = submission;
        this.currentWorkload++;
    }

    // [loop - each reviewer] EvaluationManager calls Reviewer submitScore()
    public double submitScore() {
        double score = 0.0; // Placeholder
        return score;
    }

    public boolean hasConflict() {
        return hasConflict;
    }

    public int getCurrentWorkload() {
        return currentWorkload;
    }
    
    public Submission getAssignedSubmission() {
        return assignedSubmission;
    }


}