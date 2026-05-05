package reviewSystem.controllers;

import java.util.List;

import javax.management.Notification;
import javax.xml.validation.Validator;

import reviewSystem.data.Database;
import reviewSystem.models.EvaluationResult;
import reviewSystem.models.Reviewer;
import reviewSystem.models.Submission;
import reviewSystem.services.EvaluationManager;
import reviewSystem.services.NotificationService;
import reviewSystem.services.ReviewerManager;

// central controller (submit(data) in diagram) coordinates all downstream calls
public class SubmissionController {
    private Validator validator;
    private Database database;
    private ReviewerManager reviewerManager;
    private EvaluationManager evaluationManager;
    private NotificationService notificationService;

    public SubmissionController(Validator validator, Database database, ReviewerManager reviewerManager, EvaluationManager evaluationManager, NotificationService notificationService) {
        this.validator = validator;
        this.database = database;
        this.reviewerManager = reviewerManager;
        this.evaluationManager = evaluationManager;
        this.notificationService = notificationService;
    }

    //UI calls SubmissionController submit(data)
    // Orchestrates the full sequence
    public String submit(Object data) {
        // SubmissionController calls Validator validateFormat(data)
        String validationResult = validator.validateFormat(data);
        // alt invalid -> return error
        if (validationResult.equals("invalid")) {
            return "error";
        }
        //valid -> continue
        // SubmissionController calls Database saveSubmission(data)
        Submission submission = new Submission(data);
        String confirmation = database.saveSubmission(submission);
        // SubmissionController calls ReviewerManager assignReviewers(submission)
        List<Reviewer> filteredReviewers = reviewerManager.getAvaliableReviewers();
        // loop - assign reviewers. SubmissionController calls Reviewr assignReviewers()
        reviewerManager.assignReviewers(filteredReviewers, submission);
        database.saveSubmission(submission);
        submission.setAssignedReviewers(filteredReviewers);
        // SubmissionController calls EvaluationManager startEvaluation()
        // loop each reviewer. submitScore(score)
        // SubmissionController calls Database saveScore(score) (inside the loop)
        EvaluationResult result = evaluationManager.startEvaluation(submission);
        //save scores (loop each reviewer)
        for (double score : result.getScores()) {
            database.saveScore(score);
        }
        // alt outcome NotificationService
        String outcome = result.getOutcome();
        if (outcome.equals("accepted")) {
            notificationService.notifyAcceptance(submission);
        } else if (outcome.equals("rejected")) {
            notificationService.notifyRejection(submission);
        } else if (outcome.equals("revision")) {
            notificationService.notifyRevision(submission);
        }

        return outcome;
    }
}
