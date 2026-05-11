package reviewSystem.models;
/* Required to service the EvaluationManger's self-calls
*  - calculateAverage()
*  - checkConsensus()
*  - applyRules()
 */

import java.util.List;

public class EvaluationResult {
    private double averageScore;
    private boolean consensusReached;
    private String outcome; // "accepted", "rejected", "revision" - alt branch
    private List<Double> scores;

    public EvaluationResult(double averageScore, boolean consensusReached, String outcome, List<Double> scores) {
        this.averageScore = averageScore;
        this.consensusReached = consensusReached;
        this.outcome = outcome;
        this.scores = scores;
    }

    // Used by SubmissionController to dispatch alt notification
    public String getOutcome() {
        return outcome;
    }

    // Used by SubmissionController to save scores
    public List<Double> getScores() {
        return scores;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public boolean isConsensusReached() {
        return consensusReached;
    }
}
