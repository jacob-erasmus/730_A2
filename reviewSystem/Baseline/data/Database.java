package reviewSystem.Baseline.data;
import java.util.ArrayList;
import java.util.List;

import reviewSystem.Baseline.models.Reviewer;
import reviewSystem.Baseline.models.Submission;
import reviewSystem.Evaluation.DatabaseManager;
import reviewSystem.Evaluation.Metrics;

import java.sql.*;

/**
 * Class as shown in the diagram.
 * Tracable interactions:
 * SubmissionController -> Database saveSubmission(data)
 * Database -> SubmissionController confirmation
 * ReviewerManager -> Database fetchReviewers()
 * Database -> ReviewerManager reviewerList
 * EvaluationManager -> Database saveScore(score)
 */
public class Database {
    public Database() {
    }

    // SubmissionController calls Database saveSubmission(data)
    // returns confirmation
    // After [alt valid]
    public String saveSubmission(Submission data) {
        Metrics.getInstance().record("Database.saveSubmission");
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO submissions (data) VALUES (?)")){
            ps.setString(1, data.getData().toString());
            ps.executeUpdate();
        }catch (SQLException e) {e.printStackTrace();}
        return "confirmation";
    }

    // ReviewerManager calls Database fetchReviewers()
    // returns reviewrList to reviewer manager for filtering and workload checks.
    public List<Reviewer> fetchReviewers() {
        Metrics.getInstance().record("Database.fetchReviewers");
        List<Reviewer> list = new ArrayList<>();
        try (Statement stmt = DatabaseManager.getConnection().createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM reviewers")) {
            while (rs.next()){
            list.add(new Reviewer(rs.getBoolean("hasConflict"), rs.getInt("currentWorkload")));
            }
        }catch (SQLException e) { e.printStackTrace();} return list;
    }

    // EvaluationManager calls Database saveScore(score)
    public void saveScore(double score) {
        Metrics.getInstance().record("Database.saveScore");
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO scores (score) VALUES (?)")){
            ps.setDouble(1, score);
            ps.executeUpdate();
        }catch (SQLException e) { e.printStackTrace();}
        System.out.println("Score saved: " + score);
    }
}
