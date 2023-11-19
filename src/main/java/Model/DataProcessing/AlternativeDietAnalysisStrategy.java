package Model.DataProcessing;

import java.util.HashMap;
import java.util.Map;

public class AlternativeDietAnalysisStrategy implements DietAnalysisStrategy {

    @Override
    public Map<String, Double> analyzeDiet(Map<String, Double> dietComposition) {
        Map<String, Double> analysisResult = new HashMap<>();

        // Alternative recommended ratios for nutrients
        Map<String, Double> alternativeRatios = Map.of(
            "Protein", 30.0, // Recommended ratio 30%
            "Fat", 50.0,     // Recommended ratio 50%
            "Carbohydrates", 20.0  // Recommended ratio 20%
        );

        // Calculate total intake of nutrients
        double totalIntake = dietComposition.values().stream().mapToDouble(Double::doubleValue).sum();

        // Calculate deviation from recommended ratios for each nutrient
        for (String nutrient : alternativeRatios.keySet()) {
            double intake = dietComposition.getOrDefault(nutrient, 0.0);
            double percentage = (intake / totalIntake) * 100;
            double deviation = percentage - alternativeRatios.get(nutrient);
            // Store the deviation for each nutrient
            analysisResult.put(nutrient, deviation);
        }

        // Return the analysis results
        return analysisResult;
    }
}
