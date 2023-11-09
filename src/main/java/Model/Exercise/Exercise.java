package Model.Exercise;

import java.util.Date;

import Model.Profile.UserProfile;

public class Exercise {
	private UserProfile user;
	private Date date;
	private double duration;
	private String intensity;
	private double caloriesBurnt;
	private String exerciseType;
	
	public Exercise() {
	}

	public Exercise(UserProfile user, Date date, double duration, String intensity, String exerciseType) {
		this.user = user;
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
	
	public void setUser(UserProfile user) {
		this.user = user;
	}
	public UserProfile getUser() {
		return this.user;
	}
}
