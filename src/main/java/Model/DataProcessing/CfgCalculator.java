package Model.DataProcessing;

import Model.Diet.FoodCategory;
import Model.Profile.UserProfile;

public abstract class CfgCalculator {
    protected CfgCalculator successor;

    public void setSuccessor(CfgCalculator successor) {
        this.successor = successor;
    }

    public abstract double calculateServings(FoodCategory foodCategory, UserProfile profile);
}
