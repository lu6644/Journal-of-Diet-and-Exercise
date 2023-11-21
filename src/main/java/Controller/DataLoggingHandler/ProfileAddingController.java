package Controller.DataLoggingHandler;

import Model.DatabaseInteraction.ProfileDAO;
import Model.Profile.UserProfile;

// Controller class responsible for adding new user profiles
public class ProfileAddingController {

    // Singleton instance of the controller
    private static ProfileAddingController instance = null;

    // Private constructor to enforce singleton pattern
    private ProfileAddingController() {

    }

    public static ProfileAddingController getInstance() {
        if (instance == null) {
            instance = new ProfileAddingController();
        }
        return instance;
    }

    // Method to add a new user profile to the database
    public int addNewProfile(String username, String password, String firstName, String lastName,
                             int age, String gender, double height, String heightUnit, double weight, String weightUnit,
                             String specialPeriod, boolean hasWeightScale) {
        UserProfile profile = new UserProfile(username,password,firstName,lastName,age,gender,height,heightUnit,weight, weightUnit,
                specialPeriod,hasWeightScale);

        // Insert the new profile into the database and get the assigned profile ID
        int profileId = ProfileDAO.getInstance().insertNewProfile(profile);

        // Return the assigned profile ID
        return profileId;
    }
}
