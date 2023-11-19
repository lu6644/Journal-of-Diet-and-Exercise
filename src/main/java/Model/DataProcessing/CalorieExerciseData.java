package Model.DataProcessing;

import java.sql.Date;

public class CalorieExerciseData {
    private Date date; // Date of the calorie record
    private double calorieIntake; // Amount of calories consumed
    private double exerciseCaloriesBurned; // Amount of calories burned through exercise

    // Constructor to initialize a CalorieExerciseData object with date, calorie intake, and exercise calories burned
    public CalorieExerciseData(Date date, double calorieIntake, double exerciseCaloriesBurned) {
        this.date = date;
        this.calorieIntake = calorieIntake;
        this.exerciseCaloriesBurned = exerciseCaloriesBurned;
    }

    // Getter method to retrieve the date of the calorie record
    public Date getDate() {
        return date;
    }

    // Setter method to set the date of the calorie record
    public void setDate(Date date) {
        this.date = date;
    }

    // Getter method to retrieve the amount of calories consumed
    public double getCalorieIntake() {
        return calorieIntake;
    }

    // Setter method to set the amount of calories consumed
    public void setCalorieIntake(double calorieIntake) {
        this.calorieIntake = calorieIntake;
    }

    // Getter method to retrieve the amount of calories burned through exercise
    public double getExerciseCaloriesBurned() {
        return exerciseCaloriesBurned;
    }

    // Setter method to set the amount of calories burned through exercise
    public void setExerciseCaloriesBurned(double exerciseCaloriesBurned) {
        this.exerciseCaloriesBurned = exerciseCaloriesBurned;
    }
}
