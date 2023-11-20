package Model.DataProcessing;

<<<<<<< HEAD
import Model.DatabaseInteraction.DatabaseConnector;
import Model.DatabaseInteraction.DietAnalyzer;
=======
import Model.DatabaseInteraction.ProfileDAO;
>>>>>>> 16d3cfe1cf87c642cf9a2e506a8460518a33b2bc
import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.MealType;
import Model.Profile.UserProfile;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Create food and diet
        Food chicken = new Food(10086, "Chicken");
        Food rice = new Food(10087, "Rice");
        Diet diet = new Diet(1, new Date(System.currentTimeMillis()), MealType.LUNCH);
        diet.addIngredient(chicken, 150.0);
        diet.addIngredient(rice, 300.0);

        // Create and add daily nutritional intake
        DailyNutrientIntake intake1 = new DailyNutrientIntake(1, 50.0, 200.0, 30.0);
        DailyNutrientIntake intake2 = new DailyNutrientIntake(2, 60.0, 180.0, 25.0);
        NutrientTracker nutrientTracker = new NutrientTracker();
        nutrientTracker.addDailyIntake(intake1);
        nutrientTracker.addDailyIntake(intake2);

        // Print dietary information
        System.out.println("Diet Information:");
        System.out.println("Date: " + diet.getDate());
        System.out.println("Meal Type: " + diet.getMeal());
        System.out.println("Ingredients: " + diet.getIngredients());
        nutrientTracker.printSummary();
        nutrientTracker.calculatePercentages();

        // Get database connector instance
        DatabaseConnector dbConnector = DatabaseConnector.getInstance();
        System.out.println("Database Connector HashCode: " + dbConnector.hashCode());

        // Create DietAnalyzer instance
        DietAnalyzer analyzer = new DietAnalyzer(new StandardDietAnalysisStrategy());

        // Set date range
        int accountId = 1; // Assume account ID
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30); 
        Date startDate = new Date(cal.getTimeInMillis());
        Date endDate = new Date(System.currentTimeMillis());

        // Analyze using standard strategies
        System.out.println("Diet Analysis with Standard Strategy: ");
        Map<String, Double> analysisResultStandard = analyzer.calculateDietAlignment(accountId, startDate, endDate);
        System.out.println(analysisResultStandard);

        // Switch to another strategy
        analyzer.setAnalysisStrategy(new AlternativeDietAnalysisStrategy());
        System.out.println("Diet Analysis with Alternative Strategy: ");
        Map<String, Double> analysisResultAlternative = analyzer.calculateDietAlignment(accountId, startDate, endDate);
        System.out.println(analysisResultAlternative);
<<<<<<< HEAD
        
        FatLossCalculator fatLossCalculator = new FatLossCalculator();

        // Set the future date for the fat loss calculation
        Calendar futureCal = Calendar.getInstance();
        futureCal.add(Calendar.DAY_OF_MONTH, 30); // For example, 30 days in the future
        Date futureDate = new Date(futureCal.getTimeInMillis());

        // Call the method and print the expected fat loss
        try {
            double expectedFatLoss = fatLossCalculator.calculateExpectedFatLoss(futureDate, accountId);
            System.out.println("Expected Fat Loss: " + expectedFatLoss + " kg");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to calculate expected fat loss due to a database error.");
        }
=======
>>>>>>> 16d3cfe1cf87c642cf9a2e506a8460518a33b2bc

    }
}
