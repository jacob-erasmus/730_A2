package reviewSystem.Baseline.services;

import reviewSystem.Baseline.actors.Researcher;
import reviewSystem.Baseline.models.Submission;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * [alt accepted] EvaluationManager calls NoficationService notifyAcceptance()
 * [alt rejected] EvaluationManager calls NotificationService notifyRejection()
 * [alt revision] EvaluationManager calls NotificationService notifyRevision()
 * final interaction in sequence diagram: NotificationService calls Researcher receiveNotification(outcome)
 */
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
