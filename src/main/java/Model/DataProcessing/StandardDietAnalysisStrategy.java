package Model.DataProcessing;

import java.util.HashMap;
import java.util.Map;

public class StandardDietAnalysisStrategy implements DietAnalysisStrategy {

    @Override
    public Map<String, Double> analyzeDiet(Map<String, Double> dietComposition) {
        Map<String, Double> analysisResult = new HashMap<>();

        // 标准推荐值
        Map<String, Double> recommendedRatios = Map.of(
            "Protein", 20.0, // 推荐占比 20%
            "Fat", 30.0,     // 推荐占比 30%
            "Carbohydrates", 50.0  // 推荐占比 50%
        );

        double totalIntake = dietComposition.values().stream().mapToDouble(Double::doubleValue).sum();

        for (String nutrient : recommendedRatios.keySet()) {
            double intake = dietComposition.getOrDefault(nutrient, 0.0);
            double percentage = (intake / totalIntake) * 100;
            double deviation = percentage - recommendedRatios.get(nutrient);
            analysisResult.put(nutrient, deviation);
        }

        return analysisResult;
    }
}
