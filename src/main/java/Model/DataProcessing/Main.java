package Model.DataProcessing;

import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.MealType;
import Model.DataProcessing.DailyNutrientIntake;
import Model.DataProcessing.NutrientTracker;
import Model.Diet.Nutrient;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        
        Food chicken = new Food(10086, "Chicken");
        Food rice = new Food(10087, "Rice");

       
        Diet diet = new Diet(1, new Date(), MealType.LUNCH);

        
        diet.addIngredient(chicken, 150.0);
        diet.addIngredient(rice, 300.0);
        
        DailyNutrientIntake intake1 = new DailyNutrientIntake(1, 50.0, 200.0, 30.0);
        DailyNutrientIntake intake2 = new DailyNutrientIntake(2, 60.0, 180.0, 25.0);
        NutrientTracker nutrientTracker = new NutrientTracker();
        
        nutrientTracker.addDailyIntake(intake1);
        nutrientTracker.addDailyIntake(intake2);
        
        
        System.out.println("Diet Information:");
        System.out.println("Date: " + diet.getDate());
        System.out.println("Meal Type: " + diet.getMeal());
        System.out.println("Ingredients: " + diet.getIngredients());

        nutrientTracker.printSummary();
        nutrientTracker.calculatePercentages();
    }
}

