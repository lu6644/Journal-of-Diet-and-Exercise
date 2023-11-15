package Model.DataProcessing;

import Model.DatabaseInteraction.NutrientsDAO;
import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.Nutrient;

import java.util.Map;

public class NutrientsCalculator {
    public static void getNutrientsValue(Diet diet){
        for (Map.Entry<Food,Double> entry: diet.getIngredients().entrySet()){
            Food food = entry.getKey();
            Double quantity = entry.getValue();
            NutrientsDAO nutrientsDAO = NutrientsDAO.getInstance();
            for (Map.Entry<Nutrient,Double> entryn: nutrientsDAO.extractNutrients(food).entrySet()){
                Nutrient nutrient = entryn.getKey();
                Double valuePer100Grams = entryn.getValue();
                Double nutrientAmount = valuePer100Grams * quantity / 100;
                diet.addToNutrient(nutrient, nutrientAmount);
            }
        }
        diet.findCalories();
    }


}
