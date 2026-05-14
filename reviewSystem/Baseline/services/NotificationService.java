package reviewSystem.Baseline.services;

import reviewSystem.Baseline.actors.Researcher;
import reviewSystem.Baseline.models.Submission;
import reviewSystem.Evaluation.Metrics;

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
        Metrics mc = Metrics.getInstance();
        mc.record("NotificationService.notifyAcceptance");
        System.out.println("Notification: submission accepted.");
        sendNotification("accepted");
    }
    // [alt rejected] EvaluationManager calls NotificationService notifyRejection()
    public void notifyRejection(Submission submission) {
        Metrics mc = Metrics.getInstance();
        mc.record("NotificationService.notifyRejection");
        System.out.println("Notification: submission rejected.");
        sendNotification("rejected");
    }
    // [alt revision] EvaluationManager calls NotificationService notifyRevision()
    public void notifyRevision(Submission submission) {
        Metrics mc = Metrics.getInstance();
        mc.record("NotificationService.notifyRevision");
        System.out.println("Notification: submission requires revision.");
        sendNotification("revision");
    }

    // final interaction in sequence diagram
    private void sendNotification(String outcome) {
        Metrics mc = Metrics.getInstance();
        mc.record("NotificationService.sendNotification");
        researcher.receiveNotification(outcome);
    }
}
