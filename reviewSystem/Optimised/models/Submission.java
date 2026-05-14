package reviewSystem.Optimised.models;

import java.util.ArrayList;
import java.util.List;
import reviewSystem.Evaluation.Metrics;

/*
* Not a class shown in the diagram, but required to carry the data through the sequence.
*/
public class Submission {
    private Object data; 
    /*
     * Traceable to:
     * Populated by ReviewerManage.assignReviewers() and used by EvaluationManager.evaluate().
     */
    private List<Reviewer> assignedReviewers; 

    public Submission(Object data) {
        this.data = data;
        this.assignedReviewers = new ArrayList<>();
    }

    // Used by Validator.validate(data)
    public Object getData() {
        Metrics mc = Metrics.getInstance();
        mc.record("Submission.getData");
        return data;
    }

    // Used by EvaluationManager [loop - each reviewer]
    public List<Reviewer> getAssignedReviewers() {
        Metrics mc = Metrics.getInstance();
        mc.record("Submission.getAssignedReviewers");
        return assignedReviewers;
    }

    // Called by ReviewerManager.assignReviewers() after [loop - assign reviewers]
    public void setAssignedReviewers(List<Reviewer> assignedReviewers){
        Metrics mc = Metrics.getInstance();
        mc.record("Submission.setAssignedReviewers");
        this.assignedReviewers = assignedReviewers;
    }
    
}
