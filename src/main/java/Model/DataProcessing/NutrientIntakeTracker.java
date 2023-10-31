package Model.DataProcessing;

import java.util.Scanner;

public class NutrientIntakeTracker {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Nutrient Intake Tracker!");
		System.out.print("Enter the number of days to track nutrient intake: ");
		int numberOfDays = scanner.nextInt();

		NutrientTracker tracker = new NutrientTracker();

		for (int day = 1; day <= numberOfDays; day++) {
			System.out.println("\nDay " + day);
			System.out.print("Enter the amount of protein (grams) for day " + day + ": ");
			double protein = scanner.nextDouble();

			System.out.print("Enter the amount of carbohydrates (grams) for day " + day + ": ");
			double carbohydrates = scanner.nextDouble();

			System.out.print("Enter the amount of other nutrients (grams) for day " + day + ": ");
			double otherNutrients = scanner.nextDouble();

			DailyNutrientIntake dailyIntake = new DailyNutrientIntake(day, protein, carbohydrates, otherNutrients);
			tracker.addDailyIntake(dailyIntake);
		}

		tracker.calculatePercentages();
		tracker.printSummary();

		scanner.close();
	}
}
