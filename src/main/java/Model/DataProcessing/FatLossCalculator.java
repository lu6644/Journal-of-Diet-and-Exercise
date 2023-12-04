package Model.DataProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import Model.DatabaseInteraction.DatabaseConnector;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FatLossCalculator {

    private static final double CALORIES_PER_KG_FAT = 7700; // Constant representing the calories per kilogram of fat

    // Method to calculate expected fat loss until a future date for a given account
    public double calculateExpectedFatLoss(Date futureDate, int accountId) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection()) {
            double dailyCalorieIntake = getDailyCalorieIntake(conn, accountId); // Get daily calorie intake from database
            double dailyCaloriesBurned = getDailyCaloriesBurned(conn, accountId); // Get daily calories burned from database

            long daysUntilFutureDate = calculateDaysBetween(new Date(), futureDate); // Calculate days between current date and future date
            double dailyCalorieDeficit = dailyCaloriesBurned - dailyCalorieIntake; // Calculate daily calorie deficit
            double totalCalorieDeficit = dailyCalorieDeficit * daysUntilFutureDate; // Calculate total calorie deficit over the period

            return totalCalorieDeficit / CALORIES_PER_KG_FAT; // Calculate and return expected fat loss
        }
    }

    private double getDailyCalorieIntake(Connection conn, int accountId) throws SQLException {
        String sql = "SELECT AVG(Value) FROM nutrient_in_diet nid JOIN diet d ON nid.Diet_ID = d.id WHERE account_id = ? AND Nutrient_ID = 208";
        return executeAverageQuery(conn, sql, accountId);
    }


    // Refactored method to get daily calories burned
    private double getDailyCaloriesBurned(Connection conn, int accountId) throws SQLException {
        String sql = "SELECT AVG(calories_burnt) FROM exercise_calories_burnt WHERE account_id = ?";
        return executeAverageQuery(conn, sql, accountId);
    }


    // New method to execute a query and return the average value
    private double executeAverageQuery(Connection conn, String sql, int accountId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    // Method to get user's weight from the database
    private double getUserWeight(Connection conn, int accountId) throws SQLException {
        String sql = "SELECT weight FROM account WHERE account_id = ?"; // SQL query to fetch user's weight
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("weight"); // Return user's weight
            }
        }
        return 0; // Return 0 if no data found
    }

    // Method to calculate the number of days between two dates
    private long calculateDaysBetween(Date startDate, Date endDate) {
        LocalDate start = convertToLocalDate(startDate); // Convert startDate to LocalDate
        LocalDate end = convertToLocalDate(endDate); // Convert endDate to LocalDate
        return ChronoUnit.DAYS.between(start, end); // Return the number of days between the two dates
    }

    // Method to convert java.util.Date to java.time.LocalDate
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate(); // Convert and return LocalDate
    }
}
