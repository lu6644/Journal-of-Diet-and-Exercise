package Controller.DataLoggingHandler;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;

import Model.DatabaseInteraction.ExerciseDAO;
import Model.DatabaseInteraction.ProfileDAO;
import Model.Exercise.Exercise;
import Model.Profile.UserProfile;

public class ExerciseLogging {
	private CaloriesCalculationStrategy strategy;
	private static ExerciseLogging instance = null;

	private ExerciseLogging(){

	}

	public static ExerciseLogging getInstance(){
		if(instance == null){
			instance = new ExerciseLogging();
		}
		return instance;
	}

	// Method to log exercise and calculate burnt calories
	public double logExercise(int account_id, String exercise_type, Date date, String intensity, double duration){
		// Create an Exercise object
		Exercise ex = new Exercise(account_id, date, duration, intensity, exercise_type);

		// Insert exercise into the database and get the updated Exercise object
		ex = ExerciseDAO.getInstance().insertExercise(ex);

		// Get the user's BMR value from the profile
		double bmr_value = ProfileDAO.getInstance().getProfile(account_id).calculateBMR();

		// Set the strategy based on the exercise type
		switch(exercise_type){
			case "Swimming":
				setStrategy(new SwimmingStrategy());
				break;
			case "Running":
				setStrategy(new RunningStrategy());
				break;
			case "Biking":
				setStrategy(new BikingStrategy());
				break;
			case "Walking":
				setStrategy(new WalkingStrategy());
				break;
			case "Calisthenics":
				setStrategy(new CalisthenicsStrategy());
				break;
			default:
				setStrategy(new BasketballStrategy());
				break;
		}

		double caloriesBurnt = calcualteCalories(duration, intensity,bmr_value);
		ex.setCaloriesBurnt(caloriesBurnt);
		ExerciseDAO.getInstance().insertExerciseCaloriesBurnt(ex.getAccoount_id(), ex.getExercise_id(), caloriesBurnt);

		// Return the calculated calories burnt
		return caloriesBurnt;
	}
	// Set the strategy for calories calculation
	public void setStrategy(CaloriesCalculationStrategy strategy) {
		this.strategy = strategy;
	}

	// Calculate calories based on the selected strategy
	public double calcualteCalories(Double duration, String intensity, double bmr_value) {
		double caloriesBurnt = strategy.calculate(duration, intensity, bmr_value);
		return caloriesBurnt;
	}

	interface CaloriesCalculationStrategy {
		double calculate(double duration, String intensity, double bmr);
	}

	// Calories calculation strategy for swimming
	public class SwimmingStrategy implements CaloriesCalculationStrategy {
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("vigorous effort", 9.8);
			map.put("light or moderate effort", 5.8);
			MET = map.get(intensity);
			String res = String.format("%.2f", bmr * MET / 24 * duration);
			return bmr * MET / 24 * duration;
		}
	}

	// Calories calculation strategy for running
	public class RunningStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("5 mph (12 min/mile)", 8.0);
			map.put("7 mph (8.5 min/mile)", 11.5);
			map.put("10 mph (6 min/mile)", 16.0);
			MET = map.get(intensity);
			return bmr * MET / 24 * duration;
		}

	}

	// Calories calculation strategy for biking
	public class BikingStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("for pleasure", 4.0);
			map.put("light effort", 8.0);
			map.put("moderate effort", 10.0);
			map.put("racing or vigorous effort", 12.0);
			MET = map.get(intensity);
			return bmr * MET / 24 * duration;
		}
	}

	// Calories calculation strategy for walking
	public class WalkingStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("moderate pace", 3.5);
			map.put("for exercise", 4.5);
			map.put("very brisk pace", 5.0);
			MET = map.get(intensity);
			return bmr * MET / 24 * duration;
		}

	}

	// Calories calculation strategy for calisthenics
	public class CalisthenicsStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("light effort", 2.8);
			map.put("moderate effort", 3.8);
			map.put("vigorous effort", 8.0);
			MET = map.get(intensity);
			return bmr * MET / 24 * duration;
		}

	}

	// Calories calculation strategy for basketball
	public class BasketballStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("game", 8.0);
			map.put("general", 6.0);
			map.put("shooting baskets", 4.5);
			MET = map.get(intensity);
			return bmr * MET / 24 * duration;
		}
	}
}
