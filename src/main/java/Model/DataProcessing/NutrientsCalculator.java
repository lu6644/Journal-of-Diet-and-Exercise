package Model.DataProcessing;

import Model.DatabaseInteraction.NutrientsDAO;
import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.Nutrient;

import java.util.Map;

/**
 * A utility class to calculate the nutritional values of a diet.
 */
public class NutrientsCalculator {
    /**
     * Calculates the nutrient values for a given diet.
     * @param diet The diet for which nutrient values are to be calculated.
     */
    public static void getNutrientsValue(Diet diet) {
        // Iterating over each entry (food item and its quantity) in the diet
        for (Map.Entry<Food, Double> entry : diet.getIngredients().entrySet()) {
            Food food = entry.getKey();
            Double quantity = entry.getValue();
            // Obtaining an instance of NutrientsDAO to extract nutrient information
            NutrientsDAO nutrientsDAO = NutrientsDAO.getInstance();
            // Iterating over each nutrient and its value per 100 grams for the current food item
            for (Map.Entry<Nutrient, Double> entryn : nutrientsDAO.extractNutrients(food).entrySet()) {
                Nutrient nutrient = entryn.getKey();
                Double valuePer100Grams = entryn.getValue();
                // Calculating the total amount of the nutrient based on the food quantity
                Double nutrientAmount = valuePer100Grams * quantity / 100;
                // Adding the calculated nutrient amount to the diet
                diet.addToNutrient(nutrient, nutrientAmount);
            }
        }
        // Optional: Calculate the total calories for the diet
        diet.findCalories();
    }
}
