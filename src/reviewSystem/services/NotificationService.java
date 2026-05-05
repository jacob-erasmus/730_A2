package reviewSystem.services;

import reviewSystem.models.Submission;

public class NotificationService {
    // [alt accepted] EvaluationManager calls NoficationService notifyAcceptance()
    public void notifyAcceptance(Submission submission) {
        System.out.println("Notification: Acceptance for: " + submission.getId());
    }
    // [alt rejected] EvaluationManager calls NotificationService notifyRejection()
    public void notifyRejection(Submission submission) {
        System.out.println("Notification: Rejection for: " + submission.getId());
    }
    // [alt revision] EvaluationManager calls NotificationService notifyRevision()
    public void notifyRevision(Submission submission) {
        System.out.println("Notification: Revision needed for: " + submission.getId());
    }
}
