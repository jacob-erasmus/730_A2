package reviewSystem.Optimised.ui;

import reviewSystem.Optimised.controllers.SubmissionController;
import reviewSystem.Evaluation.Metrics;
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
        Metrics mc = Metrics.getInstance();
        mc.record("UI.submitResearchOutput");
        // UI -> SubmissionController submit(data)
        submissionController.submit(data);
    }
}
