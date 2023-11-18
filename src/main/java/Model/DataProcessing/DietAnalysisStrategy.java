package Model.DataProcessing;

import java.util.Map;

/**
 * Interface for diet analysis strategies.
 * This interface defines a method for analyzing diet compositions.
 */
public interface DietAnalysisStrategy {

    /**
     * Analyze the given diet composition and return the analysis results.
     * Implementations of this method should provide specific logic to analyze
     * the diet based on the provided diet composition data.
     *
     * @param dietComposition A map containing the composition of the diet,
     *                        typically with nutrient names as keys and their quantities as values.
     * @return A Map representing the analysis results, typically containing
     *         metrics or insights derived from the diet composition.
     */
    Map<String, Double> analyzeDiet(Map<String, Double> dietComposition);
}
