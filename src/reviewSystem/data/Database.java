package reviewSystem.data;

import java.util.ArrayList;
import java.util.List;
import reviewSystem.models.Reviewer;
import reviewSystem.models.Submission;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * SubmissionController -> Database saveSubmission(data)
 * Database -> SubmissionController confirmation
 * ReviewerManager -> Database fetchReviewers()
 * Database -> ReviewerManager reviewerList
 * EvaluationManager -> Database saveScore(score)
 */
public class Database {
    public Database() {
    }

    // SubmissionController calls Database saveSubmission(data)
    // returns confirmation
    // After [alt valid]
    public String saveSubmission(Submission data) {
        return "confirmation";
    }

    // ReviewerManager calls Database fetchReviewers()
    // returns reviewrList to reviewer manager for filtering and workload checks.
    public List<Reviewer> fetchReviewers() {
        //logic not specified so returns empty list.
        return new ArrayList<>();
    }

    // EvaluationManager calls Database saveScore(score)
    public void saveScore(double score) {
        // logic not specified
        System.out.println("Score saved: " + score);
    }
}
