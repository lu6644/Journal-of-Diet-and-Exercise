package View.DietExerciseDataUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import View.DietExerciseDataUI.DietLoggingPage;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.ProfileUI.ProfileUIData;

public class Main {
    public static void main(String[] args) {
        ProfileUIData user = ProfilesQueryController.getInstance().getProfile(1);
        DietLoggingPage.launch(user);

    }
}