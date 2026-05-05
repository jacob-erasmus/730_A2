package reviewSystem;

import javax.xml.validation.Validator;

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
        EvaluationManager evaluationManager = new EvaluationManager();
        NotificationService notificationService = new NotificationService();
        SubmissionController submissionController = new SubmissionController(validator, database, reviewerManager, evaluationManager, notificationService);
        UI ui = new UI(submissionController);
        Researcher researcher = new Researcher(ui);

        // Start: Researcher calls sumitResearchOutput(data)
        researcher.submitResearchOutput(new Object());

    }
}
