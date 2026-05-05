package reviewSystem.models;

import java.util.List;

public class Submission {
    private String id;
    private Object data;
    private String status;
    private List<Double> scores;
    private List<Reviewer> assignedReviewers;
}
