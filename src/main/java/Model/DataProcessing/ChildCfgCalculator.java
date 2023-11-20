package Model.DataProcessing;

import Model.Diet.FoodCategory;
import Model.Profile.UserProfile;

public class ChildCfgCalculator extends CfgCalculator{
    @Override
    public double calculateServings(FoodCategory foodCategory, UserProfile profile) {
        int age = profile.getAge();
        double servingsNum = 0;
        if (age <= 13){
            switch (foodCategory.getType()){
                case "Meat and Alternatives":
                    servingsNum = 1;
                    break;
                case "Milk and Alternatives":
                    if (age <= 8) servingsNum = 2;
                    else servingsNum = 3;
                    break;
                case "Grain Products":
                    if (age <=3) servingsNum =3;
                    else if (age <= 8) servingsNum =4;
                    else servingsNum = 6;
                    break;
                case "Vegetables and Fruit":
                    if (age <=3) servingsNum =4;
                    else if (age <= 8) servingsNum =5;
                    else servingsNum = 6;
                    break;
            }
        }
        else if (successor != null){
            servingsNum = successor.calculateServings(foodCategory, profile);
        }
        return servingsNum;
    }
}
