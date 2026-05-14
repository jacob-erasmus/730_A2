package reviewSystem.ui;

import reviewSystemO.controllers.SubmissionController;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * Researcher -> UI submitResearchOutput(data)
 * UI -> SubmissionController submit(data)
 */
public class UI {
    private SubmissionController submissionController;

    public UI(SubmissionController controller) {
        this.submissionController = controller;
    }
    // Receives submission from researcher, passes to submision controller.
    public void submitResearchOutput(Object data) {
        // UI -> SubmissionController submit(data)
        submissionController.submit(data);
    }
}
