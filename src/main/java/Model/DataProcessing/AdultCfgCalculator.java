package Model.DataProcessing;

import Model.Diet.FoodCategory;
import Model.Profile.UserProfile;

public class AdultCfgCalculator extends CfgCalculator{
    @Override
    public double calculateServings(FoodCategory foodCategory, UserProfile profile) {
        int age = profile.getAge();
        String gender = profile.getGender();
        double servingsNum = 0;
        if (age >= 19){
            switch (foodCategory.getType()){
                case "Meat and Alternatives":
                    if (gender.equalsIgnoreCase("male")) servingsNum = 3;
                    else servingsNum = 2;
                    break;
                case "Milk and Alternatives":
                    if (age <= 50) servingsNum = 2;
                    else servingsNum = 3;
                    break;
                case "Grain Products":
                    if (gender.equalsIgnoreCase("male")) {
                        if (age <= 50) servingsNum =8;
                        else servingsNum = 7;
                    }
                    else servingsNum = 6;
                    break;
                case "Vegetables and Fruit":
                    if (age <= 50){
                        if (gender.equalsIgnoreCase("male")) servingsNum = 8;
                        else servingsNum =7;
                    }
                    else servingsNum = 7;
                    break;
            }
        }
        else if (successor != null){
            servingsNum = successor.calculateServings(foodCategory, profile);
        }
        return servingsNum;
    }
}
