package reviewSystem.services;

import reviewSystem.actors.Researcher;
import reviewSystem.models.Submission;

public class NotificationService {
    private Researcher researcher;
    
    public NotificationService(Researcher researcher) {
        this.researcher = researcher;
    }

    // [alt accepted] EvaluationManager calls NoficationService notifyAcceptance()
    public void notifyAcceptance(Submission submission) {
        System.out.println("Notification: submission accepted.");
        sendNotification("accepted");
    }
    // [alt rejected] EvaluationManager calls NotificationService notifyRejection()
    public void notifyRejection(Submission submission) {
        System.out.println("Notification: submission rejected.");
        sendNotification("rejected");
    }
    // [alt revision] EvaluationManager calls NotificationService notifyRevision()
    public void notifyRevision(Submission submission) {
        System.out.println("Notification: submission requires revision.");
        sendNotification("revision");
    }

    // final interaction in sequence diagram
    private void sendNotification(String outcome) {
        researcher.receiveNotification(outcome);
    }
}
