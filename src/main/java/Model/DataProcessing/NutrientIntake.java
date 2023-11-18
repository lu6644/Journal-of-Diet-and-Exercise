package Model.DataProcessing;

import java.util.Date;

public class NutrientIntake {
    private Date date;
    private double protein;
    private double carbohydrates;
    private double fats;

    // Constructor
    public NutrientIntake() {
        // Initialize with default values if needed
    }
    
    public NutrientIntake(Date date, double protein) {
        this.date = date;
        this.protein = protein;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    // You might also want to override toString() for easy debugging
    @Override
    public String toString() {
        return "NutrientIntake{" +
                "date=" + date +
                ", protein=" + protein +
                ", carbohydrates=" + carbohydrates +
                ", fats=" + fats +
                '}';
    }
}