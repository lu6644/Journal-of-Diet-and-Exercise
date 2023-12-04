package Controller.DataLoggingHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Model.DataProcessing.NutrientsCalculator;
import Model.DatabaseInteraction.DietDAO;
import Model.Diet.*;
import View.DietExerciseDataUI.DietLoggingData;

public class DietLoggingController {
	private static DietLoggingController instance = null;

	private DietLoggingController() {

	}

	//Singleton
	public static DietLoggingController getInstance() {
		if (instance == null) {
			instance = new DietLoggingController();
		}
		return instance;
	}

	public String[] logDiet(DietLoggingData dietLoggingData) {
		MealType mealType = MealType.valueOf(dietLoggingData.getMeal().toUpperCase());
		Diet diet = new Diet(dietLoggingData.getAccountId(), dietLoggingData.getDate(), mealType);
		for (Map.Entry<String, Double> entry : dietLoggingData.getFoods().entrySet()) {
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

	//map value to string for front-end display
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
