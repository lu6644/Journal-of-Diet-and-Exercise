package Model.Diet;

import java.util.Date;
import java.util.HashMap;

public class Diet {
    Date date;
    MealType meal;

    HashMap<Food,Double> ingredients = new HashMap<>();

    HashMap<Nutrient,Double> nutrientsValue = new HashMap<>();

    public Diet(Date date, MealType meal){
        this.date = date;
        this.meal = meal;
    }

    public void addIngredient(Food food, double quantity){
        ingredients.put(food,quantity);
    }

    public void addToNutrient(Nutrient nutrient, Double amount){
        if (nutrientsValue.containsKey(nutrient)){
            double updatedAmount = nutrientsValue.get(nutrient) + amount;
            nutrientsValue.put(nutrient, updatedAmount);
        }
        else {
            nutrientsValue.put(nutrient, amount);
        }
    }

    public Date getDate() {
        return date;
    }

    public MealType getMeal() {
        return meal;
    }

    public HashMap<Food, Double> getIngredients() {
        return ingredients;
    }

    public HashMap<Nutrient, Double> getNutrientsValue() {
        return nutrientsValue;
    }

    public boolean snackAllowed(){
        //TODO: Implement querying last meal type from db and give condition for allowing snack
        return true;
    }

}
