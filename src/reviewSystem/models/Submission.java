package reviewSystem.models;

import java.util.List;

public class Submission {
    private String id;
    private Object data;
    private String status;
    private List<Double> scores;
    private List<Reviewer> assignedReviewers;
    public Reviewer[] getAssignedReviewers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAssignedReviewers'");
    }
}
