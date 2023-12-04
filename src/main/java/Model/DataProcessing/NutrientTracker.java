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
            double amount = 0;
            switch (nutrient.getName()) {
                case "Protein":
                    amount = totalProtein;
                    break;
                case "Carbohydrates":
                    amount = totalCarbohydrates;
                    break;
                case "Other Nutrients":
                    amount = totalOtherNutrients;
                    break;
            }
            printPercentage(nutrient.getName(), calculatePercentage(amount, totalNutrients));
        }
    }

    /**
     * Calculates the percentage of a nutrient.
     * @param amount The total amount of the nutrient.
     * @param total The total amount of all nutrients.
     * @return The percentage of the nutrient.
     */
    private double calculatePercentage(double amount, double total) {
        return (amount / total) * 100;
    }

    /**
     * Prints the percentage of a nutrient.
     * @param nutrientName The name of the nutrient.
     * @param percentage The percentage of the nutrient.
     */
    private void printPercentage(String nutrientName, double percentage) {
        System.out.println(nutrientName + ": " + String.format("%.2f%%", percentage));
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
