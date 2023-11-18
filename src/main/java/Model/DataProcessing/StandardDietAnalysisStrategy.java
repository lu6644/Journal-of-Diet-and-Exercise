package Model.DataProcessing;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard strategy for analyzing diet composition.
 * This strategy compares actual intake with standard nutritional recommendations.
 */
public class StandardDietAnalysisStrategy implements DietAnalysisStrategy {

    /**
     * Analyzes the diet composition based on standard nutritional ratios.
     * @param dietComposition A map of nutrient names to their intake values.
     * @return A map of nutrient names to their deviation from standard ratios.
     */
    @Override
    public Map<String, Double> analyzeDiet(Map<String, Double> dietComposition) {
        Map<String, Double> analysisResult = new HashMap<>();

        // Standard recommended ratios for nutrients
        Map<String, Double> recommendedRatios = Map.of(
            "Protein", 20.0, // Recommended ratio 20%
            "Fat", 30.0,     // Recommended ratio 30%
            "Carbohydrates", 50.0  // Recommended ratio 50%
        );

        // Calculate total intake of nutrients
        double totalIntake = dietComposition.values().stream().mapToDouble(Double::doubleValue).sum();

        // Analyze each nutrient's intake against recommended ratios
        for (String nutrient : recommendedRatios.keySet()) {
            double intake = dietComposition.getOrDefault(nutrient, 0.0);
            double percentage = (intake / totalIntake) * 100;
            double deviation = percentage - recommendedRatios.get(nutrient);
            analysisResult.put(nutrient, deviation);
        }

        return analysisResult;
    }
}
