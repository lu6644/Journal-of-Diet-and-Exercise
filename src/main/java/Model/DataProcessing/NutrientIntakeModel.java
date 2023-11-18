package Model.DataProcessing;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NutrientIntakeModel {
    public List<NutrientIntake> getNutrientIntakeForPeriod(java.sql.Date startDate, java.sql.Date endDate) {
        List<NutrientIntake> nutrientIntakes = new ArrayList<>();
        String sql = "SELECT d.date, SUM(nid.Value) AS ProteinIntake " +
                     "FROM diet d " +
                     "JOIN nutrient_in_diet nid ON d.id = nid.Diet_ID " +
                     "JOIN nutrient_name nn ON nid.Nutrient_ID = nn.Nutrient_ID " +
                     "WHERE nn.Nutrient_Name = 'PROTEIN' " +
                     "AND d.date BETWEEN ? AND ? " +
                     "GROUP BY d.date";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date date = rs.getDate("date");
                    double proteinIntake = rs.getDouble("ProteinIntake");
                    nutrientIntakes.add(new NutrientIntake(date, proteinIntake));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nutrientIntakes;
    }
}
