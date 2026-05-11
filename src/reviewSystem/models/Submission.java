package reviewSystem.models;

import java.util.ArrayList;
import java.util.List;

/*
* Required to carry the data through the lifelines in the diagram.
* Also holds assigned reviewers.
*/
public class Submission {
    private Object data; //payload from Researcher
    private List<Reviewer> assignedReviewers; 
    private String status; // set after EvaluationManager applies rules

    public Submission(Object data) {
        this.data = data;
        this.assignedReviewers = new ArrayList<>();
        this.status = "pending";
    }

    // Used by Validator.validateFormat(data)
    public Object getData() {
        return data;
    }

    // Used by EvaluationManager.startEvaluation()
    public List<Reviewer> getAssignedReviewers() {
        return assignedReviewers;
    }

    // Used by NotificationService
    public String getStatus() {
        return status;
    }

    // Used by SubmissionController after loop - assign reviewers
    public void setAssignedReviewers(List<Reviewer> assignedReviewers){
        this.assignedReviewers = assignedReviewers;
    }

    // Used by SubmissionController after EvaluationManager returns outcome via applyRules()
    public void setStatus(String status) {
        this.status = status;
    }
    
}
