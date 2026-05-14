package reviewSystem.Baseline.services;

import java.util.ArrayList;
import java.util.List;

import reviewSystem.Baseline.data.Database;
import reviewSystem.Baseline.models.Reviewer;
import reviewSystem.Baseline.models.Submission;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * SubmissionController -> ReviewerManager getAvaliableReviewers()
 * ReviewerManager -> Database fetchReviewers()
 * Database -> ReviewerManager reviewerList
 * ReviewerManager self calls filterConflicts(reviewerList)
 * ReviewerManager self calls checkWorkload(reviewerList)
 * ReviewerManager -> SubmissionController filteredReviewers
 * [loop - assign reviewers] SubmissionController calls Reviewer assignReview()
 */
public class ReviewerManager {
    private Database database;
    public ReviewerManager(Database database) {
        this.database = database;
    }

    //SubmissionController calls ReviewerManager getAvaliableReviewers()
    // Calls Database fetchReviewers()
    // Then: filterConflicts(reviewerList), checkWorkload(reviewrList)
    // returns filteredReviewers
    public List<Reviewer> getAvaliableReviewers() {
        List<Reviewer> reviewerList = database.fetchReviewers();
        reviewerList = filterConflicts(reviewerList);
        reviewerList = checkWorkload(reviewerList);
        return reviewerList;
    }

    //ReviewerManager self calls filterConflicts(reviewerList)
    public List<Reviewer> filterConflicts(List<Reviewer> reviewerList) {
        List<Reviewer> filteredList = new ArrayList<>();
        for (Reviewer r : reviewerList) {
            if (!r.hasConflict()) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

    //ReviewerManager self calls checkWorkload(reviewerList)
    public List<Reviewer> checkWorkload(List<Reviewer> reviewerList) {
        List<Reviewer> available = new ArrayList<>();
        for (Reviewer r : reviewerList) {
            if (r.getCurrentWorkload() < 5) { // Assuming max workload is 5
                available.add(r);
            }
        }
        return available;
    }

    //SubmissionController calls Reviewr assignReview(), ReviewerManager handles the loop.
    public void assignReviewers(List<Reviewer> filteredReviewers, Submission submission) {
        //[loop - assign reviewers]
        for (Reviewer r : filteredReviewers) {
            r.assignReview(submission);
        }
    }
}
