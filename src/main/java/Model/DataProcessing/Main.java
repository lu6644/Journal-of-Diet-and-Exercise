public class Main {
    public static void main(String[] args) {
        
        DailyNutrientIntake intake1 = new DailyNutrientIntake(1, 50.0, 200.0, 30.0);
        DailyNutrientIntake intake2 = new DailyNutrientIntake(2, 60.0, 180.0, 25.0);

        
        Diet diet = new Diet();
        Food food1 = new Food("10086", "Chicken");
        Food food2 = new Food("10087", "Rice");
        diet.addIngredient(food1, 150.0);
        diet.addIngredient(food2, 300.0);

        
        NutrientsCalculator.getNutrientsValue(diet);
        
        NutrientTracker nutrientTracker = new NutrientTracker();
        nutrientTracker.addDailyIntake(intake1);
        nutrientTracker.addDailyIntake(intake2);

        
        nutrientTracker.printSummary();
        nutrientTracker.calculatePercentages();
    }
}
