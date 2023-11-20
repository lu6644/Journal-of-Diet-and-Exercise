package Model.Exercise;

import java.sql.Date;

import Model.Profile.UserProfile;

public class Exercise {
    private int accoount_id;

    private int exercise_id;
    private Date date;
    private double duration;
    private String intensity;
    private double caloriesBurnt;
    private String exerciseType;

    public Exercise() {
    }

    public Exercise(int account_id, Date date, double duration, String intensity, String exerciseType) {
        this.accoount_id = account_id;
        this.date = date;
        this.duration = duration;
        this.intensity = intensity;
        this.exerciseType = exerciseType;
    }

    public void setDate(Date newDate) {
        this.date = newDate;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDuration(double n) {
        this.duration = n;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setIntensity(String str) {
        this.intensity = str;
    }

    public String getIntensity() {
        return this.intensity;
    }

    public void setCaloriesBurnt(double amount) {
        this.caloriesBurnt = amount;
    }

    public double getCaloriesBurnt() {
        return this.caloriesBurnt;
    }

    public void setExerciseType(String str) {
        this.exerciseType = str;
    }

    public String getExerciseType() {
        return this.exerciseType;
    }

    public void setExercise_id(int exercise_id){
        this.exercise_id = exercise_id;
    }

    public int getExercise_id(){
        return this.exercise_id;
    }
    public void setAccoount_id(int accoount_id) {
        this.accoount_id = accoount_id;
    }

    public int getAccoount_id() {
        return this.accoount_id;
    }
}
