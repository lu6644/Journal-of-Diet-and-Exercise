package Model.DataProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class responsible for managing health-related data.
 */
public class HealthModel {

    /**
     * Retrieves calorie intake and exercise data for a given date range.
     *
     * @param startDate The starting date of the period for which data is retrieved.
     * @param endDate   The ending date of the period for which data is retrieved.
     * @return A list of CalorieExerciseData objects, each containing calorie intake and exercise calories burned for a specific date.
     */
    public List<CalorieExerciseData> getCalorieAndExerciseData(Date startDate, Date endDate) {
        // SQL query to retrieve calorie intake and exercise data
        String sql = "SELECT D.date, SUM(FID.Quantity * NA.Nutrient_Value) as calorie_intake, SUM(ECB.calories_burnt) as exercise_calories_burned " +
                     "FROM diet D " +
                     "JOIN food_in_diet FID ON D.id = FID.diet_id " +
                     "JOIN nutrient_amount NA ON FID.food_id = NA.Food_ID " +
                     "JOIN exercise_calories_burnt ECB ON D.account_id = ECB.account_id " +
                     "WHERE D.date BETWEEN ? AND ? AND NA.Nutrient_ID = 208 " + // Assuming 208 is the nutrient ID for calories
                     "GROUP BY D.date";
        List<CalorieExerciseData> data = new ArrayList<>();

        // Database operations to retrieve and process data
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting the query parameters
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = pstmt.executeQuery();

            // Iterating through the result set to create CalorieExerciseData objects
            while (rs.next()) {
                CalorieExerciseData entry = new CalorieExerciseData(
                    rs.getDate("date"),
                    rs.getDouble("calorie_intake"),
                    rs.getDouble("exercise_calories_burned")
                );
                data.add(entry);
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            System.out.println(e.getMessage());
        }
        return data;
    }
}
