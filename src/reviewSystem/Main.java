package reviewSystem;

import reviewSystem.services.Validator;

import reviewSystem.actors.Researcher;
import reviewSystem.controllers.SubmissionController;
import reviewSystem.data.Database;
import reviewSystem.models.Reviewer;
import reviewSystem.services.EvaluationManager;
import reviewSystem.services.NotificationService;
import reviewSystem.services.ReviewerManager;
import reviewSystem.ui.UI;

public class Main {
    public static void main(String[] args) {
        // Initialize components and start the application
        Database database = new Database();
        Validator validator = new Validator();
        ReviewerManager reviewerManager = new ReviewerManager(database);
        NotificationService notificationService = new NotificationService();
        EvaluationManager evaluationManager = new EvaluationManager(notificationService);
        SubmissionController submissionController = new SubmissionController(validator, database, reviewerManager, evaluationManager, notificationService);
        UI ui = new UI(submissionController);
        Researcher researcher = new Researcher(ui);

        // Start: Researcher calls sumitResearchOutput(data)
        researcher.submitResearchOutput(new Object());

    }
}
