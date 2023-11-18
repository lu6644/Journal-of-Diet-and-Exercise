package Model.DataProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthModel {

    public List<CalorieExerciseData> getCalorieAndExerciseData(Date startDate, Date endDate) {
        String sql = "SELECT D.date, SUM(FID.Quantity * NA.Nutrient_Value) as calorie_intake, SUM(ECB.calories_burnt) as exercise_calories_burned " +
                     "FROM diet D " +
                     "JOIN food_in_diet FID ON D.id = FID.diet_id " +
                     "JOIN nutrient_amount NA ON FID.food_id = NA.Food_ID " +
                     "JOIN exercise_calories_burnt ECB ON D.account_id = ECB.account_id " +
                     "WHERE D.date BETWEEN ? AND ? AND NA.Nutrient_ID = 208 " + // 假设 208 是卡路里的营养素ID
                     "GROUP BY D.date";
        List<CalorieExerciseData> data = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting the parameters
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = pstmt.executeQuery();

            // Loop through the result set
            while (rs.next()) {
                CalorieExerciseData entry = new CalorieExerciseData(
                    rs.getDate("date"),
                    rs.getDouble("calorie_intake"),
                    rs.getDouble("exercise_calories_burned")
                );
                data.add(entry);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
