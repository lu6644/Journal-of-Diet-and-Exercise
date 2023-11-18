package Model.DataProcessing;

public class DailyNutrientIntake {
    private int day; // Represents the day for which the nutrient intake is recorded
    private double protein; // Amount of protein intake for the day (in grams)
    private double carbohydrates; // Amount of carbohydrate intake for the day (in grams)
    private double otherNutrients; // Amount of other nutrients intake for the day (in grams)

    // Constructor to initialize a DailyNutrientIntake object with day, protein, carbohydrates, and other nutrients
    public DailyNutrientIntake(int day, double protein, double carbohydrates, double otherNutrients) {
        this.day = day;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.otherNutrients = otherNutrients;
    }

    // Getter method to retrieve the day for which the nutrient intake is recorded
    public double getDays() {
        return day;
    }

    // Getter method to retrieve the amount of protein intake for the day
    public double getProtein() {
        return protein;
    }

    // Getter method to retrieve the amount of carbohydrate intake for the day
    public double getCarbohydrates() {
        return carbohydrates;
    }

    // Getter method to retrieve the amount of other nutrients intake for the day
    public double getOtherNutrients() {
        return otherNutrients;
    }
}
