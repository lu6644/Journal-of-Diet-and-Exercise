package Model.DataProcessing;

import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.MealType;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        
        Food chicken = new Food(10086, "Chicken");
        Food rice = new Food(10087, "Rice");

       
        Diet diet = new Diet(1, new Date(), MealType.LUNCH);

        
        diet.addIngredient(chicken, 150.0);
        diet.addIngredient(rice, 300.0);

        
        System.out.println("Diet Information:");
        System.out.println("Date: " + diet.getDate());
        System.out.println("Meal Type: " + diet.getMeal());
        System.out.println("Ingredients: " + diet.getIngredients());

       
    }
}

