package Model.DataProcessing;

import java.sql.Date;

public class CalorieExerciseData {
    private Date date;
    private double calorieIntake;
    private double exerciseCaloriesBurned;

    // Constructor
    public CalorieExerciseData(Date date, double calorieIntake, double exerciseCaloriesBurned) {
        this.date = date;
        this.calorieIntake = calorieIntake;
        this.exerciseCaloriesBurned = exerciseCaloriesBurned;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(double calorieIntake) {
        this.calorieIntake = calorieIntake;
    }

    public double getExerciseCaloriesBurned() {
        return exerciseCaloriesBurned;
    }

    public void setExerciseCaloriesBurned(double exerciseCaloriesBurned) {
        this.exerciseCaloriesBurned = exerciseCaloriesBurned;
    }
}
