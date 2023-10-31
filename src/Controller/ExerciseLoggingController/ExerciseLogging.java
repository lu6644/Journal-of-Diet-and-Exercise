package Controller.ExerciseLoggingController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import Model.Profile.UserProfile;

public class ExerciseLogging {
	Date date;
	private double duration;
	private String intensity;
	private CaloriesCalculationStrategy strategy;
	UserProfile user;

	public ExerciseLogging(String date, String time, double duration, String intensity, UserProfile user) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		String date_time = date + " " + time + ":00";
		try {
			this.date = sdf.parse(date_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.duration = duration;
		this.intensity = intensity;
		this.user = user;
	}

	public void setStrategy(CaloriesCalculationStrategy strategy) {
		this.strategy = strategy;
	}

	public double calcualteCalories(double bmr) {
		return strategy.calculate(duration, intensity, bmr);
	}

	interface CaloriesCalculationStrategy {
		double calculate(double duration, String intensity, double bmr);
	}

	public class SwimmingStrategy implements CaloriesCalculationStrategy {
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("vigorous effort", 9.8);
			map.put("light or moderate effort", 5.8);
			MET = map.get(intensity);
			String res = String.format("%.2f", user.calculateBMR() * MET / 24 * duration);
			return user.calculateBMR() * MET / 24 * duration;
		}
	}

	public class RunningStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("5 mph (12 min/mile)", 8.0);
			map.put("7 mph (8.5 min/mile)", 11.5);
			map.put("10 mph (6 min/mile)", 16.0);
			MET = map.get(intensity);
			return user.calculateBMR() * MET / 24 * duration;
		}

	}

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
			return user.calculateBMR() * MET / 24 * duration;

		}

	}

	public class WalkingStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("moderate pace", 3.5);
			map.put("for exercise", 4.5);
			map.put("very brisk pace", 5.0);
			MET = map.get(intensity);
			return user.calculateBMR() * MET / 24 * duration;
		}

	}

	public class CalisthenicsStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("light effort", 2.8);
			map.put("moderate effort", 3.8);
			map.put("vigorous effort", 8.0);
			MET = map.get(intensity);
			return user.calculateBMR() * MET / 24 * duration;
		}

	}

	public class BasketballStrategy implements CaloriesCalculationStrategy {

		@Override
		public double calculate(double duration, String intensity, double bmr) {
			double MET;
			HashMap<String, Double> map = new HashMap<>();
			map.put("game", 8.0);
			map.put("general", 6.0);
			map.put("shooting baskets", 4.5);
			MET = map.get(intensity);
			return user.calculateBMR() * MET / 24 * duration;
		}

	}
}
