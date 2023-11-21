package Model.DataProcessing;

import Model.Diet.Nutrient;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to track and analyze daily nutrient intake.
 */
public class NutrientTracker {
    private List<DailyNutrientIntake> dailyIntakes;
    private List<Nutrient> nutrients;

    /**
     * Constructor for NutrientTracker.
     * Initializes lists for daily intakes and nutrients.
     */
    public NutrientTracker() {
        dailyIntakes = new ArrayList<>();
        nutrients = new ArrayList<>();
        
        // Adding default nutrients to the list
        nutrients.add(new Nutrient(1, "Protein", "grams"));
        nutrients.add(new Nutrient(2, "Carbohydrates", "grams"));
        nutrients.add(new Nutrient(3, "Other Nutrients", "grams"));
    }

    /**
     * Adds a daily intake record to the tracker.
     * @param intake The DailyNutrientIntake object to be added.
     */
    public void addDailyIntake(DailyNutrientIntake intake) {
        dailyIntakes.add(intake);
    }

    /**
     * Calculates and prints the percentage of each nutrient based on total intake.
     */
    public void calculatePercentages() {
        double totalProtein = 0;
        double totalCarbohydrates = 0;
        double totalOtherNutrients = 0;

        // Summing up the total intake of each nutrient
        for (DailyNutrientIntake intake : dailyIntakes) {
            totalProtein += intake.getProtein();
            totalCarbohydrates += intake.getCarbohydrates();
            totalOtherNutrients += intake.getOtherNutrients();
        }

        double totalNutrients = totalProtein + totalCarbohydrates + totalOtherNutrients;

        // Calculating and printing the percentage of each nutrient
        for (Nutrient nutrient : nutrients) {
            String name = nutrient.getName();
            double percentage = 0;
            if (name.equals("Protein")) {
                percentage = (totalProtein / totalNutrients) * 100;
            } else if (name.equals("Carbohydrates")) {
                percentage = (totalCarbohydrates / totalNutrients) * 100;
            } else if (name.equals("Other Nutrients")) {
                percentage = (totalOtherNutrients / totalNutrients) * 100;
            }
            System.out.println(name + ": " + String.format("%.2f%%", percentage));
        }
    }

    /**
     * Prints a summary of daily nutrient intake.
     */
    public void printSummary() {
        System.out.println("\nAverage Daily Nutrient Intake Over " + dailyIntakes.size() + " days:");
        // Iterating through each daily intake to print details
        for (DailyNutrientIntake intake : dailyIntakes) {
            System.out.println("Day " + (int)intake.getDays());
            System.out.println("Protein: " + intake.getProtein() + " grams");
            System.out.println("Carbohydrates: " + intake.getCarbohydrates() + " grams");
            System.out.println("Other Nutrients: " + intake.getOtherNutrients() + " grams");
            System.out.println();
        }
    }
}
