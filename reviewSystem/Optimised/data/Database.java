package reviewSystem.Optimised.data;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.*;

import reviewSystem.Evaluation.*;
import reviewSystem.Optimised.models.Reviewer;
import reviewSystem.Optimised.models.Submission;

/**
 * Tracable interactions:
 * SubmissionController -> Database saveSubmission(data)
 * Database -> SubmissionController confirmation
 * ReviewerManager -> Database fetchEligableReviewers()
 * Database -> ReviewerManager eligableReviewers
 * EvaluationManager -> Database saveScore(score)
 * 
 * Optimisation:
 * fetchReviewers() now is fetchEligableReviewers() with the filtering logic moved,
 * eliminating filterConflicts() and checkWorkload() self calls from ReviewerManager, and reducing the number of interactions and data passed between classes.
 */
public class Database {
    public static final int MAX_WORKLOAD = 5; // Example threshold for maximum workload
    public Database() {
    }

    // SubmissionController calls Database saveSubmission(data)
    // returns confirmation
    public String saveSubmission(Submission data) {
        Metrics.getInstance().record("Database.saveSubmission");
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO submissions (data) VALUES (?)")){
            ps.setString(1, data.getData().toString());
            ps.executeUpdate();
        }catch (SQLException e) {e.printStackTrace();}
        return "confirmation";
    }

    // ReviewerManager calls Database fetchEligableReviewers()
    // returns eligibleReviewers.
    // Optimisation is that the filtering logic is moved here, so only eligable reviewers are returned, eliminating the need for separate filterConflicts() and checkWorkload() calls in ReviewerManager.
    public List<Reviewer> fetchEligibleReviewers() {
        Metrics.getInstance().record("Database.fetchEligiableReviewers");
        List<Reviewer> list = new ArrayList<>(); 
        String sql = "SELECT * FROM reviewers WHERE hasConflict = FALSE AND currentWorkload < ?";
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, MAX_WORKLOAD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new Reviewer(rs.getBoolean("hasConflict"),rs.getInt("currentWorkload")));
            }
        }catch(SQLException e) { e.printStackTrace();}
        return list;
        /* 
        List<Reviewer> eligible = new ArrayList<>();
        for (Reviewer reviewer : allReviewers) {
            if (!reviewer.hasConflict() && reviewer.getCurrentWorkload() < MAX_WORKLOAD) {
                eligible.add(reviewer);
            }
        }
        return eligible;
        */
    }

    // EvaluationManager calls Database saveScore(score)
    // Optimisation: no repetivate loop call, single batch call after loop completion.
    public void saveScore(List<Double> scores) {
        Metrics.getInstance().record("Database.saveScores");
        if (scores == null || scores.isEmpty()) return;
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO scores (score) VALUES (?)")){
            for(double score : scores){
                ps.setDouble(1,score);
                ps.addBatch();
            }
            ps.executeBatch();
        }catch (SQLException e){e.printStackTrace();}
    }
}
