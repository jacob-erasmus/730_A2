package reviewSystemO;

import reviewSystemO.actors.Researcher;
import reviewSystemO.controllers.SubmissionController;
import reviewSystemO.data.Database;
import reviewSystemO.services.EvaluationManager;
import reviewSystemO.services.NotificationService;
import reviewSystemO.services.ReviewerManager;
import reviewSystemO.services.Validator;
import reviewSystemO.ui.UI;

/**
 * Main class to set up dependencies and trigger.
 * 
 * Optimisation:
 * No circular dependency between Researcher and NotificationService, so no need for null UI reference in Researcher constructor, and no need to set UI reference after creating NotificationService.
 */
public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Validator validator = new Validator();
        NotificationService notificationService = new NotificationService();
        ReviewerManager reviewerManager = new ReviewerManager(database);
        // NotifcationService is no longer a dependency of EvaluationManager.
        EvaluationManager evaluationManager = new EvaluationManager(database);
        // NotifcationService is now a dependency of SubmissionController not EvaluationManager.
        SubmissionController submissionController = new SubmissionController(validator, database, reviewerManager, evaluationManager, notificationService);
        // no null UI workaround needed, can be created directly.
        UI ui = new UI(submissionController);
        Researcher researcher = new Researcher(ui);

        // Trigger: Researcher calls sumitResearchOutput(data)
        Object data = new Object(); 
        researcher.submitResearchOutput(data);
    }
}
