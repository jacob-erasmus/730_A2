package reviewSystem.Evaluation;
import java.sql.*;
import reviewSystem.Evaluation.Metrics;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:mem:reviewdb;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            initSchema();
            seedData();
        }
        return connection;
    }

    private static void initSchema() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS submissions (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "data VARCHAR(255)," +
                "ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")");

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS reviewers (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100)," +
                "hasConflict BOOLEAN DEFAULT FALSE, " +
                "currentWorkload INT DEFAULT 0" +
                ")");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS scores (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "submissionId BIGINT, " +
                "score DOUBLE, " +
                "ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")");
        stmt.close();
    }

    private static void seedData() throws SQLException {
        Statement check = connection.createStatement();
        ResultSet rs = check.executeQuery("SELECT COUNT(*) FROM reviewers");
        rs.next();
        if(rs.getInt(1) > 0) {
            rs.close();
            check.close();
            return; // Data already seeded
        }
        // Provide 5 reviewers, 3 eligible, 1 with conflict, 1 at excessive workload.
        PreparedStatement ps = connection.prepareStatement("INSERT INTO reviewers (name, hasConflict, currentWorkload) VALUES (?, ?, ?)");
        Object[][] data = {
            {"Jacob", false, 2},
            {"George", false, 1},
            {"Erasmus", false, 0},
            {"Daniel", true, 1}, //conflict
            {"Trevor", false, 6} //excessive workload
        };
        for (Object[] row : data) {
            ps.setString(1, (String) row[0]);
            ps.setBoolean(2, (Boolean) row[1]);
            ps.setInt(3, (Integer) row[2]);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

    public static void resetScores() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate("DELETE FROM scores");
        stmt.execute("DELETE FROM submissions");
        stmt.close();
    }
}