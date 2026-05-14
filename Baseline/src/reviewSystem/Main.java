package reviewSystem;

import reviewSystemO.actors.Researcher;
import reviewSystemO.controllers.SubmissionController;
import reviewSystemO.data.Database;
import reviewSystemO.services.EvaluationManager;
import reviewSystemO.services.NotificationService;
import reviewSystemO.services.ReviewerManager;
import reviewSystemO.services.Validator;
import reviewSystemO.ui.UI;

/**
 * Main class to set up dependencies and start the sequence.
 */
public class Main {
    public static void main(String[] args) {
        // Database has no dependenices, so can be created first.
        Database database = new Database();
        // Validator has no dependencies, so can be created next.
        Validator validator = new Validator();
        // ReviewerManager depends on Database, so can be created next.
        ReviewerManager reviewerManager = new ReviewerManager(database);
        // Researcher must exist before NotificationService - circular depenendcy. Null UI reference used
        Researcher researcher = new Researcher(null);
        // NotificationService depends on Researcher, so can be created next.
        NotificationService notificationService = new NotificationService(researcher);
        // EvaluationManager depends on NotificationService, so can be created next.
        EvaluationManager evaluationManager = new EvaluationManager(notificationService, database);
        // SubmissionController, can be created now.
        SubmissionController submissionController = new SubmissionController(validator, database, reviewerManager, evaluationManager);
        // UI depends on SubmissionController.
        UI ui = new UI(submissionController);
        // Complete circular dependency - set UI reference in Researcher
        researcher.setUI(ui);

        // Start the entire sequence: Researcher calls sumitResearchOutput(data)
        Object data = new Object(); // placeholder for actual research output data
        researcher.submitResearchOutput(data);

    }
}
