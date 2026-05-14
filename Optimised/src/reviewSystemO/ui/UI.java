package reviewSystemO.ui;

import reviewSystemO.controllers.SubmissionController;

/**
 * Tracable interactions:
 * Researcher -> UI submitResearchOutput(data)
 * UI -> SubmissionController submit(data)
 * No optimisation, no change.
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
