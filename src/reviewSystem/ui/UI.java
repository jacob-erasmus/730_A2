package reviewSystem.ui;

import reviewSystem.controllers.SubmissionController;

public class UI {
    private SubmissionController submissionController;
    public UI(SubmissionController controller) {
        this.submissionController = controller;
    }

    public void submitResearchOutput(Object data) {
        String result = submissionController.submit(data);
        System.out.println("Result: " + result);
    }
}
