package Model.DataProcessing;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for analyzing diet compositions.
 * This class uses a DietAnalysisStrategy to perform the analysis.
 */
public class DietAnalyzer {
    private DietAnalysisStrategy analysisStrategy;

    /**
     * Constructor to initialize the DietAnalyzer with a specific analysis strategy.
     *
     * @param strategy The DietAnalysisStrategy to be used for diet analysis.
     */
    public DietAnalyzer(DietAnalysisStrategy strategy) {
        this.analysisStrategy = strategy;
    }

    /**
     * Calculates the alignment of a diet for a given account within a specified date range.
     * This method retrieves the diet composition and uses the current analysis strategy to analyze it.
     *
     * @param accountId The account ID for which the diet is being analyzed.
     * @param startDate The start date of the period for diet analysis.
     * @param endDate   The end date of the period for diet analysis.
     * @return A Map containing the analysis results, as determined by the current analysis strategy.
     */
    public Map<String, Double> calculateDietAlignment(int accountId, Date startDate, Date endDate) {
        Map<String, Double> dietComposition = getUserDietComposition(accountId, startDate, endDate);
        return analysisStrategy.analyzeDiet(dietComposition);
    }

    /**
     * Sets a new analysis strategy for the DietAnalyzer.
     *
     * @param strategy The new DietAnalysisStrategy to be used for diet analysis.
     */
    public void setAnalysisStrategy(DietAnalysisStrategy strategy) {
        this.analysisStrategy = strategy;
    }

    /**
     * Retrieves the diet composition for a given account and date range.
     *
     * @param accountId The account ID for which the diet composition is retrieved.
     * @param startDate The start date of the period for which the diet composition is retrieved.
     * @param endDate   The end date of the period for which the diet composition is retrieved.
     * @return A Map containing the diet composition, with nutrient names as keys and their total intake as values.
     */
    private Map<String, Double> getUserDietComposition(int accountId, java.sql.Date startDate, java.sql.Date endDate) {
        Map<String, Double> composition = new HashMap<>();
        // SQL query to retrieve diet composition data
        String query = "SELECT nn.Nutrient_Name, SUM(nid.Value) as TotalIntake " +
                       "FROM diet d " +
                       "JOIN nutrient_in_diet nid ON d.id = nid.Diet_ID " +
                       "JOIN nutrient_name nn ON nid.Nutrient_ID = nn.Nutrient_ID " +
                       "WHERE d.account_id = ? AND d.date BETWEEN ? AND ? " +
                       "AND nn.Nutrient_Name IN ('PROTEIN', 'FAT (TOTAL LIPIDS)', 'CARBOHYDRATE, TOTAL (BY DIFFERENCE)') " +
                       "GROUP BY nn.Nutrient_Name";

        // Database operations to retrieve and process data
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Processing the result set and populating the composition map
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
