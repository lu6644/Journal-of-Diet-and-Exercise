package Model.DatabaseInteraction;

import Model.Diet.Diet;
import Model.Diet.FoodCategory;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        FoodDAO foodDAO = FoodDAO.getInstance();
//        int foodId = foodDAO.queryId("Butter, unsalted");
//        NutrientsDAO nutrientsDAO = NutrientsDAO.getInstance();
//        Map<Nutrient,Double> nutrientsList = nutrientsDAO.extractNutrients(new Food("Butter, unsalted"));
//        Diet diet = new Diet(1, new Date(2023,10,03), MealType.DINNER);
//        Food f1 = new Food("Butter, unsalted");
//        Food f2 = new Food("Beef, ground, regular");
//        diet.addIngredient(f1, 30);
//        diet.addIngredient(f2,150);
//        NutrientsCalculator.getNutrientsValue(diet);
//        DietDAO dietDAO = DietDAO.getInstance();
//        System.out.println("Diet Info to be inserted: " + diet.toString());
//        dietDAO.insertDiet(diet);

//        DietDAO dietDAO = DietDAO.getInstance();
//        List<Diet> diets = dietDAO.queyDietsHistory(1);
//        System.out.println(diets);

//        Diet diet = dietDAO.queryDietNutrientsDetail(3);
//        System.out.println(diet);
        Map<FoodCategory, Double> foodCategoryTotal = FoodDAO.getInstance().queryFoodCategoryTotal(1);
        System.out.println(foodCategoryTotal);
        Map<FoodCategory, Double> foodCategoryDailyServings = FoodDAO.getInstance().queryFoodCategoryDailyCFG(1);
        System.out.println(foodCategoryDailyServings);
    }
}
