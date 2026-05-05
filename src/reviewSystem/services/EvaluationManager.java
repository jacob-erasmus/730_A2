package reviewSystem.services;

import java.util.ArrayList;
import java.util.List;

import reviewSystem.models.EvaluationResult;
import reviewSystem.models.Reviewer;
import reviewSystem.models.Submission;

public class EvaluationManager {
    //SubmissionController calls EvaluationManager startEvaluation()
    // Loop each reviewer, calls submitScore(score)
    // then calculateAverage(), checkConsensus(), applyRules()
    // returns EvaluationResult
    public EvaluationResult startEvaluation(Submission submission) {
        List<Double> scores = new ArrayList<>();
        //loop for each reviewer
        for (Reviewer r : submission.getAssignedReviewers()) {
            double score = r.submitScore();
            scores.add(score);
        }
        double averageScore = calculateAverage(scores);
        boolean consensus = checkConsensus(scores);
        String outcome = applyRules(averageScore, consensus);
        EvaluationResult result = new EvaluationResult();
        result.setAverageScore(averageScore);
        result.setConsensusReached(consensus);
        result.setOutcome(outcome);
        return result;
    }

    // EvaluationManager self calls calculateAverage(scores)
    public double calculateAverage(List<Double> scores) {
        return scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
    // EvaluationManager self calls checkConsensus(scores)
    public boolean checkConsensus(List<Double> scores) {
        return true;
    }
    // EvaluationManager self calls applyRules(averageScore, consensus)
    public String applyRules(double averageScore, boolean consensus) {
        if (consensus && averageScore >= 3.0) { // Assuming 3.0 is the threshold for acceptance
            return "Accept";
        } else if (consensus && averageScore < 3.0) {  // Assuming below 3.0 is the threshold for rejection
            return "Reject";
        } else {
            return "Revise";
        }
    }
    
}
