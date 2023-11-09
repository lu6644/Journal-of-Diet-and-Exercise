package Controller.DataLoggingHandler;

import Controller.DataLoggingHandler.ExerciseLogging.BikingStrategy;
import Model.Profile.UserProfile;

public class Main {
	public static void main(String args[]) {
		
		//for Exercise Logging controller
		UserProfile profile = new UserProfile("test", "test", "test", "test", 30, "male", 175.00, 70, null, false);
		
		ExerciseLogging el = new ExerciseLogging("2022-01-01", "21:21",3,"for pleasure", profile);
		
		ExerciseLogging.BikingStrategy bs = el.new BikingStrategy();
		
		el.setStrategy(bs);
		
		System.out.println("The burnt calories of this exercise is: "+el.calcualteCalories()+" cals");
		
		//for Diet Logging Controller
		
	}
}
