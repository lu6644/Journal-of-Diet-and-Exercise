package Controller.DataLoggingHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Model.DataProcessing.NutrientsCalculator;
import Model.DatabaseInteraction.DietDAO;
import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.MealType;
import Model.Diet.Nutrient;

public class DietLoggingController {
	private static DietLoggingController instance = null;

	private DietLoggingController() {

	}

	public static DietLoggingController getInstance() {
		if (instance == null) {
			instance = new DietLoggingController();
		}
		return instance;
	}

	public String[] logDiet(int accountId, Date date, String meal, Map<String, Double> foods) {
		MealType mealType = MealType.valueOf(meal.toUpperCase());
		Diet diet = new Diet(accountId, date, mealType);
		for (Map.Entry<String, Double> entry : foods.entrySet()) {
			if (!entry.getKey().isEmpty()) {
				Food f = new Food(entry.getKey());
				Double qty = entry.getValue();
				diet.addIngredient(f, qty);
			}
		}
		NutrientsCalculator.getNutrientsValue(diet);
		DietDAO.getInstance().addDiet2Db(diet);
		// return nutrients information and calories intake to frontend page
		return new String[]
				{nutrientsValueToString(diet.getNutrientsValue()),
				diet.getCalories()+"kCal"};
	}

	private String nutrientsValueToString(HashMap<Nutrient, Double> nutrientsValue) {
		String str = "";
		for (Map.Entry<Nutrient, Double> entry : nutrientsValue.entrySet()) {
			Nutrient nutrient = entry.getKey();
			Double amount = entry.getValue();
			str += String.format("%s: %.2f%s\n", nutrient.getName(), amount, nutrient.getUnit());
		}
		System.out.println(str);
		return str;
	}

}
