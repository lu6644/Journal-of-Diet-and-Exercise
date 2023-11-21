package Model.Diet;

import Model.DataProcessing.AdultCfgCalculator;
import Model.DataProcessing.CfgCalculator;
import Model.DataProcessing.ChildCfgCalculator;
import Model.DataProcessing.TeenCfgCalculator;
import Model.Profile.UserProfile;

public abstract class FoodCategory {
    CfgCalculator cfgCalculator;

    public FoodCategory(){

        //employ chain of responsibility here
        CfgCalculator cfgCalculator1 = new ChildCfgCalculator();
        CfgCalculator cfgCalculator2 = new TeenCfgCalculator();
        CfgCalculator cfgCalculator3 = new AdultCfgCalculator();
        cfgCalculator1.setSuccessor(cfgCalculator2);
        cfgCalculator2.setSuccessor(cfgCalculator3);
        this.cfgCalculator = cfgCalculator1;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "type='" + getType() + '\'' +
                '}';
    }

    //get the recommended number of Canadian Food Guide servings
    public double getRecommendedCfg(UserProfile profile) {
        return cfgCalculator.calculateServings(this,profile);
    }

    public abstract String getType();
}
