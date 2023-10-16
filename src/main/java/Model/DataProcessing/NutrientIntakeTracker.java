package Project;

import java.util.Scanner;

public class NutrientIntakeTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Nutrient Intake Tracker!");
        System.out.print("Enter the number of days to track nutrient intake: ");
        int numberOfDays = scanner.nextInt();

        double totalProtein = 0;
        double totalCarbohydrates = 0;
        double totalOtherNutrients = 0;

        for (int day = 1; day <= numberOfDays; day++) {
            System.out.println("\nDay " + day);
            System.out.print("Enter the amount of protein (grams) for day " + day + ": ");
            double protein = scanner.nextDouble();
            totalProtein += protein;

            System.out.print("Enter the amount of carbohydrates (grams) for day " + day + ": ");
            double carbohydrates = scanner.nextDouble();
            totalCarbohydrates += carbohydrates;

            System.out.print("Enter the amount of other nutrients (grams) for day " + day + ": ");
            double otherNutrients = scanner.nextDouble();
            totalOtherNutrients += otherNutrients;
        }

        // Calculate percentages
        double totalNutrients = totalProtein + totalCarbohydrates + totalOtherNutrients;
        double proteinPercentage = (totalProtein / totalNutrients) * 100;
        double carbohydratesPercentage = (totalCarbohydrates / totalNutrients) * 100;
        double otherNutrientsPercentage = (totalOtherNutrients / totalNutrients) * 100;

        System.out.println("\nAverage Daily Nutrient Intake Over " + numberOfDays + " days:");
        System.out.println("Protein: " + proteinPercentage + "%");
        System.out.println("Carbohydrates: " + carbohydratesPercentage + "%");
        System.out.println("Other Nutrients: " + otherNutrientsPercentage + "%");

        scanner.close();
    }
}