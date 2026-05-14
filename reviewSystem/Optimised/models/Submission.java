package reviewSystem.Optimised.models;

import java.util.ArrayList;
import java.util.List;

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
        return data;
    }

    // Used by EvaluationManager [loop - each reviewer]
    public List<Reviewer> getAssignedReviewers() {
        return assignedReviewers;
    }

    // Called by ReviewerManager.assignReviewers() after [loop - assign reviewers]
    public void setAssignedReviewers(List<Reviewer> assignedReviewers){
        this.assignedReviewers = assignedReviewers;
    }
    
}
