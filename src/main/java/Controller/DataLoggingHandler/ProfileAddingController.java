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
                             int age, String gender, double height, double weight,
                             String specialPeriod, boolean hasWeightScale) {
        UserProfile profile = new UserProfile(username,password,firstName,lastName,age,gender,height,weight,
                specialPeriod,hasWeightScale);
        int profileId = ProfileDAO.getInstance().insertNewProfile(profile);
        return profileId;
    }
}
