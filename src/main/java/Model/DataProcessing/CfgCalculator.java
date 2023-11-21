package Model.DataProcessing;

import Model.Diet.FoodCategory;
import Model.Profile.UserProfile;

public abstract class CfgCalculator {
    //Chain of Responsibility design pattern used here
    protected CfgCalculator successor;

    public void setSuccessor(CfgCalculator successor) {
        this.successor = successor;
    }

    //calculate the number of servings recommended in CFG
    public abstract double calculateServings(FoodCategory foodCategory, UserProfile profile);
}
