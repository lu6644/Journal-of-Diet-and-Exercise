package View.DataVisulizationUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.ProfileUI.ProfileUIData;

public class Main {
    public static void main(String[] args){
        ProfileUIData user = ProfilesQueryController.getInstance().getProfile(1);
        CFGComparisionPage.launch(user);
    }
}
