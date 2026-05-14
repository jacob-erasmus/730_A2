package reviewSystem.Optimised.controllers;

import reviewSystem.Optimised.data.Database;
import reviewSystem.Optimised.models.Submission;
import reviewSystem.Optimised.services.EvaluationManager;
import reviewSystem.Optimised.services.NotificationService;
import reviewSystem.Optimised.services.ReviewerManager;
import reviewSystem.Optimised.services.Validator;

/**
 * Central orchestrator of the sequence.
 * Tracable interactions:
 * UI calls SubmissionController submit(data)
 * SubmissionController calls Validator validate(data)
 * Validator -> SubmissionController boolean: isValid
 * [alt invalid] SubmissionController calls UI returnError()
 * [alt valid] 
 * SubmissionController calls Database saveSubmission(data)
 * Database -> SubmissionController confirmation
 * SubmissionController calls ReviewerManager assignReviewers(submission)
 * SubmissionController calls EvaluationManager evaluate(submission)
 * EvaluationManager -> SubmissionController outcome
 * SubmissionController calls NotificationService notify(outcome)
 * 
 * Optimisation:
 * validate() returns boolean not string.
 * assignReviewers(submission) encapsulates the fetching, filtering and assignment loop.
 * evaluate(submission) returns outcome, not startEvaluation() that was tighly coupled with NotificationService. Now SubmissionController recieves result and decides.
 * NotificationService now a dependency of SubmissionController, not EvaluationManager, as it is not part of the evaluation sequence. SubmissionController calls NotificationService notify(outcome) at the end of the sequence.
 */
public class SubmissionController {
    private Validator validator;
    private Database database;
    private ReviewerManager reviewerManager;
    private EvaluationManager evaluationManager;
    // optimisation addition.
    private NotificationService notificationService;

    public SubmissionController(Validator validator, Database database, ReviewerManager reviewerManager, EvaluationManager evaluationManager, NotificationService notificationService) {
        this.validator = validator;
        this.database = database;
        this.reviewerManager = reviewerManager;
        this.evaluationManager = evaluationManager;
        this.notificationService = notificationService;
    }

    //UI calls SubmissionController submit(data)
    public void submit(Object data) {
        // SubmissionController calls Validator validate(data)
        // returns: boolean isValid
        boolean isValid = validator.validate(data);
        // alt invalid -> return error
        if (!isValid) {
            System.out.println("Error returned: Invalid submission format.");
            return;
        }

        // alt valid -> continue
        // SubmissionController calls Database saveSubmission(data)
        // returns: confirmation
        Submission submission = new Submission(data);
        String confirmation = database.saveSubmission(submission);
        System.out.println("Database confirmation: " + confirmation);

        // SubmissionController calls ReviewerManager assignReviewers()
        // optimisation: one encapsulated call.
        reviewerManager.assignReviewers(submission);

        // SubmissionController calls EvaluationManager evaluate(submission)
        // returns: outcome
        // optimisation: startEvluation() replaced with evaluate() which returns outcome rather than notifications being dispatched in EvaluationManager.
        String outcome =  evaluationManager.evaluate(submission);

        // SubmissionController calls NotificationService notify(outcome)
        // optimisation: no longer three separate calls for each condition from EvaluationManager.
        notificationService.notify(outcome);
    }
}
