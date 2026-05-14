package reviewSystem.data;

import java.util.ArrayList;
import java.util.List;
import reviewSystem.models.Reviewer;
import reviewSystem.models.Submission;

/**
 * Tracable interactions:
 * SubmissionController -> Database saveSubmission(data)
 * Database -> SubmissionController confirmation
 * ReviewerManager -> Database fetchEligableReviewers()
 * Database -> ReviewerManager eligableReviewers
 * EvaluationManager -> Database saveScore(score)
 * 
 * Optimisation:
 * fetchReviewers() now is fetchEligableReviewers() with the filtering logic moved,
 * eliminating filterConflicts() and checkWorkload() self calls from ReviewerManager, and reducing the number of interactions and data passed between classes.
 */
public class Database {
    public static final int MAX_WORKLOAD = 5; // Example threshold for maximum workload
    public Database() {
    }

    // SubmissionController calls Database saveSubmission(data)
    // returns confirmation
    public String saveSubmission(Submission data) {
        return "confirmation";
    }

    // ReviewerManager calls Database fetchEligableReviewers()
    // returns eligibleReviewers.
    // Optimisation is that the filtering logic is moved here, so only eligable reviewers are returned, eliminating the need for separate filterConflicts() and checkWorkload() calls in ReviewerManager.
    public List<Reviewer> fetchEligibleReviewers() {
        List<Reviewer> allReviewers = new ArrayList<>(); 
        List<Reviewer> eligible = new ArrayList<>();
        for (Reviewer reviewer : allReviewers) {
            if (!reviewer.hasConflict() && reviewer.getCurrentWorkload() < MAX_WORKLOAD) {
                eligible.add(reviewer);
            }
        }
        return eligible;
    }

    // EvaluationManager calls Database saveScore(score)
    // Optimisation: no repetivate loop call, single batch call after loop completion.
    public void saveScore(List<Double> scores) {
        for(double score : scores){
            System.out.println("Database batch score: " + score);
        }
    }
}
