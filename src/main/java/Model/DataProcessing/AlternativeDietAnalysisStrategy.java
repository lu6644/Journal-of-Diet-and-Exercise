package Model.DataProcessing;

import java.util.HashMap;
import java.util.Map;

public class AlternativeDietAnalysisStrategy implements DietAnalysisStrategy {

    @Override
    public Map<String, Double> analyzeDiet(Map<String, Double> dietComposition) {
        Map<String, Double> analysisResult = new HashMap<>();

        // �����Ƽ�ֵ
        Map<String, Double> alternativeRatios = Map.of(
            "Protein", 30.0, // �Ƽ�ռ�� 30%
            "Fat", 50.0,     // �Ƽ�ռ�� 50%
            "Carbohydrates", 20.0  // �Ƽ�ռ�� 20%
        );

        double totalIntake = dietComposition.values().stream().mapToDouble(Double::doubleValue).sum();

        for (String nutrient : alternativeRatios.keySet()) {
            double intake = dietComposition.getOrDefault(nutrient, 0.0);
            double percentage = (intake / totalIntake) * 100;
            double deviation = percentage - alternativeRatios.get(nutrient);
            analysisResult.put(nutrient, deviation);
        }

        return analysisResult;
    }
}
