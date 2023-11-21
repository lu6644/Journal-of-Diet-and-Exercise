package View.ExerciseLoggingUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import View.ProfileUI.ProfileUIData;

public class Main {

	public static void main(String argsp[]) {
		ProfileUIData user = ProfilesQueryController.getInstance().getProfile(1);
		ExerciseLoggingUI.launch(user);
	}
}
