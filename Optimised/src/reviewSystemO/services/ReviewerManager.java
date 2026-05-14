package reviewSystemO.services;

import java.util.List;

import reviewSystemO.data.Database;
import reviewSystemO.models.Reviewer;
import reviewSystemO.models.Submission;

/**
 * Tracable interactions:
 * SubmissionController -> ReviewerManager assignReviewers(submission)
 * ReviewerManager -> Database fetchEligibleReviewers()
 * Database -> ReviewerManager eligibleReviewers
 * [loop - assign reviewers] ReviewerManager calls Reviewer assignReview()
 * 
 * Optimisation:
 * getAvailableReviewers() removed.
 * filterConflicts() self-call removed. Done in database.
 * checkWorkload() self-call removed. Done in database.
 * Reviewer assignment loop encapsulated inside here.
 */
public class ReviewerManager {
    private Database database;
    public ReviewerManager(Database database) {
        this.database = database;
    }

    //SubmissionController calls ReviewerManager assignReviewers(submission)
    // Optimisation: Fetch, filter and assignment loop is all handled. 
    // No longer does the submissionController call getAvailableReviewers() then 
    // assignReviwers(filteredList, submission) separately, no longer can see the assignment loop.
    public void assignReviewers(Submission submission) {
        List<Reviewer> eligibleReviewers = database.fetchEligibleReviewers();
        //[loop - assign reviewers]
        for (Reviewer reviewer : eligibleReviewers) {
            reviewer.assignReview(submission);
        }
        // passing assigned reviewers to submission for EvaluationManager to access later.
        submission.setAssignedReviewers(eligibleReviewers);
    }
}
