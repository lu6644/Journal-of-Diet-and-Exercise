package Model.Diet;

import Model.DatabaseInteraction.ProfileDAO;
import Model.Profile.UserProfile;
import View.DietExerciseDataUI.DietLoggingPage;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Diet diet = new Diet(1, new Date(2023,10,03), MealType.DINNER);
        diet.setId(1);
        Food f1 = new Food(1, "food1");
        Food f2 = new Food(2, "food2");
        diet.addIngredient(f1, 100);
        diet.addIngredient(f2,150);
        Nutrient n1 = new Nutrient(1, "nutrient1", "g");
		Nutrient n2 = new Nutrient(2, "nutrient2", "mg");
		Nutrient n3 = new Nutrient(3, "nutrient3", "µg");
		Nutrient n4 = new Nutrient(4, "nutrient4", "g");
        diet.addToNutrient(n1,10.0);
        diet.addToNutrient(n2, 15.0);
        diet.addToNutrient(n3,200.0);
        diet.addToNutrient(n4,2.5);
        System.out.println(String.format("Diet(id=%d) Info:", diet.getId()));
        System.out.println(String.format("for user(userId=%d)",diet.getAccountId()));
        System.out.println(String.format("MealType: %s",diet.getMeal().name()));
        System.out.println(String.format("Date: %s",diet.getDate().toString()));
        System.out.println("\nFoods include:");
        for (Map.Entry<Food,Double> entry:diet.getIngredients().entrySet()){
            System.out.println(String.format("%fg of %s", entry.getValue(), entry.getKey().getName()));
        }
        System.out.println("\nSummary of Nutrient Intake:");
        for (Map.Entry<Nutrient,Double> entry:diet.getNutrientsValue().entrySet()){
            System.out.println(String.format("%f%s of %s", entry.getValue(), entry.getKey().getUnit(), entry.getKey().getName()));
        }

        String[] categories = new String[]{"Vegetables and Fruit", "Grain Products", "Meat and Alternatives", "Milk and Alternatives"};
        FoodCategoryFactory fcf = new FoodCategoryFactory();
        List<UserProfile> profiles = ProfileDAO.getInstance().getProfileList();
        for (String c:categories){
            FoodCategory fc = fcf.createFoodCategory(c);
            for (UserProfile profile:profiles){
                System.out.println(profile);
                System.out.println(fc);
                System.out.println(fc.getRecommendedCfg(profile));
                System.out.println();
            }
        }




    }
}
