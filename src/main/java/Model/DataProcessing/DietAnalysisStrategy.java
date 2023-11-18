package Model.DataProcessing;

import java.util.Map;

public interface DietAnalysisStrategy {
    Map<String, Double> analyzeDiet(Map<String, Double> dietComposition);
}
