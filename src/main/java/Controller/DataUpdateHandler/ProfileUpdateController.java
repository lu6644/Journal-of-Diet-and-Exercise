package Controller.DataUpdateHandler;

import Model.DatabaseInteraction.ProfileDAO;

public class ProfileUpdateController {
    private static ProfileUpdateController instance = null;

    private ProfileUpdateController(){

    }

    public static ProfileUpdateController getInstance(){
        if(instance == null){
            instance = new ProfileUpdateController();
        }

        return instance;
    }

    // Update the profile with the provided information
    public void updateProfile(int id, String firstname, String lastname, int age, String gender, Double height, String heightUnit, Double weight, String weightUnit, String specialPeriod, Boolean hasWeightScale){
        ProfileDAO.getInstance().updateProfile(id, firstname, lastname, age, gender, height, heightUnit, weight, weightUnit, specialPeriod, hasWeightScale);
    }

}
