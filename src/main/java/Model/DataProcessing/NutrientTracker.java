package Model.DataProcessing;

import Model.Diet.Nutrient;

import java.util.ArrayList;
import java.util.List;

public class NutrientTracker {
    private List<DailyNutrientIntake> dailyIntakes;
    private List<Nutrient> nutrients;

    public NutrientTracker() {
        dailyIntakes = new ArrayList<>();
        nutrients = new ArrayList<>();
        
        nutrients.add(new Nutrient(1, "Protein", "grams"));
        nutrients.add(new Nutrient(2, "Carbohydrates", "grams"));
        nutrients.add(new Nutrient(3, "Other Nutrients", "grams"));
    }

    public void addDailyIntake(DailyNutrientIntake intake) {
        dailyIntakes.add(intake);
    }

    public void calculatePercentages() {
        double totalProtein = 0;
        double totalCarbohydrates = 0;
        double totalOtherNutrients = 0;

        for (DailyNutrientIntake intake : dailyIntakes) {
            totalProtein += intake.getProtein();
            totalCarbohydrates += intake.getCarbohydrates();
            totalOtherNutrients += intake.getOtherNutrients();
        }

        double totalNutrients = totalProtein + totalCarbohydrates + totalOtherNutrients;

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

    public void printSummary() {
        System.out.println("\nAverage Daily Nutrient Intake Over " + dailyIntakes.size() + " days:");
        for (DailyNutrientIntake intake : dailyIntakes) {
            System.out.println("Day " + (int)intake.getDays());
            System.out.println("Protein: " + intake.getProtein() + " grams");
            System.out.println("Carbohydrates: " + intake.getCarbohydrates() + " grams");
            System.out.println("Other Nutrients: " + intake.getOtherNutrients() + " grams");
            System.out.println();
        }
    }
}