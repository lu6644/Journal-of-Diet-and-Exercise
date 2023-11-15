package Model.Diet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Diet {
    int id;
    int accountId;
    Date date;
    MealType meal;

    HashMap<Food,Double> ingredients = new HashMap<>();

    HashMap<Nutrient,Double> nutrientsValue = new HashMap<>();
    Double calories = 0.0;

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }



    @Override
    public String toString() {
        return "Diet{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", date=" + date +
                ", meal=" + meal.name() +
                ", ingredients=" + ingredients.toString() +
                ", nutrientsValue=" + nutrientsValue.toString() +
                '}';
    }

    public Diet(int accountId, Date date, MealType meal){
        this.accountId = accountId;
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

    public void findCalories(){
        calories = nutrientsValue.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals("ENERGY (KILOCALORIES)"))
                .map(Map.Entry::getValue)
                .findFirst().orElse(0.0);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
