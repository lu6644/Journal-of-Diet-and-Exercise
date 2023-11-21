package Model.DataProcessing;

import java.util.Date;

/**
 * Represents the nutrient intake for a specific date.
 */
public class NutrientIntake {
    private Date date;
    private double protein;
    private double carbohydrates;
    private double fats;

    /**
     * Default constructor. Initializes with default values.
     */
    public NutrientIntake() {
        // Initialize with default values if needed
    }
    
    /**
     * Constructs a NutrientIntake with specified date and protein value.
     * @param date The date of the nutrient intake.
     * @param protein The amount of protein intake.
     */
    public NutrientIntake(Date date, double protein) {
        this.date = date;
        this.protein = protein;
    }

    // Getters and Setters

    /**
     * Gets the date of nutrient intake.
     * @return The date of intake.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of nutrient intake.
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the protein intake amount.
     * @return The amount of protein intake.
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Sets the protein intake amount.
     * @param protein The amount of protein to set.
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * Gets the carbohydrates intake amount.
     * @return The amount of carbohydrates intake.
     */
    public double getCarbohydrates() {
        return carbohydrates;
    }

    /**
     * Sets the carbohydrates intake amount.
     * @param carbohydrates The amount of carbohydrates to set.
     */
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    /**
     * Gets the fats intake amount.
     * @return The amount of fats intake.
     */
    public double getFats() {
        return fats;
    }

    /**
     * Sets the fats intake amount.
     * @param fats The amount of fats to set.
     */
    public void setFats(double fats) {
        this.fats = fats;
    }

    /**
     * Returns a string representation of the NutrientIntake object.
     * @return String representation of NutrientIntake.
     */
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
