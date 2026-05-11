package reviewSystem.actors;

import reviewSystem.ui.UI;

public class Researcher {
    private UI ui;
    public Researcher(UI ui) {
        this.ui = ui;
    }
    
    public void submitResearchOutput(Object data) {
        ui.submitResearchOutput(data);
    }

    // recieve notification from NotificationService
    public void receiveNotification(String outcome) {
        System.out.println("Researcher received notification: " + outcome);
    }
}
