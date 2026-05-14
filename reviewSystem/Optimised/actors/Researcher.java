package reviewSystem.Optimised.actors;

import reviewSystem.Optimised.ui.UI;
import reviewSystem.Evaluation.Metrics;
/**
 * Tracable interactions:
 * Researcher -> UI submitResearchOutput(data)
 * 
 * Optimisation:
 * NotificationService no longer sends notification to Researcher, circular dependency removed.
 * 
 * Researcher is the actor that triggers the entire sequence.
 */
public class Researcher {
    private UI ui;

    public Researcher(UI ui) {
        this.ui = ui;
    }
    
    // Initates the enitre sequence
    // Researcher -> UI: submitResearchOutput(data)
    public void submitResearchOutput(Object data) {
        Metrics mc = Metrics.getInstance();
        mc.record("Researcher.submitResearchOutput");
        ui.submitResearchOutput(data);
    }
}
