package Model.DataProcessing;

import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.MealType;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

/**
 * Main class to demonstrate the functionality of the application.
 */
public class Main {
    public static void main(String[] args) {
        
        // Creating food items
        Food chicken = new Food(10086, "Chicken");
        Food rice = new Food(10087, "Rice");

        // Creating a diet for a specific meal
        Diet diet = new Diet(1, new Date(System.currentTimeMillis()), MealType.LUNCH);
        diet.addIngredient(chicken, 150.0);
        diet.addIngredient(rice, 300.0);

        // Tracking daily nutrient intake
        DailyNutrientIntake intake1 = new DailyNutrientIntake(1, 50.0, 200.0, 30.0);
        DailyNutrientIntake intake2 = new DailyNutrientIntake(2, 60.0, 180.0, 25.0);
        NutrientTracker nutrientTracker = new NutrientTracker();
        nutrientTracker.addDailyIntake(intake1);
        nutrientTracker.addDailyIntake(intake2);

        // Displaying diet information
        System.out.println("Diet Information:");
        System.out.println("Date: " + diet.getDate());
        System.out.println("Meal Type: " + diet.getMeal());
        System.out.println("Ingredients: " + diet.getIngredients());
        nutrientTracker.printSummary();
        nutrientTracker.calculatePercentages();

        // Using the Singleton pattern for database connection
        DatabaseConnector dbConnector = DatabaseConnector.getInstance();
        System.out.println("Database Connector HashCode: " + dbConnector.hashCode());

        // Analyzing the diet using different strategies
        DietAnalyzer analyzer = new DietAnalyzer(new StandardDietAnalysisStrategy());

        // Sample data for diet analysis
        int accountId = 1; 
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30); 
        Date startDate = new Date(cal.getTimeInMillis());
        Date endDate = new Date(System.currentTimeMillis()); 

        // Analyzing diet with standard strategy
        System.out.println("Diet Analysis with Standard Strategy: ");
        Map<String, Double> analysisResultStandard = analyzer.calculateDietAlignment(accountId, startDate, endDate);
        System.out.println(analysisResultStandard);

        // Switching to an alternative strategy for analysis
        analyzer.setAnalysisStrategy(new AlternativeDietAnalysisStrategy());
        System.out.println("Diet Analysis with Alternative Strategy: ");
        Map<String, Double> analysisResultAlternative = analyzer.calculateDietAlignment(accountId, startDate, endDate);
        System.out.println(analysisResultAlternative);
    }
}
