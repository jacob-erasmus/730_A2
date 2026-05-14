package reviewSystem.Optimised.services;
import reviewSystem.Evaluation.Metrics;
/**
 * Tracable interactions:
 * SubmissionController -> NotificationService notify(outcome)
 * Optimisation: 
 * notifyAcceptance(), notifyRejection() and notifyRevision() replaced with notify(outcome). 
 * circular dependecy removed with Researcher.
 * NotificationService is terminal in the sequence diagram.
 */
public class NotificationService {
    // mo longer has reference to researcher, circular dependency removed.
    
    public NotificationService() {
    }

    // Traceable interactions: SubmissionController -> NotificationService notify(outcome)
    // optimisation: single parameterised method called by submission controller.
    public void notify(String outcome) {
        Metrics mc = Metrics.getInstance();
        mc.record("NotificationService.notify");
        switch(outcome) {
            case "accepted":
                System.out.println("Notification: submission accepted.");
                break;
            case "rejected":
                System.out.println("Notification: submission rejected.");
                break;
            case "revision":
                System.out.println("Notification: submission requires revision.");
                break;
            default:
                System.out.println("Notification: unknown outcome: " + outcome);
        }
    }
}
