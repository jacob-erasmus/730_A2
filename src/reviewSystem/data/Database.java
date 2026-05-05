package reviewSystem.data;

import java.util.ArrayList;
import java.util.List;
import reviewSystem.models.Reviewer;
import reviewSystem.models.Submission;

public class Database {
    // SubmissionController calls Database saveSubmission(data)
    // returns confirmation
    public String saveSubmission(Submission data) {
        return "confirmation";
    }

    // ReviewerManager calls Database fetchReviewers()
    // returns reviewrList
    public List<Reviewer> fetchReviewers() {
        return new ArrayList<>();
    }

    // Evaluationmanager calls Database saveScore(score)
    public void saveScore(double score) {
        // reviewer's score.
    }
}
