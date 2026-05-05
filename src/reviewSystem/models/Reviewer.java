package reviewSystem.models;

import java.util.List;

public class Reviewer {
    private String id;
    private String name;
    private boolean hasConflict;
    private int currentWorkload;

    // SubmissionController calls Reviewer assignReview()
    public void assignReview(Submission submission) {
        this.assignedSubmission = submission;
    }

    // Reviewer calls EvaluationManager submitScore(score)
    public double submitScore() {
        double score;
        return score;
}
