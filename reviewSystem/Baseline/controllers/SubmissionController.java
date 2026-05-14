package reviewSystem.Baseline.controllers;

import java.util.List;

import reviewSystem.Baseline.data.Database;
import reviewSystem.Baseline.models.Reviewer;
import reviewSystem.Baseline.models.Submission;
import reviewSystem.Baseline.services.EvaluationManager;
import reviewSystem.Baseline.services.ReviewerManager;
import reviewSystem.Baseline.services.Validator;
import reviewSystem.Evaluation.Metrics;

/**
 * Class as shown in the diagram.
 * Central orchestrator of the sequence.
 * Tracable interactions:
 * UI calls SubmissionController submit(data)
 * SubmissionController calls Validator validateFormat(data)
 * Validator -> SubmissionController valid/invalid
 * [alt invalid] SubmissionController returns error
 * [alt valid] 
 * SubmissionController calls Database saveSubmission(data)
 * Database -> SubmissionController confirmation
 * SubmissionController calls ReviewerManager getAvaliableReviewers()
 * ReviewrManager -> filteredReviewers
 * [loop - assign reviewers] SubmissionController calls Reviewer assignReview()
 * SubmissionController calls EvaluationManager startEvaluation()
 */
public class SubmissionController {
    private Validator validator;
    private Database database;
    private ReviewerManager reviewerManager;
    private EvaluationManager evaluationManager;

    public SubmissionController(Validator validator, Database database, ReviewerManager reviewerManager, EvaluationManager evaluationManager) {
        this.validator = validator;
        this.database = database;
        this.reviewerManager = reviewerManager;
        this.evaluationManager = evaluationManager;
    }

    //UI calls SubmissionController submit(data)
    // Orchestrates the full sequence
    public void submit(Object data) {
        Metrics mc = Metrics.getInstance();
        mc.record("SubmissionController.submit");
        // SubmissionController calls Validator validateFormat(data)
        // Validator -> SubmissionController valid/invalid
        String validationResult = validator.validateFormat(data);
        // alt invalid -> return error
        if (validationResult.equals("invalid")) {
            return;
        }

        //valid -> continue
        // SubmissionController calls Database saveSubmission(data)
        // Database -> SubmissionController confirmation
        Submission submission = new Submission(data);
        String confirmation = database.saveSubmission(submission);
        System.out.println("database confirmation: " + confirmation);
        // SubmissionController calls ReviewerManager getAvailableReviewers()
        // ReviewerManager -> SubmissionController filteredReviewers
        List<Reviewer> filteredReviewers = reviewerManager.getAvaliableReviewers();

        // [loop - assign reviewers] SubmissionController calls Reviewr assignReview()
        reviewerManager.assignReviewers(filteredReviewers, submission);
        submission.setAssignedReviewers(filteredReviewers);

        // SubmissionController calls EvaluationManager startEvaluation()
        evaluationManager.startEvaluation(submission);
    }
}
