package Model.Exercise;

import java.sql.Date;

import Model.Profile.UserProfile;

public class Exercise {
	private UserProfile user;
	private Date date;
	private double duration;
	private String intensity;
	private double caloriesBurnt;
	private String exerciseType;

	public Exercise(UserProfile user, Date date, double duration, String intensity, double caloriesBurnt,
			String exerciseType) {
		this.user = user;
		this.date = date;
		this.duration = duration;
		this.intensity = intensity;
		this.caloriesBurnt = caloriesBurnt;
	}

	public void setDate(Date newDate) {
		this.date = newDate;
	}

	public Date getDate() {
		return this.date;
	}

	public double getDuration() {
		return this.duration;
	}

	public String getIntensity() {
		return this.intensity;
	}

	public double getCaloriesBurnt() {
		return this.caloriesBurnt;
	}

	public String getExerciseType() {
		return this.exerciseType;
	}
}
