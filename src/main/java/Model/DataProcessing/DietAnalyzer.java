package Model.DataProcessing;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DietAnalyzer {
    private DietAnalysisStrategy analysisStrategy;

    public DietAnalyzer(DietAnalysisStrategy strategy) {
        this.analysisStrategy = strategy;
    }

    public Map<String, Double> calculateDietAlignment(int accountId, Date startDate, Date endDate) {
        Map<String, Double> dietComposition = getUserDietComposition(accountId, startDate, endDate);
        // 使用当前策略分析饮食组成
        return analysisStrategy.analyzeDiet(dietComposition);
    }

    public void setAnalysisStrategy(DietAnalysisStrategy strategy) {
        this.analysisStrategy = strategy;
    }

    private Map<String, Double> getUserDietComposition(int accountId, java.sql.Date startDate, java.sql.Date endDate) {
        Map<String, Double> composition = new HashMap<>();
        String query = "SELECT nn.Nutrient_Name, SUM(nid.Value) as TotalIntake " +
                       "FROM diet d " +
                       "JOIN nutrient_in_diet nid ON d.id = nid.Diet_ID " +
                       "JOIN nutrient_name nn ON nid.Nutrient_ID = nn.Nutrient_ID " +
                       "WHERE d.account_id = ? AND d.date BETWEEN ? AND ? " +
                       "AND nn.Nutrient_Name IN ('PROTEIN', 'FAT (TOTAL LIPIDS)', 'CARBOHYDRATE, TOTAL (BY DIFFERENCE)') " +
                       "GROUP BY nn.Nutrient_Name";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nutrientName = rs.getString("Nutrient_Name");
                    double totalIntake = rs.getDouble("TotalIntake");

                    switch (nutrientName) {
                        case "PROTEIN":
                            composition.put("Protein", totalIntake);
                            break;
                        case "FAT (TOTAL LIPIDS)":
                            composition.put("Fat", totalIntake);
                            break;
                        case "CARBOHYDRATE, TOTAL (BY DIFFERENCE)":
                            composition.put("Carbohydrates", totalIntake);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return composition;
    }
}
