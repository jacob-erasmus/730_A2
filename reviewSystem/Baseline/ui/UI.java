package reviewSystem.Baseline.ui;

import reviewSystem.Baseline.controllers.SubmissionController;
import reviewSystem.Evaluation.Metrics;
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
        Metrics mc = Metrics.getInstance();
        mc.record("UI.submitResearchOutput");
        // UI -> SubmissionController submit(data)
        submissionController.submit(data);
    }
}
