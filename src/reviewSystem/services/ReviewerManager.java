package reviewSystem.services;

import java.util.ArrayList;
import java.util.List;

import reviewSystem.data.Database;
import reviewSystem.models.Reviewer;
import reviewSystem.models.Submission;

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
        for (Reviewer r : filteredReviewers) {
            r.assignReview(submission);
        }
    }
}
