package reviewSystem.actors;

import reviewSystem.ui.UI;

/**
 * Class as shown in Diagram.
 * Tracable interactions:
 * Researcher -> UI submitResearchOutput(data)
 * NotificationService sendNotification() to Researcher
 * 
 * Researcher is the actor that triggers the entire sequence & the terminal recpient.
 */
public class Researcher {
    private UI ui;

    public Researcher(UI ui) {
        this.ui = ui;
    }
    
    // Used to set UI reference after circular dependency with NotificationService is resolved in Main.
    public void setUI(UI ui) {
        this.ui = ui;
    }
    
    // Initates the enitre sequence, called in Main to trigger flow as shown in sequence diagram.
    // Researcher -> UI: submitResearchOutput(data)
    public void submitResearchOutput(Object data) {
        ui.submitResearchOutput(data);
    }

    // As diagram: NotificationService sendNotification() to Researcher
    // called by notification service as final interaction in sequence diagram, researcher is recipient of sendNotification().
    public void receiveNotification(String outcome) {
        System.out.println("Researcher received notification: " + outcome);
    }
}
