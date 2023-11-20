package Controller.DataRequestHandler;

import Model.DatabaseInteraction.FoodDAO;
import Model.DatabaseInteraction.ProfileDAO;
import Model.Diet.FoodCategory;
import Model.Diet.FoodCategoryFactory;
import Model.Profile.UserProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class FoodsQueryController {
    private static FoodsQueryController instance = null;
    private UserProfile profile = null;

    private FoodsQueryController() {

    }

    public static FoodsQueryController getInstance() {
        if (instance == null) {
            instance = new FoodsQueryController();
        }
        return instance;
    }

    private UserProfile getProfile(int profileId) {
        if (profile == null){
            profile = ProfileDAO.getInstance().getProfile(profileId);
        }
        return profile;
    }

    public Map<String, Double> getActualFoodPortions(int profileId){
        Map<FoodCategory, Double> foodCategoryDailyCFG =
                FoodDAO.getInstance().queryFoodCategoryDailyCFG(profileId);
        return foodCategoryDailyCFG.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey().getType(),
                entry -> Math.round(entry.getValue() * 100)/100.0
        ));
    }

    public Map<String, Double> getRecommendedFoodPortions(int profileId){
        String[] foodCategories = new String[]{
                "Vegetables and Fruit",
                "Grain Products",
                "Meat and Alternatives",
                "Milk and Alternatives"
        };
        UserProfile profile = getProfile(profileId);
        return Arrays.stream(foodCategories).map(category ->
                        (new FoodCategoryFactory()).createFoodCategory(category))
                .collect(Collectors.toMap(
                        foodCategory -> foodCategory.getType(),
                        foodCategory -> foodCategory.getRecommendedCfg(profile)));
    }
}
