package Controller.DataLoggingHandler;

import Model.DatabaseInteraction.ProfileDAO;
import Model.Profile.UserProfile;

public class ProfileAddingController {
    private static ProfileAddingController instance = null;

    private ProfileAddingController() {

    }

    public static ProfileAddingController getInstance() {
        if (instance == null) {
            instance = new ProfileAddingController();
        }
        return instance;
    }

    public int addNewProfile(String username, String password, String firstName, String lastName,
                             int age, String gender, double height, String heightUnit, double weight, String weightUnit,
                             String specialPeriod, boolean hasWeightScale) {
        UserProfile profile = new UserProfile(username,password,firstName,lastName,age,gender,height,heightUnit,weight, weightUnit,
                specialPeriod,hasWeightScale);
        int profileId = ProfileDAO.getInstance().insertNewProfile(profile);
        return profileId;
    }
}
