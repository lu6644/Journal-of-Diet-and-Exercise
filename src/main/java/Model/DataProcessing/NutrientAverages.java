package Model.DataProcessing;

public class NutrientAverages {
    private double avgProtein;
    private double avgCarbohydrates;
    private double avgFats;

    // Constructor that takes three double values
    public NutrientAverages(double avgProtein, double avgCarbohydrates, double avgFats) {
        this.avgProtein = avgProtein;
        this.avgCarbohydrates = avgCarbohydrates;
        this.avgFats = avgFats;
    }

    // Getters
    public double getAvgProtein() {
        return avgProtein;
    }

    public double getAvgCarbohydrates() {
        return avgCarbohydrates;
    }

    public double getAvgFats() {
        return avgFats;
    }

}
