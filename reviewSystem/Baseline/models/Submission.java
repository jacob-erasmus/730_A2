package reviewSystem.Baseline.models;

import java.util.ArrayList;
import java.util.List;

import reviewSystem.Baseline.models.Reviewer;

/*
* Not a class shown in the diagram, but required to carry the data through the sequence.
*/
public class Submission {
    // payload from Researcher, could be any data type, so using Object as placeholder.
    /* Traceable to:
    * Researcher calls submitResearchOutput(data) on UI, which calls submit(data) on SubmissionController,
    * which calls validateFormat(data) on Validator and saveSubmission(data) on Database. 
    */
    private Object data; 
    /*
     * Traceable to:
     * [loop - assign reviewers] SubmissionController calls Reviewer assignReview()
     * Required so EvaluationManager can loop through reviewers in [loop - each reviewer] so Reviewer can call submitScore(score) on EvaluationManager.
     */
    private List<Reviewer> assignedReviewers; 

    public Submission(Object data) {
        this.data = data;
        this.assignedReviewers = new ArrayList<>();
    }

    // Used by Validator.validateFormat(data)
    public Object getData() {
        return data;
    }

    // Used by EvaluationManager.startEvaluation() to iterate [loop - each reviewer]
    public List<Reviewer> getAssignedReviewers() {
        return assignedReviewers;
    }

    // Called by SubmissionController after [loop - assign reviewers]
    public void setAssignedReviewers(List<Reviewer> assignedReviewers){
        this.assignedReviewers = assignedReviewers;
    }
    
}
